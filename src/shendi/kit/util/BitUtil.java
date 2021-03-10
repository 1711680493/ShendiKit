package shendi.kit.util;

/**
 * 位工具
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since 1.1
 */
public class BitUtil {

	/**
	 * 获取指定数值占多少位<br>
	 * <pre>
	 * 数字符号位占一位.
	 * sizeOf(0) -> 2
	 * sizeOf(1) -> 2
	 * sizeOf(2) -> 3
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param num
	 * @return bit num
	 */
	public static int sizeOf(long num) {
		int i = 2;
		// 绝对值
		num = java.lang.Math.abs(num);
		
		while ((num >>= 1) != 0) i++;
		
		return i;
	}
	
	/**
	 * 获取指定位数的最大十进制值,不带符号位<br>
	 * <pre>
	 * bitMax(0) -> 0
	 * bitMax(1) -> 1
	 * bitMax(2) -> 3
	 * bitMax(3) -> 7
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param bitNum 位数
	 * @return 位数对应最大十进制值
	 */
	public static long bitMax(int bitNum) {
		if (bitNum < 1) return 0;
		if (bitNum == 1) return 1;
		return (1L << bitNum) - 1;
	}
	
}
