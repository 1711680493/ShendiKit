package shendi.kit.log.interpreter;

import shendi.kit.log.LogFactory.LogInfo;
import shendi.kit.log.LogFactory.LogType;

/**
 * 日志错误信息解释器.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since ShendiKit 1.0
 */
public class LogErrorInterpreter extends LogCreate {

	@Override
	public LogInfo interpreterLog(String logResource) {
		return interpreterLog(logResource, LogType.Error);
	}

}
