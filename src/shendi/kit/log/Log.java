package shendi.kit.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import shendi.kit.path.ProjectPath;
import shendi.kit.time.TimeUtils;
import shendi.kit.time.TimeUtils.TimeDisposal;

/**
 * 日志类,拥有打印日志的功能,并且可以把日志写在文件里.<br>
 * 如果需要性能,您应该将日志设置为控制台不显示 {@link #setIsLog(boolean)}.<br>
 * 提供了解析日志的功能,请参考 LogManager.<br>
 * Log save path as Project root/logs.
 * {@link #print(Object)} 打印普通日志<br>
 * {@link #printAlarm(Object)} 打印警报日志<br>
 * {@link #printErr(Object)} 打印错误日志
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @since ShendiKit 1.0
 * @version 1.0
 * @see LogManager
 */
public class Log {
	/** 是否开启日志 默认开启 true */
	private static boolean isLog = true;
	
	/** 日志文件保存路径 默认为当前项目路径 */
	private static File savePath;
	
	/** 上一次的时间戳 */
	private static long upTime;
	
	/** 当前日志文件路径 */
	private static String currentPath;
	
	static {
		// 获取文件路径
		savePath = new File(new ProjectPath().getPath("") + File.separatorChar + "logs");
		
		if (!savePath.exists()) savePath.mkdirs();
		
		// 初始化上一次时间戳为今天0点
		upTime = TimeDisposal.getToTime(-1, -1, -1, 0, 0, 0, 0);
		
		currentPath = savePath.getPath() + File.separatorChar + TimeUtils.getTime().getFormatTime("date").getString(upTime);
	}
	
	/**
	 * 输出普通日志
	 * @param obj 日志信息
	 */
	public static void print(Object obj) {
		isNewDay();
		String str = getString()+obj;
		//日志开启才输出
		if (isLog) {
			//获取堆栈跟踪信息
			System.out.println(str);
		}
		//将日志写在日志文件里
		try (FileWriter writer = new FileWriter(currentPath)){
			writer.write("[Info] "+str+"\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 输出警报日志
	 * @param obj 日志信息
	 */
	public static void printAlarm(Object obj) {
		isNewDay();
		String str = getString()+obj;
		//日志开启才输出
		if (isLog) {
			//获取堆栈跟踪信息
			System.out.println(str);
		}
		//将日志写在日志文件里
		try (FileWriter writer = new FileWriter(currentPath)){
			writer.write("[Alarm] "+str+"\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 输出错误日志
	 * @param obj 日志信息
	 */
	public static void printErr(Object obj) {
		isNewDay();
		String str = getString()+obj;
		//日志开启才输出
		if (isLog) {
			System.err.println(str);
		}
		//将日志写在日志文件里
		try (FileWriter writer = new FileWriter(currentPath)){
			writer.write("[Error] "+str+"\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取堆栈跟踪信息 获取调用者的调用者
	 * @return 返回堆栈跟踪信息
	 */
	private static StackTraceElement getStackTraceInfo() {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		StackTraceElement callInfo = stackTraceElements[4];
		return callInfo;
	}
	
	/**
	 * 获取调用者信息
	 * @return 信息
	 */
	private static String getString() {
		//获取堆栈跟踪信息
		StackTraceElement callInfo = getStackTraceInfo();
		return "["+ TimeUtils.getTime().getFormatTime().getString(new Date()) +"] "+callInfo.getClassName()+"."+callInfo.getMethodName()+"()."+callInfo.getLineNumber()+"\t\t>";
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
			currentPath = savePath.getPath() + File.separatorChar + TimeUtils.getTime().getFormatTime("date").getString(upTime);
		}
	}
	
	/**
	 * 设置日志是否在控制台可见 true可见 false不可见
	 * @param isLog true 可见,false 不可见
	 */
	public static void setIsLog(boolean isLog) {
		System.err.println(getString() + "设置了日志:" + isLog);
		Log.isLog = isLog;
	}
	
	/**
	 * 获取日志的保存路径.
	 * @return 日志保存的文件夹
	 */
	public static File getSavePath() { return savePath; }
	
	/**
	 * 设置日志保存的路径
	 * @param savePath 日志的保存路径.
	 */
	public static void setSavePath(File savePath) { Log.savePath = savePath; }	
	
}
