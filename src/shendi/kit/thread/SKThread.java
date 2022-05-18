package shendi.kit.thread;

/**
 * 线程封装类,使用线程创建此类对象来表示一个可被管理的线程.
 * 创建时间 2022年1月19日 下午5:44:10
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class SKThread {
	
	/** 线程 */
	private final Thread THREAD;
	
	/** 线程创建时间 */
	private final long CREATE_TIME;
	
	public SKThread(String name, Thread t) {
		this.THREAD = t;
		this.CREATE_TIME = System.currentTimeMillis();
		
		ThreadManager.THREADS.put(name, this);
	}
	
	/** @return 包装的线程 */
	public Thread getThread() { return THREAD; }
	
	/** @return 线程创建时间 */
	public long getCreateTime() { return CREATE_TIME; }
	
}
