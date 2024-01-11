package shendi.kit.id;

import java.util.Random;

/**
 * 随机字串生成工具.
 * <br>
 * 创建时间 2024年1月10日
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 */
public class RanId {

	/** 随机数生成器 */
	public static final Random RANDOM = new Random();
	
	/** 字符 0-9a-zA-Z */
	public static final char[] RANDOM_CHAR = {
			'0','1','2','3','4','5','6','7','8','9',
			'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m',
			'Q','W','E','R','T','Y','U','I','O','P','A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M'};

	/**
	 * 从给定的范围生成随机数,包装 Random.nextInt();
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param bound 范围
	 * @return 随机数
	 */
	public static int ranNum(int bound) {
		return RANDOM.nextInt(bound);
	}
	
	/**
	 * 生成指定位数的随机码,String类型.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param len 随机码长度
	 * @return 指定位数的随机码,值只包含 {@link #RANDOM_CHAR}
	 */
	public static String ranCodeToString(int len) {
		return String.valueOf(ranCode(len));
	}
	
	/**
	 * 生成指定位数的随机码.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param len 随机码长度
	 * @return 指定位数的随机码,值只包含 {@link #RANDOM_CHAR}
	 */
	public static char[] ranCode(int len) {
		char[] cs = new char[len];
		for (int i = 0; i < len; i++) {
			cs[i] = RANDOM_CHAR[RANDOM.nextInt(RANDOM_CHAR.length)];
		}
		return cs;
	}
	
}
