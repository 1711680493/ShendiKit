package shendi.kit.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shendi.kit.log.LogFactory.LogInfo;
import shendi.kit.log.interpreter.LogCreate;

/**
 * 日志管理类.<br>
 * 用于从日志中提取有用的信息等.<br>
 * 日志的表示形式为 {@link LogInfo}
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since ShendiKit 1.0
 * @see Log
 * @see LogFactory
 */
public class LogManager {
	/** 单例模式 */
	private static LogManager manager = new LogManager();
	
	/**
	 * 获取所有的日志文件.<br>
	 * 将日志文件以 ArrayList&lt;File&gt; 的形式返回
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return null则代表日志文件夹不存在
	 */
	public List<File> getLogs() {
		//获取所有的日志文件
		File file = Log.getSavePath();
		if (file.exists()) { return Arrays.asList(file.listFiles()); }
		return null;
	}
	
	/**
	 * 获取 List 中所有 File 的日志.<br>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param logs 包含日志文件的 List
	 * @return 表示日志的List数组
	 * @see #getLog(File)
	 */
	public List<LogInfo> getLogs(List<File> logs) {
		List<LogInfo> infos = new ArrayList<>();
		logs.forEach(file -> infos.addAll((getLog(file))));
		return infos;
	}
	
	/**
	 * 获取指定文件的日志信息.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param file 指定的文件
	 * @return 日志信息列表
	 */
	public List<LogInfo> getLog(File file) { return readFile(file,null); }
	
	/**
	 * 获取指定文件的普通日志信息.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param file 指定的文件
	 * @return 普通信息列表
	 */
	public List<LogInfo> getInfo(File file) { return readFile(file,LogFactory.getLogFactory().LOG_INFO_I); }
	
	/**
	 * 获取指定文件的警报日志信息.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param file 指定的文件
	 * @return 警报信息列表
	 */
	public List<LogInfo> getAlarm(File file) { return readFile(file,LogFactory.getLogFactory().LOG_ALARM_I); }
	
	/**
	 * 获取指定文件的错误日志信息.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param file 指定的文件
	 * @return 错误信息列表
	 */
	public List<LogInfo> getError(File file) { return readFile(file,LogFactory.getLogFactory().LOG_ERROR_I); }
	
	/**
	 * 读取日志文件,根据指定的解释器进行处理.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param file 日志文件.
	 * @param create 指定的解释器
	 * @return 经过解释器处理的日志列表
	 */
	private List<LogInfo> readFile(File file,LogCreate create) {
		List<LogInfo> infos = new ArrayList<>();
		if (file.exists()) {
			BufferedReader read = null;
			try {
				read = new BufferedReader(new FileReader(file));
				String info;
				while ((info = read.readLine()) != null) {
					//创建错误信息
					LogInfo log = LogFactory.getLogFactory().getLog(info,create);
					if (log != null) infos.add(log);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Log.printErr("获取日志信息失败");
			} catch (IOException e) {
				e.printStackTrace();
				Log.printErr("读取日志信息失败");
			} finally {
				try {
					if (read != null) read.close();
				} catch (IOException e) {
					e.printStackTrace();
					Log.printErr("读取日志信息出错,关闭流失败" + e.getMessage());
				}
			}
		}
		return infos;
	}
	
	/**
	 * 获取此类唯一的实例.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return LogManager
	 */
	public static LogManager getManager() {
		return manager;
	}
}
