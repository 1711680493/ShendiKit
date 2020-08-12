package shendi.kit.encrypt;

import shendi.kit.exception.NonsupportDecodeException;

/**
 * 求和取余加密算法,只可加密,不可逆.<br>
 * 将当前字节的前后(包括自己)相加,取余最大字节长度(-128,127).
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class SumRemainderEncrypt implements Encrypt {

	@Override public byte[] encrypt(byte[] data) {
		if (data == null || data.length <= 2) return null;
		
		int len = data.length - 1;
		byte[] cData = new byte[len + 1];
		
		// 第一个字节加第二个和最后一个
		int num = data[0] + data[1] + data[len];
		cData[0] = (byte) (num < 0 ? num % 128 : num % 127);
		
		for (int i = 1;i < len;i++) {
			num = data[i - 1] + data[i] + data[i + 1];
			cData[i] = (byte) (num < 0 ? num % 128 : num % 127);
		}
		
		//最后一个字节加上前面一个和第一个
		num = data[len] + data[len-1] + data[0];
		cData[len] = (byte) (num < 0 ? num % 128 : num % 127);
		return cData;
	}

	@Override public byte[] decode(byte[] data) throws NonsupportDecodeException {
		throw new NonsupportDecodeException();
	}
	
}
