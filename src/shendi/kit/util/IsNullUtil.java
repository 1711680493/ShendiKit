package shendi.kit.util;

/**
 * 判空工具,也可用于判断一系列数据是否正确.<br>
 * <pre>
 * 通常在写函数的时候会有很多参数
 * 首先会对其进行判断
 * 例如
 * if (account == null || "".equals(account)) {}
 * 
 * 当参数一多,就要写很长的代码,或者重复很多次上述操作
 * 于是此类就这样诞生了
 * 
 * 对于上述操作,判断 null 和 "" 为空的
 * 可以直接通过 {@link #strsIsNull(String...)} 来判断
 * 例如
 * String account = null;
 * String password = "hello";
 * IsNullUtil.strsIsNull(account, password)
 * == true
 * 
 * account = "world";
 * IsNullUtil.strsIsNull(account, password)
 * == false
 * 
 * 当然,也可以自己设定判空条件,使用 {@link #isNull(Object[], Object...)} 函数
 * 在写 Java Web 项目通常参数会为 "null"
 * if (IsNullUtil.isNull(new String[] {"", "null"}, account, password)) {
 * 	...
 * }
 * </pre>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since 1.1
 */
public class IsNullUtil {
	
	/** 字符串为空的条件 */
	public static final String[] STR_NULL = {""};
	
	/**
	 * 通过指定的条件判断元素集合是否正确.<br>
	 * 首先会进行判 null 操作,不为null则通过给定的错误条件集合判断.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param nul 元素不正确的条件集合,例如字符串判空则为 "".
	 * @param objs 要被判断的元素集合
	 * @return 元素是否正确/为空,true则代表元素集合中有元素不正确.
	 */
	public static boolean isNull(Object[] nul,Object... objs) {
		if (nul == null) return false;
		if (objs == null) return true;
		
		for (Object o : objs) {
			if (o == null) return true;
			for (Object n : nul) {
				if (o.equals(n)) return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断字符串集合的元素是否为空.<br>
	 * 等价于 isNull(IsNullUtil.STR_NULL, (Object[]) strs);
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param strs 要被判断的字符串可变参数
	 * @return 集合内元素是否为空
	 */
	public static boolean strsIsNull(String... strs) {
		if (strs == null) return true;
		return isNull(STR_NULL, (Object[]) strs);
	}
	
}
