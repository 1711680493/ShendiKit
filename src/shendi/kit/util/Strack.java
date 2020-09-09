package shendi.kit.util;

/**
 * 栈,元素先进后出.<br>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class Strack<E> {
	
	int size = 0;
	
	private Node<E> top;
	
	public E pop() {
		if (top == null) return null;
		Node<E> node = top;
		top = top.next;
		size--;
		return node.data;
	}
	
	public void push(E e) {
		if (top == null) top = new Node<E>(e,null);
		else {
			Node<E> node = new Node<E>(e, top);
			top = node;
		}
		size++;
	}
	
}
