package shendi.kit.log.interpreter;

import java.util.HashMap;

import shendi.kit.log.LogFactory.LogType;

/**
 * 日志类型解释器,用于解释日志的类型.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since ShendiKit 1.0
 */
public class LogTypeInterpreter implements LogTypeCreate {
	/** 代表一个字符串同等的LogType */
	private final HashMap<String,LogType> types;
	public LogTypeInterpreter(HashMap<String,LogType> types) {
		this.types = types;
	}
	
	
	@Override
	public LogType logType(String type) { return types.get(type); }
}
