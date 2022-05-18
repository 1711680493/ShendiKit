package shendi.kit.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import shendi.kit.log.Log;

/**
 * 对流处理的工具类.<br>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.1
 */
public class StreamUtils {
	private StreamUtils() {}
	
	/**
	 * 从指定的输入流中读取一行数据.
	 * @param input 要操作的输入流
	 * @return 如果没有数据,则返回null
	 * @throws IOException 在读取的过程中出现错误
	 */
	public static String readLine(InputStream input) throws IOException {
		byte[] result = readLineRByte(input);
		return result == null ? null : new String(result);
	}
	
	/**
	 * 从指定的输入流中读取一行数据.
	 * @param input 要操作的输入流
	 * @return 如果没有数据,则返回null
	 * @throws IOException 在读取的过程中出现错误
	 */
	public static byte[] readLineRByte(InputStream input) throws IOException {
		int value = -1;
		//有效数据长度
		int len = 0;
		byte[] data = new byte[1024];
		while ((value = input.read()) != -1) {
			//如果数组长度不够则增长
			if (len >= data.length) {
				byte[] temp = data;
				data = new byte[len+1024];
				System.arraycopy(temp, 0, data, 0, temp.length);
			}
			data[len] = (byte) value;
			len++;
			// 如果读到\n则读取完这一行
			if ((char)value == '\n') break;
		}
		// 数组长度为0,返回null
		if (len == 0) return null;
		byte[] d = new byte[len];
		System.arraycopy(data, 0, d, 0, len);
		return d;
	}

	/**
	 * 从指定的输入流中读取以指定字节结尾的数据(读取到的数据包含结尾).<br>
	 * 如果可能读取不到,则请给输入流设置超时时间.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param input 要操作的输入流
	 * @param end 数据的结尾,例如以 \r\n结尾则数组为 {'\r','\n'}
	 * @return 读取的数据,如果没有则返回null
	 * @throws IOException 读取过程中出现错误
	 */
	public static byte[] readByEnd(InputStream input, byte[] end) throws IOException {
		return readByEnd(input, end, -1);
	}
	
	/**
	 * 从指定的输入流中读取以指定字节结尾的数据(读取到的数据包含结尾).<br>
	 * 如果可能读取不到,则请给输入流设置超时时间.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param input 要操作的输入流
	 * @param end 数据的结尾,例如以 \r\n结尾则数组为 {'\r','\n'}
	 * @param max 读取数据的最大长度,-1则不限制.
	 * @return 读取的数据,如果没有则返回null
	 * @throws IOException 读取过程中出现错误
	 * @since 1.1
	 */
	public static byte[] readByEnd(InputStream input, byte[] end, int max) throws IOException {
		int value = -1, len = 0;
		byte[] data = new byte[4096];
		
		while ((value = input.read()) != -1) {
			//如果数组长度不够则增长,翻倍增长
			if (len >= data.length) {
				byte[] temp = data;
				data = new byte[len<<1];
				System.arraycopy(temp, 0, data, 0, temp.length);
			}
			data[len] = (byte) value;
			len++;
			
			if (ByteUtil.endsWith(data, len, end)) break;
			
			// 长度大于最大长度则直接返回数据
			if (max != -1 && len >= max) {
				Log.print("本地读取数据已超过设置的最大数(%s),跳出读取", max);
				break;
			}
		}
		if (len == 0) return null;
		byte[] d = new byte[len];
		System.arraycopy(data, 0, d, 0, len);
		return d;
	}
	
	/**
	 * 将一个文件读取为字节流,读取的文件必须是确定存在的.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param file 文件路径
	 * @return 文件的字节流.
	 */
	public static byte[] getFile(String file) { return file == null ? null : getFile(new File(file)); }
	
	/**
	 * 将一个文件读取为字节流,读取的文件必须是确定存在的.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param file 文件
	 * @return 文件的字节流.
	 */
	public static byte[] getFile(File file) {
		byte[] data = new byte[0];
		try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
			int len = -1;
			byte[] bytes = new byte[1024];
			while ((len = input.read(bytes)) != -1) {
				byte[] temp = data;
				data = new byte[temp.length + len];
				System.arraycopy(temp,0,data,0,temp.length);
				System.arraycopy(bytes,0,data,temp.length,len);
			}
		} catch (IOException e) { Log.printErr(file.getPath() + " 文件读取错误: " + e.getMessage()); }
		return data;
	}
	
	/**
	 * 读取指定输入流中的所有数据.
	 * @param input 输入流
	 * @return 输入流中的所有数据
	 * @throws IOException 读取过程中出错
	 * @since 1.1
	 */
	public static byte[] readAllByte(InputStream input) throws IOException {
		return readAllByte(input, -1);
	}
	
	/**
	 * 读取指定输入流中的所有数据.
	 * @param input 输入流
	 * @param max 数据最大长度,当读取的数据缓存达到此长度将直接返回数据,-1为不限制.
	 * @return 输入流中的所有数据
	 * @throws IOException 读取过程中出错
	 * @since 1.1
	 */
	public static byte[] readAllByte(InputStream input, int max) throws IOException {
		byte[] data = new byte[input.available()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) input.read();
			if (max != -1 && data.length >= max) {
				Log.print("本地读取数据已超过设置的最大数(%s),跳出读取", max);
				break;
			}
		}
		
		return data;
	}
	
}
