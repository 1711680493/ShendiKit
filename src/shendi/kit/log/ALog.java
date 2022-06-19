package shendi.kit.log;

import java.io.Closeable;
import java.util.HashMap;

/**
 * 日志缓存类,实现此类以扩展.<br>
 * 拥有缓存池,以解决多线程使用单对象造成原子性的问题.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public abstract class ALog implements Closeable {
	/** 当前操作名称 */
	protected String name;
	/** 当前操作的日志信息的缓存池,key为线程 */
	protected HashMap<Thread, StringBuilder> bs = new HashMap<>();
	/** 日志是否显示在控制台 */
	protected boolean isLog = true;
	/** 标题 */
	protected String title;
	
	public ALog(String name) {
		this.name = name;
		title = initTitle(name);
	}
	
	/** @return 日志信息的初始缓存大小 */
	protected abstract int initSize();
	
	/**
	 * @param name 操作名称
	 * @return 日志信息的标题
	 */
	protected abstract String initTitle(String name);
	
	/**
	 * 格式化打印日志.
	 * @param log 日志信息
	 * @param objs 格式化参数 
	 */
	public void log(Object log, Object... objs) {
		String fl = format(log, objs);
		
		// 用于调试日志, 非调试模式则不做任何操作
		if (fl == null) return;
		
		Thread t = Thread.currentThread();
		
		if (!bs.containsKey(t)) {
			StringBuilder b = new StringBuilder(initSize());
			b.append(title);
			b.append("\r\n");
			b.append(fl);
			bs.put(t, b);
		} else bs.get(t).append(fl);
		
		if (isLog) print(fl);
	}
	
	/** 将当前日志缓存提交到文件中 */
	public void commit() {
		Thread c = Thread.currentThread();
		if (!bs.containsKey(c)) return;
		save(bs.remove(c).toString());
	}
	
	/**
	 * 格式化日志信息.<br>
	 * 实现可使用 Log.log(); 其中isWrite参数需为false(不输出到日志文件).
	 * @param log 日志信息
	 * @param objs 格式化参数
	 * @return 格式化后的日志信息
	 */
	protected abstract String format(Object log, Object... objs);
	
	/**
	 * 打印日志信息到控制台.
	 * @param log 日志信息
	 */
	protected abstract void print(String log);
	
	/**
	 * 保存日志信息
	 * @param log 日志缓存,要保存的日志信息
	 */
	protected abstract void save(String log);
	
	/**
	 * 日志是否在控制台显示,true显示.
	 * @param isLog 是否显示在控制台
	 */
	public void setIsLog(boolean isLog) { this.isLog = isLog; }
	
	/** @return 缓存池 */
	public HashMap<Thread, StringBuilder> getPool() { return bs; }
	
	/** 仅仅为了简化提交缓存操作,实际调用commit()函数,支持 try...with...resource 的方式,执行此函数后还可以继续使用. */
	public void close() { commit(); }
	
}
