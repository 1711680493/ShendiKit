package shendi.kit.encrypt;

/**
 * 通过指定密码来进行加密解密.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class PasswordEncrypt implements Encrypt {
	
	/** 默认的密码 */
	private char[] pwd = "Shendi".toCharArray();
	
	@Override public byte[] encrypt(byte[] data) {
		byte[] cData = new byte[data.length];
		for (int i = 0;i < data.length;i++) {
			cData[i] = (byte) (data[i] ^ pwd[i % pwd.length]);
		}
		return cData;
	}

	@Override public byte[] decode(byte[] data) { return encrypt(data); }

	/**
	 * 设置加密使用的密码,如果不设置则为默认密码.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param pwd 加密使用的密码
	 */
	public void setPassword(Object pwd) { this.pwd = pwd.toString().toCharArray(); }
	
}
