package shendi.kit.exception;

/**
 * 不支持解密异常.<br>
 * 当一个不可逆的加密类试图执行解密方法时出现.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class NonsupportDecodeException extends RuntimeException {
	private static final long serialVersionUID = -7024664166205001125L;

	public NonsupportDecodeException() { super("试图使用一个不支持解密的类的解密方法！"); }
	
}
