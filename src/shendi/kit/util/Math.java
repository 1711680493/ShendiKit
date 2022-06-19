package shendi.kit.util;

import java.math.BigDecimal;

/**
 * 一些计算操作的类.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since 1.1
 */
public class Math {
	
	/**
	 * 单位转换,整数形式.
	 * <pre>
	 * String[] names = {"厘米", "分米", "米"};
	 * String s = unitConvert(names, 10, 10);
	 * s == "1分米"
	 * 
	 * String s = unitConvert(names, 10, 1011);
	 * s == "10米 1分米 1厘米"
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param names		单位名,例如 {"厘米", "分米", "米"}
	 * @param decimal	进制,例如厘米,分米,米是十进制,10
	 * @param size		第一个单位的大小(上面例子为厘米)
	 * @return 转换后的字符串表示形式
	 */
	public static String unitConvert(String[] names, long decimal, long size) {
		if (names == null) return null;
		if (size < decimal) return size + names[0];
		else if (size == decimal) return 1 + names[1];
		
		StringBuilder data = new StringBuilder(20);
		int len = names.length - 1;
		for (int i = 0; i < names.length; i++) {
			if (size == 0) break;
			
			if (i == len) {
				data.insert(0, ' ');
				data.insert(0, names[i]);
				data.insert(0, size);
				break;
			}
			
			long d = size % decimal;
			if (d != 0) {
				data.insert(0, ' ');
				data.insert(0, names[i]);
				data.insert(0, d);
			}
			size -= d;
			size /= decimal;
		}
		return data.toString();
	}
	
	/**
	 * 单位转换,小数形式.
	 * <pre>
	 * String[] names = {"厘米", "分米", "米"};
	 * String s = unitConvert(names, 10.0, 10);
	 * s == "1.0分米"
	 * 
	 * String s = unitConvert(names, 10.0, 1011);
	 * s == "10.0米 1.0分米 1.0厘米"
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param names		单位名,例如 {"厘米", "分米", "米"}
	 * @param decimal	进制,例如厘米,分米,米是十进制,10
	 * @param size		第一个单位的大小(上面例子为厘米)
	 * @return 转换后的字符串表示形式
	 */
	public static String unitConvert(String[] names, double decimal, double size) {
		if (names == null) return null;
		if (size < decimal) return size + names[0];
		else if (size == decimal) return 1.0 + names[1];
		
		StringBuilder data = new StringBuilder(20);
		int len = names.length - 1;
		for (int i = 0; i < names.length; i++) {
			if (size == 0) break;
			
			if (i == len) {
				data.insert(0, ' ');
				data.insert(0, names[i]);
				data.insert(0, size);
				break;
			}
			
			double d = size % decimal;
			if (d != 0) {
				data.insert(0, ' ');
				data.insert(0, names[i]);
				data.insert(0, d);
			}
			size -= d;
			size /= decimal;
		}
		return data.toString();
	}
	
	/**
	 * 计算指定字符在指定长度中可排列的数量.<br>
	 * 例如两个字符,最长长度2,能组成的排列结果为: 0,1,00,01,10,11
	 * <br>
	 * 创建时间 2022年6月19日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param charLen		字符列表数量
	 * @param composeLen	组成的最长长度
	 * @return 可排列数量
	 */
	public static BigDecimal charLenComposeNum(int charLen, int composeLen) {
		/*
			计算指定字符的指定位数可排列结果数量
			公示: x=字符数量: x^1+x^2+...+x^最大长度
			例如三个字符能组成的排列结果: 3+3^2+3^3=39
		 */
		BigDecimal num = new BigDecimal(0);
		BigDecimal len = new BigDecimal(charLen);
		
		for (int i = 1; i <= composeLen; i++) {
			num = num.add(len.pow(i));
		}
		
		return num;
	}
	
}
