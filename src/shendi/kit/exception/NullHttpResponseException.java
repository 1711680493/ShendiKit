package shendi.kit.exception;

/**
 * 当处理Http响应的数据为空时抛出.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class NullHttpResponseException extends RuntimeException {
	private static final long serialVersionUID = -7024664166205001125L;

	public NullHttpResponseException() { super("在处理Http响应出错,响应数据为空！"); }
	
}
