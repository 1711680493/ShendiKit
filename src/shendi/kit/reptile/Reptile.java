package shendi.kit.reptile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import shendi.kit.exception.HttpResponseException;
import shendi.kit.exception.NullHttpResponseException;
import shendi.kit.log.Log;
import shendi.kit.util.HttpUtil;

/**
 * 爬虫工具.<br>
 * 通过 {@link #index(String)} 可以直接获取一个网页的数据<br>
 * 之后通过 {@link #getElements(String, String)} 来获取对应的所有元素<br>
 * 通常需要配合 {@link HttpUtil} 来进行资源的下载.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @see HttpUtil
 */
public class Reptile {

	/**
	 * 获取某个页面的数据体,一般获取主界面使用此方法.<br>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param url 页面链接.
	 * @return 数据体
	 */
	public static String index(String url) { return index(url, null); }
	
	/**
	 * 获取某个页面的数据体,一般获取主界面使用此方法.<br>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param url 页面链接
	 * @param type 请求类型
	 * @return 数据体
	 */
	public static String index(String url, String type) {
		if (url == null) return null;
		HttpUtil http = new HttpUtil(url, 80, type);
		try {
			http.send();
		} catch (NullHttpResponseException | HttpResponseException | IOException e) {
			Log.printErr("获取网站数据出错: " + e.getMessage());
			e.printStackTrace();
		}
		return http.getRespBody();
	}
	
	/**
	 * 从数据体中获取对应的元素.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 元素名
	 * @param data 数据体
	 * @return 对应的元素集合,如果数据体是无效的,则返回null
	 */
	public static List<Element> getElements(String name, String data) {
		if (name == null || "".equals(name) || data == null || "".equals(data)) return null;
		List<Element> list = new ArrayList<>();
		
		int index = -1;
		while ((index = data.indexOf(name, index + 1)) > 0) {
			if (data.charAt(index - 1) == '<') {
				data = data.substring(index - 1);
				// 找到此元素对应的结尾,结尾的 > 下标必须比 name 大
				index = data.indexOf('>');
				int size = name.length();
				while ((index = data.indexOf('>', index + 1)) > size) {
					if (name.equals(data.substring(index - size, index))) {
						list.add(new Element(data.substring(0, index+1), name));
						break;
					}
				}
			}
		}
		
		return list;
	}

}
