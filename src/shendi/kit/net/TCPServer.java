package shendi.kit.net;

/**
 * TCP 服务端.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public abstract class TCPServer implements IntelServer {
	
//	/** 当前服务端的对象 */
//	private ServerSocket server;
//	
//	/** 是否有协议 */
//	private boolean isProtocol;
//	
//	/** 协议是否停止的标志位 */
//	private boolean isStop;
//	
//	//开启,停止,设置是否有协议,协议头,协议尾,接收,返回数据
//	@Override public void start(int port) throws IOException {
//		isStop = false;
//		
//		server = new ServerSocket(port);
//		new Thread(() -> {
//			try {
//				server.accept();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}).start();
//	}
//
//	@Override public void stop() {
//		try {
//			isStop = true;
//			
//			server.close();
//		} catch (IOException e) {
//			Log.printErr("在关闭TCP服务端时出错: " + e.getMessage());
//			e.printStackTrace();
//		}
//	}
//	
//	public abstract void accept(Socket s);	
	
}
