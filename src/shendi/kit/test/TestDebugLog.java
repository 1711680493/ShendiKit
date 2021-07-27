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
		
		// 将日志输出并保存到文件中
		log.commit();
		// close函数本质就是调用了commit函数,出现仅仅为了简化写法,支持try...with...resource
		log.close();
		
		// 一般情况下为了避免异常都会使用try,为了避免日志没有commit,所以出现了close函数
		// 使用方式可以如下,可以不用编写提交操作(commit)
		try (DebugLog tlog = new DebugLog("测试close")) {
			tlog.log("Start-开始");
			
			tlog.log("1. Start get time-开始获取时间");
			time = System.currentTimeMillis();
			tlog.log("Time is %s", TimeUtils.getFormatTime().getString(time));
			
			throw new RuntimeException("假设发生异常,日志也会输出并保存到文件中");
//			log.log("2. Time is ok-获取到的时间是否正确");
//			if (time < 1000000) {
//				log.log("Time no ok, < 1000000");
//			}
//			log.log("Time ok, > 1000000");
//			
//			log.log("End-结束");
		}
	}
}
