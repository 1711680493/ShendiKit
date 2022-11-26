package shendi.kit.cache;

/**
 * 表示一个缓存.
 * <br>
 * 创建时间 2022年11月26日
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @since SK 1.1
 * @version 1.0
 */
public interface Cache {
	
	/**
	 * 初始化当前缓存实例.
	 * <br>
	 * 创建时间 2022年11月26日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	void init();
	
	/**
	 * 将数据写入缓存.
	 * <br>
	 * 创建时间 2022年11月26日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data 要写入的缓存数据
	 */
	void write(byte[] data);
	
	/**
	 * 重写缓存.
	 * <br>
	 * 创建时间 2022年11月26日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data 要写入的缓存数据
	 */
	void rewrite(byte[] data);
	
	/**
	 * 读取所有缓存数据
	 * <br>
	 * 创建时间 2022年11月26日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 缓存数据
	 */
	byte[] readAll();
	
	/**
	 * 清空缓存
	 * <br>
	 * 创建时间 2022年11月26日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	void clear();
	
}
