package shendi.kit.net;

import java.io.IOException;

import shendi.kit.exception.NoPortError;

/**
 * 因特尔协议簇服务端
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public interface IntelServer extends Server {

	/**
	 * 调用此方法会抛出错误,请使用 {@link #start(int)}
	 */
	default void start() {
		throw new NoPortError("IntelServer不支持 start(),请使用 start(port)!");
	}
	
	/**
	 * 开启一个服务端根据指定端口
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param port 端口
	 * @throws IOException 当启动出现问题时抛出
	 */
	void start(int port) throws IOException;
	
}
