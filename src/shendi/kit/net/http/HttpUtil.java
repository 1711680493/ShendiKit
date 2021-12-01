package shendi.kit.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import shendi.kit.exception.HttpResponseException;
import shendi.kit.exception.NullHttpResponseException;
import shendi.kit.log.Log;
import shendi.kit.util.ByteUtil;
import shendi.kit.util.StreamUtils;

/**
 * Http 协议工具类.<br>
 * HTML不区分大小写<br>
 * 所以,响应头的名称统一大写存储.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.1
 */
public class HttpUtil {
	/** 头的结尾 */
	public static final byte[] END_HEAD = "\r\n\r\n".getBytes();
	/** 体的结尾 */
	public static final byte[] END_BODY = "\r\n0\r\n\r\n".getBytes();
	/** 数据分隔符(换行符) **/
	public static final byte[] DATA_SPLIT = "\r\n".getBytes();
	
	public static final String REQ_TYPE_GET = "GET";
	public static final String REQ_TYPE_POST = "POST";
	public static final String REQ_TYPE_HEAD = "HEAD";
	
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
	/** 读取等待的超时时间 */
	private int timeout = 5000;
	/** 请求头 */
	private Map<String,String> reqHeads = new HashMap<>();
	/** 请求参数 */
	private StringBuilder parameters;
	/** 请求的数据 */
	private byte[] data;
	
	/** 响应头的字符串形式 */
	private String respHeadStr;
	/** 响应头 */
	private Map<String, String> respHeads = new HashMap<>();
	/** 响应体 */
	private String respBody;
	/** 响应体的字节形式 */
	private byte[] respBodyData;
	/** 响应的所有数据,如果有分块编码,那么也将带有分块编码的信息 */
	private byte[] respData;
	/** 响应状态 */
	private int state;
	/** 响应状态信息 */
	private String stateInfo;
	/** 是否需要自行处理响应体 */
	private HttpDataDispose dispose;
	/** 数据处理每次传递的最大字节,默认 32kb */
	private int dataDisposeLen = 32786;
	
	/** 当页面返回结果为重定向时,是否重定向 */
	private boolean isRedirect = false;
	/** 重定向所支持的最大次数,默认5,有时候可能页面会无限的重定向,这样就会造成程序卡死的局面,通过设置此变量来控制次数 */
	private int redirectMaxSize = 5;
	
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
	 * 根据指定的地址和请求类型创建
	 * @param host 主机名
	 * @param reqType 请求类型
	 */
	public HttpUtil(String host, String reqType) { this(host, -1, reqType); }
	
	/**
	 * 根据指定的主机名,端口,协议创建
	 * @param host 主机名
	 * @param port 端口
	 * @param reqType 请求类型
	 * @since 1.1
	 */
	public HttpUtil(String host, int port, String reqType) {
		this(host, port, (byte[])null);
		
		if (reqType != null && "" != reqType) this.reqType = reqType;
	}
	
