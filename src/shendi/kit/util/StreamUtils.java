package shendi.kit.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import shendi.kit.log.Log;

/**
 * 对流处理的工具类.
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
		return new String(readLineRByte(input));
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
		int value = -1, len = 0;
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
			
			if (endsWith(data, len, end)) break;
		}
		if (len == 0) return null;
		byte[] d = new byte[len];
		System.arraycopy(data, 0, d, 0, len);
		return d;
	}
	
	/**
	 * 判断指定数据是否以指定数据结尾.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data 被判断的数据
	 * @param len 被判断数据的长度
	 * @param end 结尾的数据
	 * @return true is yes,false is no.
	 */
	public static boolean endsWith(byte[] data, int len, byte[] end) {
		if (len >= end.length) {
			for (int i = 1; i <= end.length; i++) {
				if (end[end.length - i] != data[len - i]) return false;
			}
			return true;
		} else {
			return false;
		}
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
		byte[] data = new byte[input.available()];
		
		for (int i = 0; i < data.length; data[i] = (byte) input.read(),i++);
		
		return data;
	}
	
}
