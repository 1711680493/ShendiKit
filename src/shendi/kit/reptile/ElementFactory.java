package shendi.kit.reptile;

import java.util.HashMap;

import shendi.kit.log.Log;

/**
 * 元素工厂.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class ElementFactory {
	
	/** 元素类型集合 */
	private static final HashMap<String, Class<Element>> es = new HashMap<>();
	
	/**
	 * 创建元素.<br>
	 * 一个正确的元素应该是以左尖括号开头,右尖括号结尾.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param element 元素的字符串数据
	 * @return 如果此元素不是正确的则返回null
	 */
	public static Element createElement(String element) {
		if (element == null || element.length() < 7 || element.indexOf('>') == -1 || element.charAt(0) != '<' || element.charAt(element.length() - 1) != '>') return null;
		String name = element.substring(1, element.indexOf(' '));
		if (name == null) return null;
		if (es.containsKey(element)) {
			try {
				return es.get(name).getConstructor(String.class).newInstance(element);
			} catch (Exception e) {
				Log.printErr("创建对应元素类型时出错,改为创建默认元素类型: " + element + ']' + e.getMessage());
				e.printStackTrace();
			}
		}
		return new Element(element, name);
	}
	
}
