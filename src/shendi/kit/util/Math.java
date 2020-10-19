package shendi.kit.util;

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
	
}
