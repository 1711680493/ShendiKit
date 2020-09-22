package shendi.kit.reptile;

import java.util.HashMap;
import java.util.Set;

/**
 * 代表一个基本的元素标签.<br>
 * 
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class Element {
	/** 元素的字符串表示形式 */
	protected String element;
	/** 元素名 */
	protected String name;
	/** 元素的所有属性的键值对表示 */
	protected HashMap<String, String> kvs = new HashMap<>();
	/** 元素的内容 */
	protected String html;
	
	/**
	 * 创建一个元素.
	 * @param element 元素字符串表示形式
	 * @param name 元素名
	 */
	public Element(String element, String name) {
		this.element = element;
		this.name = name;
		// 先解析元素的属性
		int firstLast = element.indexOf('>');
		String[] attributes = element.substring(element.indexOf(' ') + 1, firstLast).split(" ");
		for (int i = 0; i < attributes.length; i++) {
			if (!"".equals(attributes[i])) {
				String k = attributes[i].trim();
				for (int j = i + 1; j < attributes.length; j++) {
					String v = attributes[j].trim();
					if (!"".equals(v) && !"=".equals(v)) {
						int len = attributes[j].length();
						v = len <= 2 ? "" : attributes[j].substring(1, len - 1);
						kvs.put(k, v);
						i = j;
						break;
					}
				}
			}
		}
		
		// 获取元素内容
		html = element.substring(firstLast + 1);
		html = html.substring(0, html.indexOf('<'));
	}
	
	/**
	 * 获取此元素的指定属性.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param attr 属性名
	 * @return 属性名对应的值
	 */
	public String attr(String attr) {
		if (attr == null || "".equals(attr)) return null;
		return kvs.get(attr);
	}

	/** @return 此元素的所有属性名. */
	public Set<String> attrNames() { return kvs.keySet(); }
	
	/** @return 此元素的所有属性. */
	public HashMap<String, String> attrs() { return kvs; }
	
	/** @return 元素的内容. */
	public String html() { return html; }
	
	@Override public String toString() { return element; }
	
}
