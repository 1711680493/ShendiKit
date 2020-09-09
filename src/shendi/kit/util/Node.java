package shendi.kit.util;

/**
 * 单链表结点,拥有数据与指向下一个结点的指针.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class Node<E> {
	/** 结点数据 */
	E data;
	
	/** 指向下一个结点的指针 */
	Node<E> next;
	
	/**
	 * 创建结点
	 * @param data {@link #data}
	 * @param next {@link #next}
	 */
	public Node (E data, Node<E> next) {
		this.data = data;
		this.next = next;
	}
	
}
