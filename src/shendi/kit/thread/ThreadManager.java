package shendi.kit.thread;

import java.util.HashMap;

import shendi.kit.log.Log;
import shendi.kit.util.Entry;

/**
 * 线程管理器.<br>
 * 调用 {@link #open()} 开启线程管理器,将线程添加进管理器后,将监视此线程状态,以及统一管理.<br>
 * <br>
 * 此类最初是为了解决 JDK 的一些未知问题(例如程序运行过久,死循环 try catch 的线程莫名其妙从正常状态变成了等待,代码内无任何wait等导致线程等待的代码)<br>
 * <br>
 * 当将线程以 {@link ThreadType#NO_WATTING} 加入管理器,则此线程将不会进入等待状态(变为等待状态后将会自动中断,抛出错误)
 * <br>创建时间 2021年12月1日 下午2:27:54
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class ThreadManager {
	
	/** 线程管理器是否已打开 */
	private static boolean isOpen = false;
	
	/** 需要被管理的线程列表 [线程命名:[线程:线程类型]] */
	private static HashMap<String, Entry<Thread, ThreadType>> ts = new HashMap<>();
	
	/** 管理线程,守护线程 */
	private static Thread manager;
	
	/** 守护线程的具体执行 */
	private static class DaemonThread extends Thread {
		@Override
		public void run() {
			while (true) {
				if (!isOpen) break;
				try {
					Thread.sleep(5000);
					synchronized (ts) {
						ts.forEach((k, v) -> {
							if (v.value == ThreadType.NO_WATTING) {
								if (v.key.getState() == State.WAITING) {
									v.key.interrupt();
									Log.printAlarm("线程 %s 状态变为了等待,已中断.", k);
								}
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 打开线程管理器.
	 * 创建时间 2021年12月1日 下午3:11:58
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	public static void open() {
		if (isOpen) {
			Log.print("线程管理器已经开启,请勿重复打开.");
			return;
		}
		
		manager = new DaemonThread();
		manager.setDaemon(true);
		manager.start();
		
		isOpen = true;
	}
	
	/** 关闭线程管理器. */
	public static void close() {
		isOpen = false;
	}
	
	/**
	 * 将指定线程添加到线程管理器中,以指定线程管理类型.
	 * 创建时间 2021年12月1日 下午5:07:00
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name		线程命名/描述/标识
	 * @param thread	线程
	 * @param type		线程管理类型
	 */
	public static void add(String name, Thread thread, ThreadType type) {
		synchronized (ts) {
			ts.put(name, new Entry<Thread, ThreadType>(thread, type));
		}
	}
	
	/**
	 * 将指定线程从线程管理器种删除.
	 * @param name 线程名
	 * @return 删除的线程
	 */
	public static Thread del(String name) {
		synchronized (ts) {
			Entry<Thread, ThreadType> r = ts.remove(name);
			if (r == null) return null;
			return r.key;
		}
	}
	
	/**
	 * 线程管理器是否已经打开
	 * 创建时间 2021年12月1日 下午2:14:23
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return true代表打开
	 */
	public static boolean isOpen() { return isOpen; }
	
	/** @return 被管理的线程列表 */
	public static HashMap<String, Entry<Thread, ThreadType>> getTs() { return ts; }
	
}
