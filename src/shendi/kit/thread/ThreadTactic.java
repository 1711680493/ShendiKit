package shendi.kit.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程策略,继承此类以实现一个对线程管理的线程策略类.
 * 创建时间 2022年1月19日 下午5:45:27
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public abstract class ThreadTactic {
	
	/** 使用此策略的所有线程 */
	private final List<SKThread> THREADS = new ArrayList<>();
	
	/**
	 * 创建一个线程策略,并加入线程管理器.
	 * @param name 线程策略名称,需唯一
	 */
	public ThreadTactic(String name) {
		ThreadManager.TACTICS.put(name, this);
	}
	
	
	
}
