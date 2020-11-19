package shendi.kit.format.json;

import java.util.HashMap;

/**
 * JSON 格式对象.<br>
 * 一个JSON对象格式为 {a:b,b:c}
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.1
 * @since 1.1
 */
public class JSONObject {
	
	/** 当前 json 的数据 */
	private String json;
	
	/** 解析后的键值对集合 */
	private HashMap<String, String> jsons = new HashMap<>();
	
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
	 * 获取指定键对应的值,以 Int 的形式
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 键
	 * @return 值
	 */
	public int getInt(String name) { return Integer.parseInt(jsons.get(name)); }
	
	/**
	 * 获取指定键对应的值,以 Boolean 的形式
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 键
	 * @return 值
	 */
	public boolean getBoolean(String name) { return Boolean.parseBoolean(jsons.get(name)); }
	
	/**
	 * 获取指定键对应的值,以 Double 的形式
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 键
	 * @return 值
	 */
	public double getDouble(String name) { return Double.parseDouble(jsons.get(name)); }
	
	/** @return 当前对象的源JSON数据 */
	public String getJson() { return json; }
	
}
