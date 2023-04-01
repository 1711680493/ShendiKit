package shendi.kit.util;

import java.util.regex.Pattern;

/**
 * 字符串工具类.
 * <br>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.1
 */
public class StringUtil {
	
	/**
	 * 字符串去除指定字符串的前后空格.
	 * <br>
	 * 字符串去除指定字符串的前后空格
	 * StringUtil.trimByStr("1  . 2.   3", "."); // 1.2.3
	 * <br>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param str	源字符串
	 * @param c		指定字符串
	 * @return 结果
	 */
	public static String trimByStr(String str, String c) {
		if (str == null || str.length() == 0) return "";
    
        StringBuilder result = new StringBuilder(str.length());
        String[] strs = str.split(Pattern.quote(c));
        
        for (int i = 0; i < strs.length; i++) {
            result.append(strs[i].trim()).append(c);
        }
   
        return result.substring(0, result.length() - c.length());
	}
	
}
