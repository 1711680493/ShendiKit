package shendi.kit.exception;

/**
 * 当数值不正确时抛出.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class NumberException extends RuntimeException {
	private static final long serialVersionUID = -7024664166205001125L;
	
	public NumberException(String info) { super(info); }
}
