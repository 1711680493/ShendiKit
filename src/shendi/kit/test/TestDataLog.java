package shendi.kit.test;

import shendi.kit.log.data.DataLog;

/**
 * 测试数据日志.
 * <br>
 * 创建日期 2021年8月7日 下午12:20:59
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class TestDataLog {
	
	public static void main(String[] args) {
		
		DataLog dl = new DataLog("给日志文件夹命名,文件夹会保存在项目根目录");
		dl.log("日志信息");
		
	}
	
}
