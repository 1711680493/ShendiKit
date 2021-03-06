package shendi.kit.exception;

/**
 * 当处理Http响应的数据有问题时抛出.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class HttpResponseException extends Exception {
	private static final long serialVersionUID = -7024664166205001125L;

	public HttpResponseException(String str) { super(str); }
	
	public HttpResponseException(Exception e) { super(e); }
	
}
