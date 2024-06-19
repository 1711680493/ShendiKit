package shendi.kit.util;

/**
 * 信息工具类.
 * <br>
 * 创建时间 2024年6月19日
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 */
public class InfoUtil {

	/**
	 * 信息脱敏(指定保留前几位与后几位,其余全部以*代替)
	 * @param data 数据
	 * @param sLen 从第几位开始脱敏
	 * @param eLen 脱敏到倒数第几位结束
	 * @return 脱敏后的信息
	 */
	public static String priv(String data, int sLen, int eLen) {
		return priv(data, "*", sLen, eLen);
	}
	
	/**
	 * 信息脱敏(指定保留前几位与后几位,其余全部以脱敏字符串代替)
	 * @param data 数据
	 * @param priv 脱敏的字符串,如*等
	 * @param sLen 从第几位开始脱敏
	 * @param eLen 脱敏到倒数第几位结束
	 * @return 脱敏后的信息
	 */
	public static String priv(String data, String priv, int sLen, int eLen) {
		if (data == null) return null;
		if (sLen == 0 && eLen == 0) return StringUtil.repeat(priv, data.length());
		
		int allLen = data.length(),
			encLen = sLen + eLen;
		if (encLen > allLen) return StringUtil.repeat(priv, allLen);
		
		StringBuilder result = new StringBuilder();
		result.append(data.substring(0, sLen))
			.append(StringUtil.repeat(priv, allLen - encLen))
			.append(data.substring(allLen - eLen, allLen));
		
		return result.toString();
	}
	
}
