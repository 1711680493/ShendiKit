package shendi.kit.log.interpreter;

import shendi.kit.time.Time;

/**
 * Log时间解释器.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since ShendiKit 1.0
 * @see Time
 */
public interface LogTimeCreate {
	/**
	 * 解析Log的时间.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param time 日志时间的字符串形式
	 * @return 时间类型 {@link Time}
	 */
	Time logTime(String time);
}