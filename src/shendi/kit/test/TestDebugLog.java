package shendi.kit.test;

import shendi.kit.log.DebugLog;
import shendi.kit.time.TimeUtils;

/**
 * 测试调试级日志缓存.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class TestDebugLog {
	public static void main(String[] args) {
		
		DebugLog log = new DebugLog("测试调试级日志");
		
		log.log("Start-开始");
		
		log.log("1. Start get time-开始获取时间");
		long time = System.currentTimeMillis();
		log.log("Time is %s", TimeUtils.getFormatTime().getString(time));
		
		log.log("2. Time is ok-获取到的时间是否正确");
		if (time < 1000000) {
			log.log("Time no ok, < 1000000");
		}
		log.log("Time ok, > 1000000");
		
		log.log("End-结束");
		
		log.commit();
	}
}
