package shendi.kit.exception;

/**
 * 没有端口错误,通常在服务端抛出.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class NoPortError extends Error {
	private static final long serialVersionUID = 7442521764328833368L;
	
	public NoPortError(String info) { super(info); }

}
