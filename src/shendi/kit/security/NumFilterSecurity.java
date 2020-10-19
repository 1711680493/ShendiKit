package shendi.kit.security;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import shendi.kit.util.Entry;
import shendi.kit.util.Queue;

/**
 * 数量安全过滤器.<br>
 * 用于限制某信息在指定时间间隔内的出现次数<br>
 * 通常用于 DDOS 防护等.<br>
 * <br>
 * 有以下几个变量,用于调整过滤效果<br>
 * {@link #timeout}用于判断的时间间隔,单位为毫秒,默认3000<br>
 * <br>
 * {@link #maxNum} 对应时间间隔内最大出现次数,单位为毫秒,默认100<br>
 * 当在指定时间间隔内超过maxNum次,则执行 filter 方法会返回 true<br>
 * 此时程序应跳过此次操作.<br>
 * <br>
 * {@link #filterTime} 当信息被过滤后,此信息在filterTime时间内调用的filter方法一直返回true<br>
 * 默认为一分钟 60000<br>
 * <br>
 * 可通过对应的 set 方法来设置,或在创建此类对象时使用构造方法 {@link #NumFilterSecurity(int, int, int)}
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class NumFilterSecurity implements FilterSecurity {
	
	/** 用于判断的时间间隔,单位毫秒 */
	private int timeout = 3000;
	
	/** 对应时间间隔内最大的出现次数,单位毫秒 */
	private int maxNum = 100;
	
	/** 过滤的冷却时间,被过滤后,在filterTime内在判断指定信息则一直为true,单位毫秒 */
	private int filterTime = 60000;
	
	/** 信息与信息出现次数和最后一次时间的键值对集合 */
	private HashMap<String, Entry<Integer, Long>> infos = new HashMap<>();
	
	/** 冷却队列 */
	private Queue<Entry<Long, String>> timeQueue = new Queue<>();
	
	public NumFilterSecurity() {}
	
	/**
	 * @param timeout 		时间间隔
	 * @param maxNum 		指定时间间隔的最大访问次数
	 * @param filterTime 	被过滤的冷却时间
	 */
	public NumFilterSecurity(int timeout, int maxNum, int filterTime) {
		this.timeout = timeout;
		this.maxNum = maxNum;
		this.filterTime = filterTime;
	}

	/**
	 * 当info在指定时间间隔内访问超过最大次数,则返回true,并加入冷却队列<br>
	 * 在冷却时间内调用此方法再次判断对应的info则也返回true
	 * @param info 要进行过滤判断的信息
	 * @return 是否要被过滤,如果true,则不应执行此次操作
	 */
	public boolean filter(String info) {
		// 首先判断冷却队列中是否有,是则直接返回true
		// 如果没有则判断键值对中是否有,如果有则判断是否超过限制要被过滤
		// 如果还没有则加入键值对中
		if (timeContainInfo(info)) {
			return true;
		} else if (infos.containsKey(info)) {
			Entry<Integer, Long> entry = infos.get(info);
			// 超时则归零
			// 否则判断是否超过指定次数
			//	超过则进入冷却队列,并从键值对移除
			//	没超过则次数+1
			if (System.currentTimeMillis() - entry.value > timeout) {
				entry.key = 0;
				entry.value = System.currentTimeMillis();
			} else if (entry.key > maxNum) {
				timeQueue.offer(new Entry<Long, String>(System.currentTimeMillis(), info));
				infos.remove(info);
				return true;
			} else entry.key++;
		} else {
			// 当键值对新增时,进行遍历操作,将已经过时的移除键值对
			Set<java.util.Map.Entry<String, Entry<Integer, Long>>> set = infos.entrySet();
			Iterator<java.util.Map.Entry<String, Entry<Integer, Long>>> it = set.iterator();
			while (it.hasNext()) {
				java.util.Map.Entry<String, Entry<Integer, Long>> next = it.next();
				if (System.currentTimeMillis() - next.getValue().value > timeout) {
					it.remove();
				}
			}
			infos.put(info, new Entry<Integer, Long>(0, System.currentTimeMillis()));
		}
		return false;
	}

	/**
	 * 判断冷却队列是否包含指定信息<br>
	 * <br>
	 * 通过循环判断对应信息是否超时超时则出队.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param info 信息
	 * @return 冷却队列是否包含信息
	 */
	private boolean timeContainInfo(String info) {
		int size = timeQueue.size();
		for (int i = 0; i < size; i++) {
			Entry<Long, String> poll = timeQueue.poll();
			
			if (System.currentTimeMillis() - poll.key > filterTime) {
				// 冷却已经结束,这时返回false
				if (poll.value.equals(info)) return false;
			} else {
				timeQueue.offer(poll);
				if (poll.value.equals(info)) return true;
			}
		}
		return false;
	}
	
	public int getTimeout() { return timeout; }
	public void setTimeout(int timeout) { this.timeout = timeout; }

	public int getMaxNum() { return maxNum; }
	public void setMaxNum(int maxNum) { this.maxNum = maxNum; }

	public int getFilterTime() { return filterTime; }
	public void setFilterTime(int filterTime) { this.filterTime = filterTime; }

	/** @return 信息与出现次数和最后一次出现时间的键值对. */
	public HashMap<String, Entry<Integer, Long>> getInfos() { return infos; }
	
	/** @return 冷却队列 */
	public Queue<Entry<Long, String>> getTimeQueue() { return timeQueue; }
	
}
