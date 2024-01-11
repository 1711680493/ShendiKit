package shendi.kit.util;

import java.util.regex.Pattern;

/**
 * 正则工具类.
 * <br>
 * 创建时间 2024年01月10日
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 */
public class PatternUtil {
	
	/** 匹配数字的正则 */
	public static final Pattern NUMBER = Pattern.compile("[0-9]*");
	/** uri 匹配数字,字母,斜杠,点 */
	public static final Pattern URI = Pattern.compile("[a-zA-Z0-9/.]*");
	/** 匹配邮箱的正则 */
	public static final Pattern EMAIL = Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
	/** 匹配金额的正则 */
	public static final Pattern MONEY = Pattern.compile("^-?\\d+(\\.\\d{1,2})?$");
	/** 匹配手机号的正则 */
	public static final Pattern PHONE = Pattern.compile("^1(3|4|5|6|7|8|9)\\d{9}$");
	
}
