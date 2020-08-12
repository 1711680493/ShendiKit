package shendi.kit.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
	private static String savePath;
	
//	private int num;
	
	/** 日志保存到本地的输出流 */
	private static FileWriter writer = null;
	
	/** 计时器 用于执行每天更换一个日志文件 */
	private static Timer timer = new Timer();
	
	static {
		//获取文件路径
		savePath = new ProjectPath().getPath("") + File.separatorChar + "logs" + File.separatorChar;
		//开启每天更换流
		timingUpdateWriter();
	}
	
	/**
	 * 输出普通日志
	 * @param obj 日志信息
	 */
	public static void print(Object obj) {
		isDefaultWriter();
		String str = getString()+obj;
		//日志开启才输出
		if (isLog) {
			//获取堆栈跟踪信息
			System.out.println(str);
		}
		//将日志写在日志文件里
		try {
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
		isDefaultWriter();
		String str = getString()+obj;
		//日志开启才输出
		if (isLog) {
			//获取堆栈跟踪信息
			System.out.println(str);
		}
		//将日志写在日志文件里
		try {
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
		isDefaultWriter();
		String str = getString()+obj;
		//日志开启才输出
		if (isLog) {
			System.err.println(str);
		}
		//将日志写在日志文件里
		try {
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
	 * 如果writer等于null 则赋予其默认值
	 */
	private static void isDefaultWriter() {
		if (writer == null) {
			try {
				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				writer = new FileWriter(savePath + TimeUtils.getTime().getFormatTime("date").getString(new Date()),true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 设置日志是否在控制台可见 true可见 false不可见
	 * @param isLog true 可见,false 不可见
	 */
	public static void setIsLog(boolean isLog) {
		System.err.println(getString() + "设置了日志:"+isLog);
		Log.isLog = isLog;
	}
	
	/**
	 * 获取日志的保存路径.
	 * @return 日志保存的文件夹位置.
	 */
	public static String getSavePath() {
		return savePath;
	}
	
	/**
	 * 设置日志保存的路径
	 * @param savePath 日志的保存路径.
	 */
	public static void setSavePath(String savePath) {
		Log.savePath = savePath;
		//关闭之前的流
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//进入垃圾回收
		writer = null;
	}
	
	/**
	 * 定时更换输出流 每天指向一个新的不同的文件
	 */
	private static void timingUpdateWriter() {
		long tomorrow = TimeDisposal.nowToTomorrow();
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("更换流,关闭当前流失败!");
				}
				writer = null;
			}
		},tomorrow,86400000);
	}
	
	/**
	 * 关闭log
	 */
	public static void close() {
		if (timer != null) {
			timer.cancel();
		}
		try {
			//关流
			if (writer != null) {
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
