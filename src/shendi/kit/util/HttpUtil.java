package shendi.kit.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import shendi.kit.exception.HttpResponseException;
import shendi.kit.exception.NullHttpResponseException;
import shendi.kit.log.Log;

/**
 * Http 协议工具类.<br>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class HttpUtil {
	/** 头的结尾 */
	public static final byte[] END_HEAD = "\r\n\r\n".getBytes();
	/** 体的结尾 */
	public static final byte[] END_BODY = "\r\n0\r\n\r\n".getBytes();
	
	/** 地址/主机名 */
	private String host;
	/** 端口 */
	private int port = 80;
	/** 套接字 */
	private Socket socket;
	/** http版本信息 */
	private String httpV = "HTTP/1.1";
	/** 请求类型 */
	private String reqType = "GET";
	/** 请求路径 */
	private String reqPath = "/";
	/** 超时时间 */
	private int timeout = 3000;
	/** 请求头 */
	private Map<String,String> reqHeads = new HashMap<>();
	/** 请求的数据 */
	private byte[] data;
	
	/** 响应头的字符串形式 */
	private String respHeadStr;
	/** 响应头 */
	private Map<String, String> respHeads = new HashMap<>();
	/** 响应体 */
	private String respBody;
	/** 响应的所有数据 */
	private byte[] respData;
	/** 响应状态 */
	private int state;
	/** 响应状态信息 */
	private String stateInfo;
	
	/**
	 * 根据指定的地址创建
	 * @param host 主机名
	 */
	public HttpUtil(String host) { this(host, -1); }
	
	/**
	 * 根据指定的地址与端口创建
	 * @param host 主机名
	 * @param port 端口
	 */
	public HttpUtil(String host, int port) { this(host, port, ""); }
	
	/**
	 * 根据指定的主机名,端口,协议创建
	 * @param host 主机名
	 * @param port 端口
	 * @param reqType 请求类型
	 */
	public HttpUtil(String host, int port, String reqType) {
		this(host, port, (byte[])null);
		
		if (reqType != null && "" != reqType) this.reqType = reqType;
	}
	
	/**
	 * 根据指定的主机名,端口,数据创建<br>
	 * 通常用于将一个完整的HTTP数据发给另一个服务器.
	 * @param host 主机名
	 * @param port 端口
	 * @param data 请求的所有数据
	 */
	public HttpUtil(String host, int port, byte[] data) {
		// 去掉协议头
		if (host != null && (host.indexOf("http://") != -1 || host.indexOf("https://") != -1)) {
			int len = host.indexOf("/");
			host = host.substring(len+2, host.length());
		}
		
		// 获取请求路径
		int index = host.indexOf('/');
		if (index != -1) {
			String str = host.substring(index, host.length());
			if (str.length() > 1) {
				reqPath = str;
				// 非文件的子目录尾缀需要加 /
				if (reqPath.indexOf('.') == -1) {
					reqPath += '/';
				}
			}
			host = host.substring(0,index);
		}
		this.host = host;
		this.data = data;
		reqHeads.put("Host", host);
		if (port != -1) this.port = port;
	}

	/**
	 * 完成请求.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @throws NullHttpResponseException 当处理响应时,数据为空则抛出
	 * @throws HttpResponseException 在解析响应数据过程中出现问题则抛出
	 */
	public void send() throws IOException,NullHttpResponseException,HttpResponseException {
		try (Socket socket = new Socket(host, port);
				OutputStream output = socket.getOutputStream();
				InputStream input = socket.getInputStream()) {
			this.socket = socket;
			socket.setSoTimeout(timeout);
			
			// 发送请求
			if (data == null) {
				StringBuilder build = new StringBuilder(30);
				build.append(reqType); build.append(' ');
				build.append(reqPath); build.append(' ');
				build.append(httpV); build.append("\r\n");
				reqHeads.forEach((k,v) -> {
					build.append(k); build.append(": "); build.append(v);
					build.append("\r\n");
				});
				build.append("\r\n");
				data = build.toString().getBytes();
			}
			output.write(data); output.flush();
			
			// 处理响应
			// 先获取第一行,状态是204,205,304则没有响应体,以\r\n\r\n结尾
			byte[] bState = StreamUtils.readLineRByte(input);
			if (bState == null) {
				Log.printErr("处理Http响应失败,获取状态信息(第一行)为空");
				throw new NullHttpResponseException();
			}
			stateInfo = new String(bState, 0, bState.length - 2);
			try { state = Integer.parseInt(stateInfo.split(" ")[1]); }
			catch (Exception e) {
				Log.printErr("获取响应状态失败,格式化出错: " + stateInfo);
				throw new HttpResponseException(e);
			}
			
			// 获取响应头并解析
			byte[] bHeads = StreamUtils.readByEnd(input, END_HEAD);
			if (bHeads == null || bHeads.length < 4) {
				Log.printErr("获取响应头为空!");
				throw new HttpResponseException("获取响应头为空!");
			}
			
			respHeadStr = new String(bHeads, 0, bHeads.length - 4);
			String[] heads = respHeadStr.split("\r\n");
			for (String head : heads) {
				int index = head.indexOf(':');
				if (index == -1) {
					Log.printAlarm("遍历响应头,某个响应头不是键值对形式: " + head);
				} else {
					respHeads.put(head.substring(0, index).trim(), head.substring(index + 1).trim());
				}
			}
			
			// 没有响应体则不获取
			// 有响应体则判断响应头是否有 Content-Length,是则读取指定大小,否则判断结尾
			byte[] body = null;
			if (!(state == 204 || state == 205 || state == 304)) {
				// 获取响应体
				if (respHeads.containsKey("Content-Length")) {
					try {
						body = new byte[Integer.parseInt(respHeads.get("Content-Length"))];
					} catch (NumberFormatException e) {
						Log.printErr("请求头的Content-Length的值不为数字！" + respHeads.get("Content-Length"));
					}
					for (int i = 0, value = -1; i < body.length; i++) {
						value = input.read();
						body[i] = (byte) value;
					}
				} else {
					body = StreamUtils.readByEnd(input, END_BODY);
					if (body == null) {
						Log.printAlarm("响应数据应有响应体,但是没有.");
					}
				}
			}
			
			// 合并数据,完成响应
			int size = bState.length + bHeads.length;
			if (body != null) {
				size += body.length;
				// 如果没有Content-Length,则去掉空行尾巴
				if (!respHeads.containsKey("Content-Length")) {
					respBody = new String(body, 0, body.length - END_BODY.length);
				}
			}
			respData = new byte[size];
			System.arraycopy(bState, 0, respData, 0, bState.length);
			System.arraycopy(bHeads, 0, respData, bState.length, bHeads.length);
			
			if (body != null) {
				System.arraycopy(body, 0, respData, bHeads.length + bState.length, body.length);
			}
		} catch (IOException e) {
			throw e;
		}
	}

	/** @return 当前请求的地址 */
	public String getHost() { return host; }

	/** @return 当前请求的端口 */
	public int getPort() { return port; }
	/**
	 * 设置请求的端口,为0-65535之间
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param port 端口
	 */
	public void setPort(int port) { this.port = port; }

	/**
	 * 获取套接字,只有在使用了 {@link #send()} 后才能获取到.<br>
	 * 用于获取 ip 等信息
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 已经关闭的Socket
	 */
	public Socket getSocket() { return socket; }

	/** @return 当前协议版本 */
	public String getHttpV() { return httpV; }

	/**
	 * 设置协议版本,默认为 HTTP/1.1
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param httpV 协议版本
	 */
	public void setHttpV(String httpV) { this.httpV = httpV; }

	/** @return 获取当前请求类型 */
	public String getReqType() { return reqType;}
	/**
	 * 设置请求类型,默认为GET
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param reqType 请求类型
	 */
	public void setReqType(String reqType) { this.reqType = reqType; }

	/** @return 请求的资源路径 */
	public String getReqPath() { return reqPath; }

	/** @return 超时时间 */
	public int getTimeout() { return timeout; }
	/**
	 * 设置此次操作的超时时间
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param timeout 时间,单位毫秒
	 */
	public void setTimeout(int timeout) { this.timeout = timeout; }

	/** @return 响应头的字符串形式 */
	public String getRespHeadStr() { return respHeadStr; }
	
	/**
	 * 获取请求头集合.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 请求头集合
	 */
	public Map<String, String> getReqHeads() { return reqHeads; }

	/**
	 * 设置请求头.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param key 请求头名
	 * @param value 请求头值.
	 */
	public void setReqHead(String key, String value) { reqHeads.put(key, value); }
	/**
	 * 设置请求头集合
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param reqHeads 请求头集合
	 */
	public void setReqHeads(Map<String, String> reqHeads) { this.reqHeads = reqHeads; }

	/** @return 请求的数据,调试用 */
	public byte[] getData() { return data; }
	/**
	 * 设置请求的数据,当设置后,请求的所有数据都为此数据(包括协议头,请求头请求体)
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data 请求的数据
	 */
	public void setData(byte[] data) { this.data = data; }
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param head 响应头名
	 * @return 指定的响应头
	 */
	public String getRespHead(String head) { return respHeads.get(head); }

	/** @return 响应头集合 */
	public Map<String, String> getRespHeads() { return respHeads; }

	/** @return 响应体 */
	public String getRespBody() { return respBody; }

	/** @return 响应的所有数据(包含协议头,响应头响应体 */
	public byte[] getRespData() { return respData; }

	/** @return 响应状态 */
	public int getState() { return state; }

	/** @return 响应状态信息 */
	public String getStateInfo() { return stateInfo; }
	
}
