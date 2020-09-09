package shendi.kit.data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 包含一个类的所有属性.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class Class {
	/** 类的所有字段 */
	private Field[] fs;
	/** 类的所有方法 */
	private Method[] ms;
	
	public Class(Field[] fs, Method[] ms) {
		this.fs = fs;
		this.ms = ms;
	}

	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 类的所有字段.
	 */
	public Field[] getFs() { return fs; }
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 类的所有方法
	 */
	public Method[] getMs() { return ms; }
	
}
