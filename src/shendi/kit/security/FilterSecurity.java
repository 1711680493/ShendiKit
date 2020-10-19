package shendi.kit.security;

/**
 * 安全过滤接口.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public interface FilterSecurity {
	
	/**
	 * 过滤的方法
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param info 信息,用于过滤判断
	 * @return 是否需要被过滤,当返回true时,程序应直接返回
	 */
	boolean filter(String info);
}
