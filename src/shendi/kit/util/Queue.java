package shendi.kit.util;

/**
 * 队列,元素先进先出<br>
 * 使用 offer 来添加一个元素到尾部<br>
 * 使用 poll 来获取并移除头部元素<br>
 * 使用 peek 来返回头部元素
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class Queue<E> {
//	add        增加一个元索                     如果队列已满，则抛出一个IIIegaISlabEepeplian异常
//	remove   移除并返回队列头部的元素    如果队列为空，则抛出一个NoSuchElementException异常
//	element  返回队列头部的元素             如果队列为空，则抛出一个NoSuchElementException异常
//	offer       添加一个元素并返回true       如果队列已满，则返回false
//	poll         移除并返问队列头部的元素    如果队列为空，则返回null
//	peek       返回队列头部的元素             如果队列为空，则返回null
//	put         添加一个元素                      如果队列满，则阻塞
//	take        移除并返回队列头部的元素     如果队列为空，则阻塞
	
	/** 前端结点 */
	private Node<E> front;
	/** 尾部结点 */
	private Node<E> rear;
	/** 结点数 */
	private int size;
	
	/**
	 * 添加元素到尾部.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param e 元素数据
	 */
	public void offer(E e) {
		if (front == null) {
			front = new Node<E>(e, null);
			rear = front;
		} else {
			rear.next = new Node<E>(e, null);
			rear = rear.next;
		}
		size++;
	}
	
	/**
	 * 取出并删除头部元素.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 头部元素的数据
	 */
	public E poll() {
		if (front == null) return null;
		E data = front.data;
		front = front.next;
		size--;
		return data;
	}
	
	/**
	 * 返回头部元素.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 头部元素的数据
	 */
	public E peek() { return front == null ? null : front.data; }
	
	/** @return 队列中元素的个数 */
	public int size() { return size; }
	
}
