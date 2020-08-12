package shendi.kit.log.interpreter;

import shendi.kit.time.TimeUtils;
import shendi.kit.time.TimeUtils.Time;

/**
 * 日志时间解释器,用于解释日志的时间.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since ShendiKit 1.0
 */
public class LogTimeInterpreter implements LogTimeCreate {
	
	@Override
	public Time logTime(String time) {
		return TimeUtils.getTime().createTime(time, TimeUtils.getTime().getFormatTime());
	}
}
