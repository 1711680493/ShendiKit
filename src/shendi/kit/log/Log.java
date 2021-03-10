package shendi.kit.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import shendi.kit.path.PathFactory;
import shendi.kit.time.TimeUtils;
import shendi.kit.time.TimeUtils.TimeDisposal;

/**
 * 日志类,拥有打印日志的功能,并且可以把日志写在文件里.<br>
 * 如果需要性能,您应该将日志设置为控制台不显示 {@link #setIsLog(boolean)}.<br>
 * 提供了解析日志的功能,请参考 LogManager.<br>
 * Log save path as Project root/logs.
 * {@link #printDebug(Object, Object...)} 打印调试日志<br>
 * {@link #print(Object, Object...)} 打印普通日志<br>
 * {@link #printAlarm(Object, Object...)} 打印警报日志<br>
 * {@link #printExcept(Object, Object...)} 打印异常日志<br>
 * {@link #printErr(Object, Object...)} 打印错误日志
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @since ShendiKit 1.0
 * @version 1.0
 * @see LogManager
 */
public final class Log {
	/** 是否开启日志 默认开启 true */
	private static boolean isLog = true;
	/** 是否开启调试日志输出,默认true */
	private static boolean isLogDebug = true;
	/** 是否开启信息日志输出,默认true */
	private static boolean isLogInfo = true;
	/** 是否开启警报日志输出,默认true */
	private static boolean isLogAlarm = true;
	/** 是否开启异常日志输出,默认true */
	private static boolean isLogException = true;
	/** 是否开启错误日志输出,默认true */
	private static boolean isLogError = true;
	
	/** 日志文件保存路径 默认为当前项目路径 */
	private static File savePath;
	
	/** 上一次的时间戳 */
	private static long upTime;
	
	/** 当前日志文件路径 */
	private static String currentPath;
	
	/** 日志默认长度,合理设置此参数可提高性能表现 */
	private static int logDefSize = 500;
	
	/** 默认调用层级,4 */
	public static final int CALL_LEVEL_DEF = 4;
	/** 日志调用层级,7 */
	public static final int CALL_LEVEL_LOG = 7;
	
	static {
		// 获取文件路径
		savePath = new File(PathFactory.getPath(PathFactory.PROJECT, "") + File.separatorChar + "logs");
		
		if (!savePath.exists()) savePath.mkdirs();
		
		// 初始化上一次时间戳为今天0点
		upTime = TimeDisposal.getToTime(-1, -1, -1, 0, 0, 0, 0);
		
		currentPath = savePath.getPath() + File.separatorChar + TimeUtils.getFormatTime(TimeUtils.DATE).getString(upTime);
	}
	
	/**
	 * 格式化输出调试日志,参考 String.format().
	 * @param log 格式化的日志信息
	 * @param objs 格式化参数
	 */
	public static void printDebug(Object log, Object... objs) {
		String str = log(log, LogFactory.LEVEL_DEBUG, objs);
		if (isLog && isLogDebug) System.out.print(str);
	}
	
	/**
	 * 格式化输出普通日志,参考 String.format().
	 * @param log 格式化的日志信息
	 * @param objs 格式化参数
	 */
	public static void print(Object log, Object... objs) {
		String str = log(log, LogFactory.LEVEL_INFO, objs);
		if (isLog && isLogInfo) System.out.print(str);
	}
	
	/**
	 * 格式化输出警报日志,参考 String.format().
	 * @param log 格式化的日志信息
	 * @param objs 格式化参数
	 */
	public static void printAlarm(Object log, Object... objs) {
		String str = log(log, LogFactory.LEVEL_ALARM, objs);
		if (isLog && isLogAlarm) System.out.print(str);
	}
	
	/**
	 * 格式化输出异常日志,参考 String.format().
	 * @param log 格式化的日志信息
	 * @param objs 格式化参数
	 */
	public static void printExcept(Object log, Object... objs) {
		String str = log(log, LogFactory.LEVEL_EXCEPTION, objs);
		if (isLog && isLogException) System.err.print(str);
	}
	
	/**
	 * 格式化输出错误日志,参考 String.format().
	 * @param log 格式化的日志信息
	 * @param objs 格式化参数
	 */
	public static void printErr(Object log, Object... objs) {
		String str = log(log, LogFactory.LEVEL_ERROR, objs);
		if (isLog && isLogError) System.err.print(str);
	}
	
	/**
	 * 将日志信息格式化写入到文件中,根据指定日志类型,返回格式化后的日志信息
	 * @param log 日志信息的格式化表现形式,参考String.format()
	 * @param type 日志类型,将在此条日志首部,例如[Info]
	 * @param objs 格式化的参数
	 * @return 格式化后的字符串形式
	 */
	public static String log(Object log, String type, Object... objs) {
		return log(log, type, true, CALL_LEVEL_LOG, objs);
	}
	
	/**
	 * 将日志信息格式化写入到文件中,根据指定日志类型,返回格式化后的日志信息
	 * @param log 日志信息的格式化表现形式,参考String.format()
	 * @param type 日志类型,将在此条日志首部,例如[Info]
	 * @param isWrite 是否输出到日志文件中
	 * @param callLevel 调用层级,用以确定何类调用
	 * @param objs 格式化的参数
	 * @return 格式化后的字符串形式,最后两个字符为换行符\r\n
	 */
	public static String log(Object log, String type, boolean isWrite, int callLevel, Object... objs) {
		String format = log.toString();
		if (objs != null && objs.length > 0) format = String.format(format, objs);
		return log(format, type, isWrite, callLevel);
	}
	
