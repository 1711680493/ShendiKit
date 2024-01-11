package shendi.kit.test;

import shendi.kit.util.PatternUtil;

/**
 * 测试PatternUtil.
 * <br>
 * 创建时间 2024年1月10日
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 */
public class TestPatternUtil {

	public static void main(String[] args) {
		
//		String[] moneys = "a,0a,-1,1.0,--0,-0.1.1,10.223,10,1.,-1.".split(",");
//		for (String item : moneys) {
//			System.out.println(item + "=" + PatternUtil.MONEY.matcher(item).matches());
//		}
		
		String[] phones = "13333333333,13,-123,11111111111,12333333333".split(",");
		for (String item : phones) {
			System.out.println(item + "=" + PatternUtil.PHONE.matcher(item).matches());
		}
		
	}
	
}
