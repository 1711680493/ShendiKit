package shendi.kit.encrypt;

/**
 * 加一算法,给每一个字节+1.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class AddOneEncrypt implements Encrypt {

	@Override public byte[] encrypt(byte[] data) {
		byte[] cData = new byte[data.length];
		for (int i = 0;i < data.length;i++) {
			cData[i] = (byte) (data[i] + 1);
		}
		return cData;
	}

	@Override public byte[] decode(byte[] data) {
		byte[] cData = new byte[data.length];
		for (int i = 0;i < data.length;i++) {
			data[i] = (byte) (data[i] - 1);
		}
		return cData;
	}

}
