package shendi.kit.console.command;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import shendi.kit.console.Console;
import shendi.kit.log.Log;

/**
 * 代表一个命令,实际内容可以为字段,可以为函数.<br>
 * <br>
 * 同样,创建此类对象需要字段/函数,并且需要对应的对象.<br>
 * <br>
 * 支持自定义命令,通常用于控制台内置命令,继承此类,重写 execute 函数就可以肆意发挥.<br>
 * 可参考 ExecuteCommand 类.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.1
 * @see Console
 * @see ExecuteCommand
 */
public class Command {
	
	/** 命令所在类 */
	private java.lang.Class<?> clazz;
	
	/** 命令为字段 */
	private Field field;
	
	/** 命令为函数 */
	private Method method;
	
	/** 所属的对象 */
	private Object obj;
	
	/** 命令描述 */
	private String info;
	
	/** 命令为函数时,参数为空则传递的空 map */
	private static final HashMap<String, String> NULL_MAP = new HashMap<>();
	
	/**
	 * 用字段和对象创建命令
	 * @param clazz 字段所在类
	 * @param field 字段
	 * @param obj 对象
	 */
	public Command(java.lang.Class<?> clazz, Field field, Object obj, String info) {
		this.clazz = clazz;
		this.field = field;
		this.obj = obj;
		this.info = info;
	}

	/**
	 * 用函数和对象创建命令
	 * @param clazz 方法所在类
	 * @param method 函数
	 * @param obj 对象
	 */
	public Command(java.lang.Class<?> clazz, Method method, Object obj, String info) {
		this.clazz = clazz;
		this.method = method;
		this.obj = obj;
		this.info = info;
	}
	
	/**
	 * 使用绑定的对象执行命令,不带任何参数,返回函数/字段执行结果 toString() 形式.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 命令执行结果
	 */
	public String execute() { return execute(null); }
	
	/**
	 * 使用绑定的对象执行命令,返回函数/字段执行结果 toString() 形式.<br>
	 * 命令为字段,有参数则设置字段值并返回字段,无则只返回字段,其中字段值支持八大基本类型设置值<br>
	 * 命令为函数,有参数则传递,无参则为空map
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param params 命令所用参数
	 * @return 命令执行结果
	 */
	public String execute(HashMap<String, String> params) {
		if (isField()) {
			String val = params == null ? null : params.get("set");
			
			if (val == null) {
				try {
					Object fv = field.get(obj);
					return fv == null ? "null" : fv.toString();
				} catch (IllegalArgumentException | IllegalAccessException e) {
					Log.printErr("控制台执行命令失败,获取字段值出错,字段为: %s.%s", obj.getClass().getName(), field.getName());
				}
			} else {
				// 字段设置只支持 8 大基本类型,其余类型一律直接设置(包括String)
				try {
					java.lang.Class<?> type = field.getType();
					
					if (type == short.class)		field.set(obj, Short.parseShort(val));
					else if (type == int.class)		field.set(obj, Integer.parseInt(val));
					else if (type == long.class)	field.set(obj, Long.parseLong(val));
					else if (type == float.class)	field.set(obj, Float.parseFloat(val));
					else if (type == double.class)	field.set(obj, Double.parseDouble(val));
					else if (type == char.class)	field.set(obj, val.charAt(0));
					else if (type == boolean.class) field.set(obj, Boolean.parseBoolean(val));
					else if (type == byte.class)	field.set(obj, Byte.parseByte(val));
					else							field.set(obj, val);
					
					return val;
				} catch (Exception e) {
					Log.printErr("控制台执行命令失败,设置字段值出错,字段为: %s.%s;值为 %s", obj.getClass().getName(), field.getName(), val);
				}
			}
		} else {
			try {
				if (params == null) return (String) method.invoke(obj, NULL_MAP);
				return (String) method.invoke(obj, params);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				Log.printErr("控制台执行命令失败,函数调用出错,函数为: %s.%s, 异常为: %s", obj.getClass().getName(), method.getName(), e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 当前命令是否为字段,true代表是字段
	 */
	public boolean isField() { return field != null; }
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 当前命令是否为函数,true代表是函数
	 */
	public boolean isMethod() { return method != null; }
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 如果当前命令是函数的话则返回对应函数
	 */
	public Method getMethod() { return method; }
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 如果当前命令是字段的话则返回对应字段
	 */
	public Field getField() { return field; }
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 命令描述
	 */
	public String getInfo() { return info; }

	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 命令所在类
	 */
	public java.lang.Class<?> getClazz() { return clazz; }
	
}