	/**
	 * 根据指定的主机名,端口,数据创建<br>
	 * 通常用于将一个完整的HTTP数据发给另一个服务器.
	 * @param host 主机名,也可为url,如果port=-1,则从主机名中解析端口,如果无,则用默认
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
			String str = host.substring(index);
			if (str.length() > 1) {
				this.reqPath = str;
			}
			host = host.substring(0,index);
		}
		// 如果端口不存在则从host中获取,如果无,则使用默认
		if (port == -1) {
			index = host.indexOf(':');
			if (index != -1) {
				this.port = Integer.parseInt(host.substring(index + 1));
				this.host = host.substring(0, index);
			} else {
				this.host = host;
			}
		} else {
			this.port = port;
			this.host = host;
		}
		this.data = data;
		reqHeads.put("Host", host);
	}
	
	/**
	 * 使用当前对象进行重定向,会保留当前对象的请求头等信息<br>
	 * 当 isRedirect=true 时,页面重定向将自动使用此函数<br>
	 * 重定向最大次数为 {@link #redirectMaxSize}
	 * 如果需要一些格外的信息则需要自行添加
	 * @param url 重定向的地址
	 */
	public void redirect(String url) {
		if (redirectMaxSize-- <= 0) {
			Log.print("重定向次数已达限制: " + url);
			return;
		}
		
		// 初始化和清空
		data = null;
		
		// 去掉协议头
		if (url != null && (url.indexOf("http://") != -1 || url.indexOf("https://") != -1)) {
			int len = url.indexOf("/");
			url = url.substring(len+2, url.length());
		}
		// 获取请求路径
		int index = url.indexOf('/');
		if (index != -1) {
			String str = url.substring(index);
			if (str.length() > 1) {
				this.reqPath = str;
			}
			url = url.substring(0,index);
		}
		// 如果端口不存在则从host中获取,如果无,则初始化为默认
		index = url.indexOf(':');
		if (index != -1) {
			this.port = Integer.parseInt(host.substring(index + 1));
			this.host = url.substring(0, index);
		} else {
			this.host = url;
			this.port = 80;
		}
		reqHeads.put("Host", host);
		
		// 执行重定向操作
		try {
			send();
		} catch (IOException e) {
			Log.printErr("在执行重定向操作时出现IO错误: " + e.getMessage());
		} catch (NullHttpResponseException e) {
			Log.printErr("在执行重定向操作时响应为空!");
		} catch (HttpResponseException e) {
			Log.printErr("在执行重定向操作时响应数据有误!");
		}
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
				StringBuilder build = new StringBuilder(100);
				build.append(reqType); build.append(' ');
				build.append(reqPath);
				
				// 请求类型将参数加到地址还是加到内容, true为加到地址,false为加到内容
				boolean paramPos = !"post".equalsIgnoreCase(reqType);
				boolean paramNotNull = parameters != null;
				
				if (paramNotNull && paramPos) {
					build.append('?');
					build.append(parameters);
				}
				build.append(' ');
				build.append(httpV); build.append("\r\n");
				
				// POST请求会默认添加Content-Type和Content-Length请求,如果已有,则不添加
				boolean addContentType = true;
				boolean addContentLength = true;
				
				Set<Entry<String, String>> entrys = reqHeads.entrySet();
				for (Entry<String, String> entry : entrys) {
					String k = entry.getKey();
					String v = entry.getValue();
					
					build.append(k); build.append(": "); build.append(v);
					if ("Content-Type".equalsIgnoreCase(k)) addContentType = false;
					else if ("Content-Length".equalsIgnoreCase(k)) addContentLength = false;
					build.append("\r\n");
				}
				
				build.append("\r\n");
				
				if (paramNotNull && !paramPos) {
					int len = build.length() - 4;
					if (addContentType) {
						build.insert(len, "\r\nContent-Type:application/x-www-form-urlencoded");
					}
					if (addContentLength) {
						build.insert(len, "\r\nContent-Length:" + parameters.length());
					}
					build.append(parameters);
				}
				
				data = build.toString().getBytes();
			}
			output.write(data); output.flush();
			
