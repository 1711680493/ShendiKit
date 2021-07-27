package shendi.kit.encrypt;

import shendi.kit.exception.NonsupportDecodeException;

/**
 * 双密码二分加密算法.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class TwoPasswordEncrypt implements Encrypt {

	/** 默认的双密码1 */
	private char[] pwd1 = "Hack".toCharArray();
	/** 默认的双密码2 */
	private char[] pwd2 = "Shendi".toCharArray();
	
	public TwoPasswordEncrypt() {}
	public TwoPasswordEncrypt(Object pwd1, Object pwd2) { setPassword(pwd1, pwd2); }
	
	/** 10M大小 */
	private int ten = 10485760;
	
	@Override
	public byte[] encrypt(byte[] data) {
		int size = data.length >> 1;
		
		// copy data
		byte[] cData = new byte[data.length];
		
		// 数据量大于 10M 则采用多线程处理
		if (data.length > ten) {
			Thread t1 = new Thread(() -> {
				for (int i = 0; i < size; i++) {
					cData[i] = (byte) (data[i] ^ pwd1[i % pwd1.length]);
				}
			});
			
			Thread t2 = new Thread(() -> {
				for (int i = size; i < data.length;i++) {
					cData[i] = (byte) (data[i] ^ pwd2[i % pwd2.length]);
				}
			});
			
			t1.start();
			t2.start();
			
			try {
				t1.join(10000);
				t2.join(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		} else {
			for (int i = 0; i < size; i++) {
				cData[i] = (byte) (data[i] ^ pwd1[i % pwd1.length]);
			}
		
			for (int i = size; i < data.length;i++) {
				cData[i] = (byte) (data[i] ^ pwd2[i % pwd2.length]);
			}
		}
		return cData;
	}
	
	@Override
	public byte[] decode(byte[] data) throws NonsupportDecodeException { return encrypt(data); }
	
	/**
	 * 设置加密使用的双密码,如果不设置则为默认密码.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param pwd1 加密使用的密码1
	 * @param pwd2 加密使用的密码2
	 */
	public void setPassword(Object pwd1, Object pwd2) {
		this.pwd1 = pwd1.toString().toCharArray();
		this.pwd2 = pwd2.toString().toCharArray();
	}
	
}
