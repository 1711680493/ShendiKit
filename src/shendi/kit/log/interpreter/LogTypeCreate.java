package shendi.kit.log.interpreter;

import shendi.kit.log.LogFactory.LogType;

/**
 * Log类型解释器.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since ShendiKit 1.0
 * @see LogType
 */
public interface LogTypeCreate {
	/**
	 * 解析Log的类型.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param type 日志类型的字符串形式
	 * @return 枚举类型 {@link LogType}
	 */
	LogType logType(String type);
}
