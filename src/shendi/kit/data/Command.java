package shendi.kit.data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import shendi.kit.log.Log;

/**
 * 代表一个命令,实际内容可以为字段,可以为函数.<br>
 * <br>
 * 同样,创建此类对象需要字段/函数,并且需要对应的对象.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
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
	 * 使用绑定的对象执行方法/字段.toString().
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 方法/字段的结果
	 */
	public String execute() {
		if (isField()) {
			try {
				return field.get(obj).toString();
			} catch (IllegalArgumentException | IllegalAccessException e) {
				StringBuilder build = new StringBuilder(20);
				build.append("控制台在执行命令时出错,命令字段为: ");
				build.append(obj.getClass().getName());
				build.append('.'); build.append(field.getName());
				Log.printErr(build);
			}
		} else {
			try {
				return (String) method.invoke(obj);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				StringBuilder build = new StringBuilder(20);
				build.append("控制台在执行命令时出错,命令函数为: ");
				build.append(obj.getClass().getName());
				build.append('.'); build.append(method.getName());
				Log.printErr(build);
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
