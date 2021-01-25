package shendi.kit.net.http;

/**
 * HTTP 数据体处理接口,实现此接口,可给 HttpUtil 设置处理方法.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since 1.1
 */
public interface HttpDataDispose {
	
	/**
	 * 处理响应数据的函数,此函数可能会被调用多次,直至数据完结为止
	 * @param data 响应数据
	 * @return 处理是否完成,当返回true,则代表此响应处理完成,等同于此次HTTP完成.
	 */
	boolean dispose(byte[] data);
}
