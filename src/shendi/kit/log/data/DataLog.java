package shendi.kit.log.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import shendi.kit.path.PathFactory;
import shendi.kit.path.ProjectPath;
import shendi.kit.time.TimeUtils;
import shendi.kit.time.TimeUtils.TimeDisposal;

/**
 * 数据日志类,用于数据持久化,每一个对象都对应于一个单独的文件夹.<br>
 * 每一天都会有一个新的数据文件.<br>
 * 日志文件夹将会在项目根目录生成,具体目录参考 {@link ProjectPath}
 * <br>
 * 创建日期 2021年8月7日 下午12:00:48
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since 1.1
 */
public class DataLog {
	/** 是否开启日志 默认开启 true */
	private boolean isLog = true;
	
	/** 日志文件保存路径 默认为当前项目路径 */
	private File savePath;
	
	/** 上一次 0 点的时间戳,用于切换日志文件 */
	private long upTime;
	
	/** 当前日志文件路径 */
	private String currentPath;
	
	/** 日志默认长度,合理设置此参数可提高性能表现 */
	private int logDefSize = 500;
	
	/** 调用层级,默认2,每多封装一层则+1 */
	public int callLevel = 2;
	
	/** 日志命名,等价于 [数据命名] */
	private String logName;
	
	/**
	 * 初始化数据日志类.
	 * @param name 此数据命名,会在项目根目录生成对应文件夹
	 */
	public DataLog(String name) {
		// 获取文件路径
		savePath = new File(PathFactory.getPath(PathFactory.PROJECT, "") + File.separatorChar + name);
		if (!savePath.exists()) savePath.mkdirs();
		
		// 初始化上一次时间戳为今天0点
		upTime = TimeDisposal.getToTime(-1, -1, -1, 0, 0, 0, 0);
		currentPath = savePath.getPath() + File.separatorChar + TimeUtils.getFormatTime(TimeUtils.DATE).getString(upTime);
		
		this.logName = '[' + name + ']';
	}
	
	/**
	 * 格式化输出普通日志,参考 String.format().
	 * @param log 格式化的日志信息
	 * @param objs 格式化参数
	 */
	public void log(Object log, Object... objs) {
		// 判断日志文件是否需要更改,是否为新的一天
		if (!savePath.exists()) { savePath.mkdirs(); }
		if (System.currentTimeMillis() - upTime > 86400000) {
			upTime = TimeDisposal.getToTime(-1, -1, -1, 0, 0, 0, 0);
			currentPath = savePath.getPath() + File.separatorChar + TimeUtils.getFormatTime(TimeUtils.DATE).getString(upTime);
		}
		
		StringBuilder str = new StringBuilder(logDefSize);
		str.append(logName);
		str.append(" [");
		str.append(TimeUtils.getFormatTime().getString(System.currentTimeMillis()));
		str.append("] ");
		
		//获取堆栈跟踪信息
		StackTraceElement callInfo = Thread.currentThread().getStackTrace()[callLevel];
		str.append(callInfo.getClassName());
		str.append('.');
		str.append(callInfo.getMethodName());
		str.append("().");
		str.append(callInfo.getLineNumber());
		str.append("\t\t>");
		
		if (objs != null && objs.length > 0) log = String.format(log.toString(), objs);
		str.append(log);
		str.append("\r\n");
		
		// 将日志写在日志文件里
		try (FileWriter writer = new FileWriter(currentPath, true)) {
			writer.write(str.toString());
		} catch (IOException e) {
			System.err.println("日志信息输出到文件中出错,信息为: " + str);
			e.printStackTrace();
		}
		
		if (isLog) System.out.print(str);
	}
	
	/**
	 * 设置日志是否在控制台可见 true可见 false不可见
	 * @param isLog true 可见,false 不可见
	 */
	public void setIsLog(boolean isLog) { this.isLog = isLog; }
	
	/** @return 日志保存的文件夹 */
	public File getSavePath() { return savePath; }
	
	/** @param savePath 日志的保存路径. */
	public void setSavePath(File savePath) { this.savePath = savePath; }	
	
}