			// 处理响应
			// 先获取第一行,类型为 HEAD 或状态是204,205,304则没有响应体,以\r\n\r\n结尾
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
					// 键统一大写存储
					respHeads.put(head.substring(0, index).trim().toUpperCase(), head.substring(index + 1).trim());
				}
			}
			
			// 没有响应体则不获取
			// 有响应体则判断响应头是否有 Content-Length,是则读取指定大小,否则判断结尾
			byte[] body = null;
			// 如果数据体编码为分块编码,则此变量将有数据(不为0),且会将完整的响应信息存储(包括分块编码信息),用于最后respData的数据完整性
			byte[] bodyAllData = new byte[0];
			// 如果需要格外处理响应则不会将响应数据保存
			if (!("HEAD".equalsIgnoreCase(reqType) || state == 204 || state == 205 || state == 302 || state == 304)) {
				// 获取响应体
				if (respHeads.containsKey("CONTENT-LENGTH")) {
					try {
						int len = Integer.parseInt(respHeads.get("CONTENT-LENGTH"));
						if (dispose == null) {
							body = new byte[len];
							for (int i = 0, value = -1; i < body.length; i++) {
								value = input.read();
								body[i] = (byte) value;
							}
							respBody = new String(body);
						} else {
							if (len == 0) {
								Log.print("HttpUtil 处理响应: Content-Length=0,无响应数据");
							} else {
								byte[] tmp = new byte[dataDisposeLen];
								int l = -1;
								while ((l = input.read(tmp)) != -1) {
									if (dispose.dispose(ByteUtil.subByte(tmp, 0, l))) {
										break;
									}
									
									len -= l;
									if (len <= 0) break;
								}
							}
						}
					} catch (NumberFormatException e) {
						Log.printErr("请求头的Content-Length的值不为数字！" + respHeads.get("CONTENT-LENGTH"));
					}
				} else if (respHeads.containsKey("TRANSFER-ENCODING") && respHeads.get("TRANSFER-ENCODING").equalsIgnoreCase("chunked")) {
					// 没有Content-Length,且http版本为1.1响应头一般都有Transfer-Encoding: chunked
					// 如果有,则采用分块编码方式读取,格式为: 数据大小\r\n数据内容\r\n数据大小\r\n数据内容...0\r\n\r\n
					if (dispose == null) {
						int len = 0;
						body = new byte[0];
						do {
							// 读取到长度
							byte[] tmpLen = StreamUtils.readByEnd(input, DATA_SPLIT);
							len = Integer.parseInt(new String(tmpLen, 0, tmpLen.length - DATA_SPLIT.length), 16);
							
							if (len != 0) {
								// 读取指定长度的数据并复制进body
								byte[] tmp = new byte[len];
								input.read(tmp, 0, len);
								body = ByteUtil.concat(body, tmp);
								
								// 加入到局部变量完整响应中
								bodyAllData = ByteUtil.concat(bodyAllData, tmpLen, tmp, DATA_SPLIT);
							} else {
								bodyAllData = ByteUtil.concat(bodyAllData, tmpLen, DATA_SPLIT, DATA_SPLIT);
							}
							
							// 读取数据结尾(\r\n)
							StreamUtils.readByEnd(input, DATA_SPLIT);
						} while (len != 0);
						
						if (body != null) respBody = new String(body);
						else Log.printAlarm("响应数据应有响应体,但是没有.");
					} else {
						int len = 0;
						do {
							// 读取到长度
							byte[] tmp = StreamUtils.readByEnd(input, DATA_SPLIT);
							len = Integer.parseInt(new String(tmp, 0, tmp.length - DATA_SPLIT.length), 16);
							
							if (len != 0) {
								// 读取指定长度的数据传递给函数
								tmp = new byte[len];
								input.read(tmp, 0, len);
								
								dispose.dispose(tmp);
							}
							
							// 读取数据结尾(\r\n)
							StreamUtils.readByEnd(input, DATA_SPLIT);
						} while (len != 0);
					}
				} else {
					if (dispose == null) {
						body = StreamUtils.readByEnd(input, END_BODY);
						if (body != null) {
							int size = body.length - END_BODY.length;
							body = ByteUtil.subByte(body, 0, size);
							if (size >= 0) respBody = new String(body);
						} else Log.printAlarm("响应数据应有响应体,但是没有.");
					} else {
						byte[] tmp = new byte[dataDisposeLen];
						int l = -1;
						while ((l = input.read(tmp)) != -1) {
							// 判断是否结尾
							if (ByteUtil.endsWith(tmp, l, END_BODY)) {
								dispose.dispose(ByteUtil.subByte(tmp, 0, l - END_BODY.length));
								break;
							} else {
								if (dispose.dispose(ByteUtil.subByte(tmp, 0, l))) {
									break;
								}
							}
						}
					}
				}
				respBodyData = body;
			} else if (isRedirect && (state == 301 || state == 302)) {
				String location = respHeads.get("LOCATION");
				if (location == null) {
					Log.printErr("页面重定向时失败,响应头location为空,此次数据为当前页面数据,请检查.");
				} else {
					StringBuilder b = new StringBuilder(100);
					b.append("页面由 ");
					b.append(host); b.append(port); b.append(reqPath);
					b.append(" 重定向至 ");
					b.append(location);
					Log.print(b);
					
					redirect(location);
					return;
				}
			}
			
			// 合并数据,完成响应
			int size = bState.length + bHeads.length;
			if (body != null) {
				size += bodyAllData.length != 0 ? bodyAllData.length : body.length;
			}
			respData = new byte[size];
			System.arraycopy(bState, 0, respData, 0, bState.length);
			System.arraycopy(bHeads, 0, respData, bState.length, bHeads.length);
			
			if (body != null) {
				if (bodyAllData.length != 0) {
					System.arraycopy(bodyAllData, 0, respData, bHeads.length + bState.length, bodyAllData.length);
				} else {
					System.arraycopy(body, 0, respData, bHeads.length + bState.length, body.length);
				}
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
	 * 设置读取等待操作的超时时间
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param timeout 时间,单位毫秒
	 */
	public void setTimeout(int timeout) { this.timeout = timeout; }

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
	public void setReqHead(String key, Object value) { reqHeads.put(key, value.toString()); }
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
	
	/** @return 响应头的字符串形式 */
	public String getRespHeadStr() { return respHeadStr; }
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 响应头名
	 * @return 指定的响应头
	 */
	public String getRespHead(String name) {
		if (name == null) return null;
		return respHeads.get(name.toUpperCase());
	}

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
	
	/**
	 * 添加请求参数,如果使用了此方法,则不应在链接后加参数.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param key 参数键
	 * @param value 参数值
	 * @since 1.1
	 */
	public void addParameter(String key, Object value) {
		if (parameters == null) parameters = new StringBuilder();
		if (parameters.length() != 0) parameters.append('&');
		parameters.append(key);
		parameters.append('=');
		parameters.append(value);
	}
	
	/**
	 * 直接设置请求参数,格式为 key=value&key=value,如果使用了此方法,则不应在链接后加参数.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param param 请求参数
	 * @since 1.1
	 */
	public void setParameters(String param) { parameters = new StringBuilder(param); }
	
	/**
	 * @return 请求参数
	 * @since 1.1
	 */
	public String getParameters() { return parameters.toString(); }
	
	/** @return 响应体的字节形式 */
	public byte[] getRespBodyData() { return respBodyData; }
	
	/**
	 * 设置当前响应的处理方法,当设置了后,此HttpUtil对象将不在存储格外的响应数据(respBody,respBodyData将为空),以节省内存开销.<br>
	 * 需要在 send 之前设置.
	 * @param dispose 处理响应体的方法
	 * @since 1.1
	 */
	public void setDispose(HttpDataDispose dispose) { this.dispose = dispose; }
	
	/**
	 * 设置响应处理每次传递的最大大小<br>
	 * 需要在 send 之前设置.
	 * @param dataDisposeLen 大小,单位byte,默认 1M
	 * @since 1.1
	 */
	public void setDataDisposeLen(int dataDisposeLen) { this.dataDisposeLen = dataDisposeLen; }

	/**
	 * 当页面返回结果为重定向时,是否自动获取重定向后的页面
	 * @param isRedirect true为获取,默认false
	 */
	public void setRedirect(boolean isRedirect) { this.isRedirect = isRedirect; }
	
	/**
	 * 设置重定向的最大次数,有时候可能遇到无限重定向的局面,通过设置此变量来避免此局面,前提是 isRedirecr=true
	 * @param redirectMaxSize 重定向的最大次数
	 */
	public void setRedirectMaxSize(int redirectMaxSize) { this.redirectMaxSize = redirectMaxSize; }
	
}
