package shendi.kit.util;

/**
 * 一个为键值对的结构.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class Entry<K,V> {
	
	/** 键 */
	public K key;
	/** 值 */
	public V value;
	
	/**
	 * 用键值对构建
	 * @param key 键
	 * @param value 值
	 */
	public Entry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return 一个为JSON形式的键值对
	 */
	@Override public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"key\":\"");
		builder.append(key);
		builder.append("\", \"value\":\"");
		builder.append(value);
		builder.append("\"}");
		return builder.toString();
	}
	
}
