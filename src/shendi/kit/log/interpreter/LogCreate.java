package shendi.kit.log.interpreter;

import java.util.HashMap;

import shendi.kit.log.LogFactory;
import shendi.kit.log.LogFactory.LogInfo;
import shendi.kit.log.LogFactory.LogType;
import shendi.kit.time.TimeUtils.Time;

/**
 * Log解释器,用于将一条Log解释成便于使用的 {@link LogInfo}.<br>
 * 此类提供了解释日志的方法,根据指定的日志类型.<br>
 * 继承此类的都是解释器(已定义规则的)
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since ShendiKit 1.0
 * @see LogInterpreter
 * @see LogInfoInterpreter
 * @see LogAlarmInterpreter
 * @see LogErrorInterpreter
 */
public abstract class LogCreate {
	/** 日志类型解释器 */
	private LogTypeCreate logType;
	
	/** 日志时间解释器 */
	private LogTimeCreate logTime;
	
	/** 初始化一个日志应有的解释器 */
	public LogCreate() {
		//初始化日志类型解释器
		HashMap<String,LogType> types = new HashMap<>();
		types.put("Info", LogType.Info);
		types.put("Alarm", LogType.Alarm);
		types.put("Error", LogType.Error);
		logType = new LogTypeInterpreter(types);
		//初始化时间解释器
		logTime = new LogTimeInterpreter();
	}
	
	/**
	 * 解释一个字符串为一个 LogInfo 对象
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param info 日志信息
	 * @return 解析的指定内容
	 */
	public abstract LogInfo interpreterLog(String logResource);
	
	/**
	 * 用于解释一个日志,根据指定的日志源,以及指定的类型
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param logResource 日志源
	 * @param type 需要的日志类型,此参数为null 则代表不过滤,如果此日志不是此类型则返回null
	 * @return 如果日志不是正确的或者不是所需的则返回null,否则返回 {@link LogInfo}
	 */
	LogInfo interpreterLog(String logResource,LogType type) {
		/* 一个有效日志的信息长度应不小于 20,并且第一个文字应为[ */
		if (logResource == null || logResource.length() < 20 || !logResource.startsWith("[")) return null;
		String info = logResource;
		/* 截取类型 (第一个字符到 第一个 ] 之间) */
		LogType t = logType.logType(info.substring(1,info.indexOf(']')));
		//如果不是指定类型则返回
		if (type != null && type != t) { return null; }
		//将类型那段的字符串删除
		info = info.substring(info.indexOf(']') + 1).trim();
		/* 截取时间 */
		Time time = logTime.logTime(info.substring(1,info.indexOf(']')));
		//移除字符串
		info = info.substring(info.indexOf(']') + 1).trim();
		/* 解析类 方法 行 以及日志,这里不用解释器 */
		String callInfo = info.substring(0, info.indexOf('>')).trim();
		String[] callInfos = callInfo.split("\\.");
		//倒数第二个是方法名
		String method = callInfos[callInfos.length - 2];
		//倒数第一个是行
		int line = Integer.parseInt(callInfos[callInfos.length - 1]);
		//前面一串是类
		String className = callInfo.substring(0,callInfo.indexOf(method) - 1);
		/* 最后得到日志信息 */
		info = info.substring(info.indexOf('>') + 1).trim();
		return LogFactory.getLogFactory().create(logResource, type, time, callInfo, className, method, line,info);
	}
}
