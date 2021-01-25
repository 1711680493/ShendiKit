package shendi.kit.format.json;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import shendi.kit.log.Log;

/**
 * JSON 格式对象.<br>
 * 一个JSON对象格式为 {a:b,b:c},在创建对象后可通过 isJson 来判断当前字符串是否为JSONObject.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.1
 * @since 1.1
 */
public class JSONObject {
	
	/** 当前 json 的数据 */
	private String json;
	
	/** 解析后的键值对集合 */
	private HashMap<String, String> jsons = new HashMap<>();
	
	/** 当前对象的Json是否为 JSONObject(是否解析成功) */
	private boolean isJson;
	
	/**
	 * 使用 Json 数据来创建 JSONObject 对象.
	 * @param json JsonObject数据,格式为 {"key" : value,...}
	 */
	public JSONObject(String json) {
		this.json = json;
		if (json == null || json.length() < 5) return;
		
		// 解析
		json = json.trim();
		if (json.charAt(0) != '{' || json.charAt(json.length() - 1) != '}') return;
		
		// 去掉括号 {}
		json = json.substring(1, json.length() - 1);
		
		// 一个一个进行拆分解析,从左到右
		// key带双引号,与值用:隔开
		while (json.length() > 0) {
			json = json.substring(json.indexOf('"') + 1);
			// TODO 还有转义符没有处理 \
			
			int index = json.indexOf('"');
			String key = json.substring(0, index);
			json = json.substring(index + 1).trim();
			
			// 处理完后第一个字符为:(分隔符)
			if (json.charAt(0) != ':') {
				jsons.clear();
				return;
			}
			
			// 去掉分隔符和空行,通过判断第一个字符是否为引号来继续操作
			json = json.substring(1).trim();
			String value = null;
			
			// 值如果带""则为字符串,如果不带,则通过逗号判断,没有逗号则直接跳到结尾
			// TODO 判断是否为 JSONObject或JSONArray等格外形式
			if (json.charAt(0) == '"') {
				json = json.substring(1);
				
				index = json.indexOf('"');
				value = json.substring(0, index);
				json = json.substring(index + 1).trim();
				// 如果不是逗号则代表结尾
				if (json.length() == 0 || json.charAt(0) != ',') {
					json = "";
				}
			} else {
				// 没有逗号则代表结尾
				index = json.indexOf(',');
				if (index == -1) {
					value = json.trim();
					json = "";
				} else {
					value = json.substring(0, index).trim();
					json = json.substring(index);
				}
			}
			jsons.put(key, value);
		}
		isJson = true;
	}
	
	/**
	 * 将 JSON 的值填充到指定类的属性中,一般这个类为 JavaBean.<br>
	 * json key 与类属性名匹配(区分大小写).<br>
	 * 且属性只支持一些常见类型,如果不是则会匹配失败
	 * @param type 类的类型,例如 JSONObject.class
	 * @return type 所对应类的对象.
	 */
	public <T>T convert(Class<T> type) {
		Constructor<T> constructor = null;
		try {
			constructor = type.getDeclaredConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			Log.printErr("JSON转Class,在获取构造方法时出错: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		if (constructor == null) {
			Log.printErr("JSON转Class,此类没有无参构造方法!");
		} else {
			T obj = null;
			try {
				obj = constructor.newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Log.printErr("JSON转Class,在创建对象时出错: " + e.getMessage());
				e.printStackTrace();
				return null;
			}
			
			// 将 json 中的数据填充到 bean 中
			Field[] fs = type.getDeclaredFields();
			Set<Entry<String, String>> entrys = jsons.entrySet();
			for (Field f : fs) {
				String name = f.getName();
				for (Entry<String, String> entry : entrys) {
					// 匹配 JSON
					if (entry.getKey().equals(name)) {
						f.setAccessible(true);
						Class<?> t = f.getType();
						try {
							String v = entry.getValue();
							if (t == String.class) {
								f.set(obj, v);
							} else if (t == short.class  || t == Short.class) {
								f.set(obj, Short.parseShort(v));
							} else if (t == int.class	 || t == Integer.class) {
								f.set(obj, Integer.parseInt(v));
							} else if (t == long.class	 || t == Long.class) {
								f.set(obj, Long.parseLong(v));
							} else if (t == float.class  || t == Float.class) {
								f.set(obj, Float.parseFloat(v));
							} else if (t == double.class || t == Double.class) {
								f.set(obj, Double.parseDouble(v));
							} else if (t == boolean.class|| t == Boolean.class) {
								f.set(obj, Boolean.parseBoolean(v));
							} else if (t == StringBuffer.class) {
								f.set(obj, new StringBuffer(v));
							} else if (t == StringBuilder.class) {
								f.set(obj, new StringBuilder(v));
							}
						} catch (IllegalArgumentException | IllegalAccessException e) {
							Log.printErr("JSON转Class,在给属性赋值时出错: " + e.getMessage());
							e.printStackTrace();
							return null;
						}
					}
				}
			}
			return obj;
		}
		return null;
	}
	
	/**
	 * 是否有指定数据.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 键
	 * @return 有此键
	 */
	public boolean has(String name) { return jsons.containsKey(name); }
	
	/**
	 * 获取指定键对应的值,以String的形式
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 键
	 * @return 值
	 */
	public String getString(String name) { return jsons.get(name); }
	
	/**
	 * 获取指定键对应的值,以 short 的形式
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 键
	 * @return 值
	 */
	public short getShort(String name) { return has(name) ? Short.parseShort(jsons.get(name)) : null; }
	
	/**
	 * 获取指定键对应的值,以 int 的形式
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 键
	 * @return 值
	 */
	public int getInt(String name) { return has(name) ? Integer.parseInt(jsons.get(name)) : null; }
	
	/**
	 * 获取指定键对应的值,以 long 的形式
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 键
	 * @return 值
	 */
	public long getLong(String name) { return has(name) ? Long.parseLong(jsons.get(name)) : null; }
	
	/**
	 * 获取指定键对应的值,以 float 的形式
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 键
	 * @return 值
	 */
	public double getFloat(String name) { return has(name) ? Float.parseFloat(jsons.get(name)) : null; }
	
	/**
	 * 获取指定键对应的值,以 double 的形式
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 键
	 * @return 值
	 */
	public double getDouble(String name) { return has(name) ? Double.parseDouble(jsons.get(name)) : null; }
	
	/**
	 * 获取指定键对应的值,以 Boolean 的形式
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 键
	 * @return 值
	 */
	public boolean getBoolean(String name) { return has(name) ? Boolean.parseBoolean(jsons.get(name)) : null; }
	
	/**
	 * 获取指定键对应的值,以 byte 的形式
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 键
	 * @return 值
	 */
	public byte getByte(String name) { return has(name) ? Byte.parseByte(jsons.get(name)) : null; }
	
	/**
	 * 获取指定键对应的值,以 char 的形式
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 键
	 * @return 值
	 */
	public char getChar(String name) { return has(name) ? jsons.get(name).charAt(0) : null; }
	
	/** @return 当前对象数据格式是否为JSONObject */
	public boolean isJson() { return isJson; }
	
	/** @return 当前对象的源JSON数据 */
	@Override public String toString() { return json; }
}
