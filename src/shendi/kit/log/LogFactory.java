package shendi.kit.log;

import shendi.kit.log.interpreter.LogAlarmInterpreter;
import shendi.kit.log.interpreter.LogCreate;
import shendi.kit.log.interpreter.LogErrorInterpreter;
import shendi.kit.log.interpreter.LogInfoInterpreter;
import shendi.kit.log.interpreter.LogInterpreter;
import shendi.kit.time.TimeUtils.Time;

/**
 * 日志工厂,拥有 {@link LogInfo} 与 {@link LogType} 两个内部类.<br>
 * 通过 {@link #getLogFactory()} 获取此类唯一的对象.<br>
 * 此类持有 日志解释器 信息日志解释器 错误日志解释器的引用.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since ShendiKit 1.0
 * @see LogManager
 * @see LogInfo
 * @see LogCreate
 */
public final class LogFactory {
	/** 此类的唯一对象 */
	private static final LogFactory LOG_FACTORY = new LogFactory();
	
	/** 日志解释器 */
	public final LogInterpreter LOG_I;
	
	/** 日志普通信息解释器 */
	public final LogInfoInterpreter LOG_INFO_I;
	
	/** 日志警报信息解释器 */
	public final LogAlarmInterpreter LOG_ALARM_I;
	
	/** 日志错误信息解释器 */
	public final LogErrorInterpreter LOG_ERROR_I;
	
	/** 给解释器进行初始化 */
	public LogFactory() {
		//初始化
		LOG_I = new LogInterpreter();
		LOG_INFO_I = new LogInfoInterpreter();
		LOG_ALARM_I = new LogAlarmInterpreter();
		LOG_ERROR_I = new LogErrorInterpreter();
	}
	
	/**
	 * 代表一个日志的信息.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @version 1.0
	 * @since ShendiKit 1.0
	 * @see LogType
	 */
	public class LogInfo {
		/**
		 * 日志原本的信息
		 */
		private final String logResource;
		
		/**
		 * 日志类型
		 */
		private final LogType type;
		
		/**
		 * 日志发生时间
		 */
		private final Time time;
		
		/**
		 * 调用信息
		 */
		private final String callInfo;
		
		/**
		 * 触发日志的类
		 */
		private final String className;
		
		/**
		 * 触发日志的函数
		 */
		private final String method;
		
		/**
		 * 在哪一行被触发的
		 */
		private final int line;
		
		/**
		 * 日志信息
		 */
		private final String info;
		
		/**
		 * 创建一条日志信息
		 * @param logResource 日志的全部信息
		 * @param type 日志类型
		 * @param time 日志生成时间
		 * @param callInfo 调用的信息
		 * @param className 调用的类
		 * @param method 调用的方法
		 * @param line 调用所在行
		 * @param info 日志信息
		 */
		LogInfo(String logResource,LogType type,Time time,String callInfo,String className,String method,int line,String info) {
			this.logResource = logResource;
			this.type = type;
			this.time = time;
			this.callInfo = callInfo;
			this.className = className;
			this.method = method;
			this.line = line;
			this.info = info;
		}
		
		/**
		 * 返回当前日志的类型.
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return LogType
		 */
		public LogType getType() { return type; }
		
		/**
		 * 获取日志内容.
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 日志的主要内容.
		 */
		public String getInfo() { return info; }
		
		/**
		 * 获取日志创建时间.
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 日志的创建时间,使用 Time 对象表示
		 */
		public Time getTime() { return time; }
		
		/**
		 * 获取触发日志的类名.
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 日志的调用类名
		 */
		public String getClassName() { return className; }
		
		/**
		 * 获取触发日志的函数
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 日志的调用方法名
		 */
		public String getMethod() { return method; }
		
		/**
		 * 获取触发日志的所在行
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 日志所在行
		 */
		public int getLine() { return line; }
		
		/**
		 * 获取调用信息 包含 类名 方法 行
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 调用信息
		 */
		public String getCallInfo() {return callInfo; }
		
		/**
		 * 获取日志原本形态
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 日志的所有信息
		 */
		public String getLogResource() { return logResource; }

		@Override
		public String toString() {
			return "LogInfo [logResource=" + logResource + ", type=" + type + ", time=" + time + ", callInfo="
					+ callInfo + ", className=" + className + ", method=" + method + ", line=" + line + ", info=" + info
					+ "]";
		}
	}
	
	/**
	 * 代表日志的类型.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @version 1.0
	 * @since ShendiKit 1.0
	 * @see LogInfo
	 */
	public enum LogType {
		/**普通日志*/
		Info,
		/*警报日志*/
		Alarm,
		/**错误日志*/
		Error
	}
	
	/**
	 * 获取此类唯一对象.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return LogFactory object.
	 */
	public static LogFactory getLogFactory() { return LOG_FACTORY; }
	
	/**
	 * 创建一个日志信息.<br>
	 * 听过所有的有效信息来构建
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param logResource 日志原本信息
	 * @param type 日志类型
	 * @param info 日志的信息
	 * @param time 发生时间
	 * @param callInfo 调用的信息
	 * @param className 触发的类
	 * @param method 触发的方法
	 * @param line 触发所在行
	 * @return create new LogInfo object.
	 */
	public LogInfo create(String logResource,LogType type,Time time,String callInfo,String className,String method,int line,String info) {
		return new LogInfo(logResource, type, time, callInfo, className, method, line, info);
	}
	
	/**
	 * 解析日志成便于使用的 LogInfo.<br>
	 * 使用默认的解释器,不过滤日志.<br>
	 * 等价于调用 getLog(log,null);
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param log 日志信息
	 * @return 返回一个被解释后的日志,返回null则代表log不为日志或出错
	 * @see #getLog(String)
	 */
	public LogInfo getLog(String log) { return getLog(log,null); }
	
	/**
	 * 解析日志成便于使用的 LogInfo.<br>
	 * 可以自己指定需要的解释器.<br>
	 * 默认解释器,通过{@link #getLogFactory()} 获取对象来获取解释器.
	 * <table border='1'>
	 * 	<tr>
	 * 		<th>参数名</th>
	 * 		<th>描述</th>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link #LOG_I}</td>
	 * 		<td>默认的解释器,不过滤</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link #LOG_INFO_I}</td>
	 * 		<td>用于获取普通日志的解释器</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link #LOG_ALARM_I}</td>
	 * 		<td>用于获取普通日志的解释器</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link #LOG_ERROR_I}</td>
	 * 		<td>用于获取错误日志的解释器</td>
	 * 	</tr>
	 * </table>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param log 日志信息
	 * @param create 要使用的解释器类型,如果为null,则使用默认的解释器
	 * @return 返回一个被解释后的日志,返回null则代表log不为日志或出错
	 */
	public LogInfo getLog(String log,LogCreate create) {
		if (create == null) return LOG_I.interpreterLog(log);
		return create.interpreterLog(log);
	}
	
}
