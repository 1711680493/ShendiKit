package shendi.kit.log.interpreter;

import shendi.kit.log.LogFactory.LogInfo;

/**
 * 日志解释器.<br>
 * 用于将一个有效的 符合规则的字符串解析成 LogInfo.<br>
 * 一个有效的日志结构应为 [type] [time] class.method(...).line		&gt;info
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since ShendiKit 1.0
 */
public class LogInterpreter extends LogCreate {
	
	@Override
	public LogInfo interpreterLog(String logResource) {
		return interpreterLog(logResource, null);
	}

}
