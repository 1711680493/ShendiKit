package shendi.kit.exception;

/**
 * 当变量不应小于0但小于0时抛出.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class NumberLessThanZero extends RuntimeException {
	private static final long serialVersionUID = -7024664166205001125L;

	public NumberLessThanZero() { super("数字小于0!"); }
	
}