	/**
	 * 将日志信息写入到文件中,根据指定日志类型,返回格式化后的日志信息
	 * @param log 日志信息的格式化表现形式,参考String.format()
	 * @param type 日志类型,将在此条日志首部,例如[Info]
	 * @param isWrite 是否输出到日志文件中
	 * @param callLevel 调用层级,用以确定何类调用
	 * @return 格式化后的字符串形式,最后两个字符为换行符\r\n
	 */
	public static String log(Object log, String type, boolean isWrite, int callLevel) {
		isNewDay();
		
		StringBuilder str = new StringBuilder(logDefSize);
		str.append(type);
		str.append(' ');
		getString(str, callLevel);
		str.append(log);
		str.append("\r\n");
		
		// 将日志写在日志文件里
		if (isWrite) write(str);
		return str.toString();
	}
	
	/**
	 * 将信息输出到文件中.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param info 信息
	 */
	private static void write(Object info) {
		try (FileWriter writer = new FileWriter(currentPath, true)) {
			writer.write(info.toString());
		} catch (IOException e) {
			System.err.println("日志信息输出到文件中出错,信息为: " + info);
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取堆栈跟踪信息 获取调用者的调用者
	 * @param level 调用层级,第一层为Thread,后面每调用一次加一层
	 * @return 返回堆栈跟踪信息
	 */
	private static StackTraceElement getStackTraceInfo(int level) {
		return Thread.currentThread().getStackTrace()[level];
	}
	
	/**
	 * 将调用者信息写进参数中.
	 * @param str 参数
	 * @param callLevel 调用层级,默认为4,自己封装的时候需要增加,多嵌套一层就+1
	 */
	private static void getString(StringBuilder str, int callLevel) {
		str.append("[");
		str.append(TimeUtils.getFormatTime().getString(System.currentTimeMillis()));
		str.append("] ");
		
		//获取堆栈跟踪信息
		StackTraceElement callInfo = getStackTraceInfo(callLevel);
		
		str.append(callInfo.getClassName());
		str.append('.');
		str.append(callInfo.getMethodName());
		str.append("().");
		str.append(callInfo.getLineNumber());
		str.append("\t\t>");
	}
	
	/** @return 返回调用者信息 */
	private static String getString() {
		StringBuilder b = new StringBuilder();
		getString(b, CALL_LEVEL_DEF);
		return b.toString();
	}
	
	/**
	 * 判断是否为新的一天,是则更换路径.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	private static void isNewDay() {
		// 判断文件夹是否存在
		if (!savePath.exists()) { savePath.mkdirs(); }
		
		if (System.currentTimeMillis() - upTime > 86400000) {
			upTime = TimeDisposal.getToTime(-1, -1, -1, 0, 0, 0, 0);
			currentPath = savePath.getPath() + File.separatorChar + TimeUtils.getFormatTime(TimeUtils.DATE).getString(upTime);
		}
	}
	
	/**
	 * 设置日志是否在控制台可见 true可见 false不可见
	 * @param isLog true 可见,false 不可见
	 */
	public static void setIsLog(boolean isLog) {
		System.err.println(getString() + "设置了日志是否控制台可见:" + isLog);
		Log.isLog = isLog;
	}
	
	/** @return 是否在控制台显示调试日志 */
	public static boolean isLogDebug() { return isLogDebug; }
	/**
	 * 设置 {@link LogFactory#LEVEL_DEBUG} 的日志是否在控制台可见 true可见 false不可见
	 * @param isLog true 可见,false 不可见
	 */
	public static void setIsLogDebug(boolean isLog) {
		System.err.println(getString() + "设置了信息日志是否控制台可见:" + isLog);
		Log.isLogDebug = isLog;
	}
	
	/** @return 是否在控制台显示信息日志 */
	public static boolean isLogInfo() { return isLogInfo; }
	/**
	 * 设置 {@link LogFactory#LEVEL_INFO} 的日志是否在控制台可见 true可见 false不可见
	 * @param isLog true 可见,false 不可见
	 */
	public static void setIsLogInfo(boolean isLog) {
		System.err.println(getString() + "设置了信息日志是否控制台可见:" + isLog);
		Log.isLogInfo = isLog;
	}
	
	/** @return 是否在控制台显示警报日志 */
	public static boolean isLogAlarm() { return isLogAlarm; }
	/**
	 * 设置 {@link LogFactory#LEVEL_ALARM} 的日志是否在控制台可见 true可见 false不可见
	 * @param isLog true 可见,false 不可见
	 */
	public static void setIsLogAlarm(boolean isLog) {
		System.err.println(getString() + "设置了警报日志是否控制台可见:" + isLog);
		Log.isLogAlarm = isLog;
	}
	
	/** @return 是否在控制台显示异常日志 */
	public static boolean isLogException() { return isLogException; }
	/**
	 * 设置 {@link LogFactory#LEVEL_EXCEPTION} 的日志是否在控制台可见 true可见 false不可见
	 * @param isLog true 可见,false 不可见
	 */
	public static void setIsLogException(boolean isLog) {
		System.err.println(getString() + "设置了异常日志是否控制台可见:" + isLog);
		Log.isLogException = isLog;
	}
	
	/** @return 是否在控制台显示错误日志 */
	public static boolean isLogError() { return isLogError; }
	/**
	 * 设置 {@link LogFactory#LEVEL_ERROR} 的日志是否在控制台可见 true可见 false不可见
	 * @param isLog true 可见,false 不可见
	 */
	public static void setIsLogError(boolean isLog) {
		System.err.println(getString() + "设置了错误日志是否控制台可见:" + isLog);
		Log.isLogError = isLog;
	}
	
	/** @return 日志保存的文件夹 */
	public static File getSavePath() { return savePath; }
	
	/** @param savePath 日志的保存路径. */
	public static void setSavePath(File savePath) { Log.savePath = savePath; }	
	
}
