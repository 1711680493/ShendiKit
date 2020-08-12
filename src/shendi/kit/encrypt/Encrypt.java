package shendi.kit.encrypt;

import shendi.kit.exception.NonsupportDecodeException;

/**
 * 所有加密类的父接口,实现此接口来创建一个新的加密方式.<br>
 * 加密操作不应污染源数据.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public interface Encrypt {
	
	/**
	 * 对指定数据执行加密操作.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data 要加密的数据
	 * @return 被加密的数据
	 */
	byte[] encrypt(byte[] data);
	
	/**
	 * 对指定数据执行解密操作,如果有.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data 被加密的数据
	 * @return 被解密的数据
	 * @throws 当加密类不支持解密时抛出
	 */
	byte[] decode(byte[] data) throws NonsupportDecodeException;
	
}
