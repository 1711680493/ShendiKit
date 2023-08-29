package shendi.kit.util;

import shendi.kit.exception.NumberLessThanZero;

/**
 * 字节工具.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since 1.1
 */
public class ByteUtil {
	
	/**
	 * 将一段字节插入到另一段字节中.<br>
	 * <pre>
	 * byte[] source = {1, 2, 3};
	 * byte[] data = {4, 5};
	 * byte[] result = insert(data, 0, source, source.length, data.length);
	 * result == {1, 2, 3, 4, 5};
	 * 
	 * source = {1, 3, 4};
	 * data = {2};
	 * result = insert(data, 0, source, 1, data.length);
	 * result == {1, 2, 3, 4};
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data			字节数据
	 * @param dataOffset	字节数据的偏移(小于 data.length)
	 * @param source		要被操作的字节数组
	 * @param sourceOffset	插入原数组中的位置(小于等于 source.length)
	 * @param len			插入多少个(小于等于 data.length - dataOffset)
	 * @return 操作完的字节数组,当传入的参数为空则返回空
	 * 
	 * @throws NumberLessThanZero 当 dataOffset 或 sourceOffset 小于 0 时抛出.
	 */
	public static byte[] insert(byte[] data, int dataOffset, byte[] source, int sourceOffset, int len) {
		if (data == null || source == null) return null;
		if (dataOffset < 0 || sourceOffset < 0) throw new NumberLessThanZero();
		
		byte[] result = new byte[source.length + len];
		
		// 插入到指定位置
		System.arraycopy(data, dataOffset, result, sourceOffset, len);
		
		// 将源数据进行填充
		if (sourceOffset != 0) {
			System.arraycopy(source, 0, result, 0, sourceOffset);
		}
		if (sourceOffset != source.length) {
			System.arraycopy(source, sourceOffset, result, sourceOffset + len, source.length - sourceOffset);
		}
		return result;
	}
	
	/**
	 * 查找指定字符在字节数组中第一次出现的位置.<br>
	 * 等价于调用 indexOf(data, str.getBytes());
	 * <pre>
	 * byte[] data = "hello,world".getBytes();
	 * int result = indexOf(data, ",");
	 * result -> 5
	 * 
	 * result = indexOf(data, "llo");
	 * result -> 2
	 * 
	 * result = indexOf(data, "what are you doing?");
	 * result -> -1
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data	字节数组
	 * @param str	要查找的字符串
	 * @return 此字符串的第一个字符在数组中出现的位置,如果没有找到则返回-1
	 */
	public static int indexOf(byte[] data, String str) {
		return indexOf(data, str.getBytes());
	}
	
	/**
	 * 查找指定字节数组在字节数组中第一次出现的位置.<br>
	 * <pre>
	 * byte[] data = "hello,world".getBytes();
	 * int result = indexOf(data, ",".getBytes());
	 * result -> 5
	 * 
	 * result = indexOf(data, "llo".getBytes());
	 * result -> 2
	 * 
	 * result = indexOf(data, "what are you doing?".getBytes());
	 * result -> -1
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data	字节数组
	 * @param sData	要查找的字节数组
	 * @return 此字节数组的第一个字节在数组中出现的位置,如果没有找到则返回-1
	 */
	public static int indexOf(byte[] data, byte[] sData) {
		if (sData == null || sData.length == 0 || data == null || data.length == 0) return -1;
		
		if (data.length < sData.length) return -1;
		
		f:for (int i = 0; i < data.length; i++) {
			if (data[i] == sData[0]) {
				if (sData.length == 1) return i;
				if (data.length - i < sData.length) return -1;
				
				for (int j = 1; j < sData.length; j++) {
					if (data[i + j] != sData[j]) continue f;
				}
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 从指定位置截取数据.<br>
	 * <pre>
	 * byte[] data = "hello,world".getBytes();
	 * byte[] result = subByte(data, 2);
	 * result -> "llo,world"
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data			被操作的数据
	 * @param beginIndex	数据起始偏移
	 * @return 截取后的数据
	 * 
	 * @throws NumberLessThanZero 当 beginIndex 小于 0 时抛出.
	 */
	public static byte[] subByte(byte[] data, int beginIndex) {
		return subByte(data, beginIndex, data.length);
	}
	
	/**
	 * 从指定位置截取数据.<br>
	 * <pre>
	 * byte[] data = "hello,world".getBytes();
	 * byte[] result = subByte(data, 2, data.length);
	 * result -> "llo,world"
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data			要操作的数据
	 * @param beginIndex	数据起始偏移
	 * @param endIndex		数据结尾位置
	 * @return 截取后的数据
	 * 
	 * @throws NumberLessThanZero 		当 beginIndex 小于 0 时抛出.
	 * @throws IllegalArgumentException 当 endIndex 小于 beginIndex 时抛出.
	 */
	public static byte[] subByte(byte[] data, int beginIndex, int endIndex) {
		if (data == null) return null;
		if (beginIndex < 0) throw new NumberLessThanZero();
		if (endIndex < beginIndex) throw new IllegalArgumentException("endIndex < beginIndex");
		
		byte[] rData = new byte[endIndex - beginIndex];
		System.arraycopy(data, beginIndex, rData, 0, endIndex - beginIndex);
		
		return rData;
	}
	
	/**
	 * 将两个或多个字节数组拼接,使用可变数组.<br>
	 * 当字节数组为空或者长度为0时,将忽略此数组<br>
	 * <pre>
	 * byte[] data1 = "hello".getBytes();
	 * byte[] data2 = ",world".getBytes();
	 * byte[] result = concat(data1, data2);
	 * result -> "hello,world"
	 * 
	 * result = concat("hello".getBytes(), ",Java.".getBytes(), "I'm".getBytes(), " Shendi".getBytes());
	 * result -> "hello,Java.I'm Shendi"
	 * 
	 * data1 = "I like ".getBytes();  
	 * data2 = new byte[0];
	 * data3 = "this kit!".getBytes();
	 * result = concat(data1, data2, data3);
	 * result -> "I like this kit!"
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param datas 可变数组
	 * @return 拼接后数据
	 */
	public static byte[] concat(byte[]... datas) {
		if (datas == null || datas.length == 0) return null;
		if (datas.length == 1) return datas[0];
		
		int size = datas[0].length;
		for (int i = 1; i < datas.length; i++)
			size += datas[i].length;
		
		byte[] result = new byte[size];
		size = 0;
		for (int i = 0; i < datas.length; i++) {
			if (datas[i] == null || datas[i].length == 0) continue;
			System.arraycopy(datas[i], 0, result, size, datas[i].length);
			size += datas[i].length;
		}
		
		return result;
	}
	
	/**
	 * 将两个或多个字节数组拼接并在其中间插入分隔数组.<br>
	 * 当字节数组为空或者长度为0时,将忽略此数组<br>
	 * <pre>
	 * byte[] split = ",".getBytes();
	 * byte[] data1 = "hello".getBytes();
	 * byte[] data2 = "world".getBytes();
	 * byte[] result = concatSplit(split, data1, data2);
	 * result -> "hello,world"
	 * 
	 * result = concatSplit(split, "red".getBytes(), "green".getBytes(), "blue".getBytes(), "shendi".getBytes());
	 * result -> "red,green,blue,shendi"
	 * 
	 * result = concatSplit(split, "hello, world".getBytes());
	 * result -> "hello, world"
	 * 
	 * data1 = "I like".getBytes();  
	 * data2 = new byte[0];
	 * data3 = "this kit!".getBytes();
	 * result = concatSplit(split, data1, data2, data3);
	 * result -> "I like,this kit!"
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param split 分隔数组
	 * @param datas 要拼接的数组
	 * @return 拼接后的数组
	 */
	public static byte[] concatSplit(byte[] split, byte[]... datas) {
		return concatSplit(split, false, datas);
	}
	
	/**
	 * 将两个或多个字节数组拼接并在其中间插入分隔数组.<br>
	 * <pre>
	 * byte[] split = ",".getBytes();
	 * byte[] data1 = "hello".getBytes();
	 * byte[] data2 = "world".getBytes();
	 * byte[] result = concatSplit(split, data1, data2);
	 * result -> "hello,world"
	 * 
	 * result = concatSplit(split, "red".getBytes(), "green".getBytes(), "blue".getBytes(), "shendi".getBytes());
	 * result -> "red,green,blue,shendi"
	 * 
	 * result = concatSplit(split, "hello, world".getBytes());
	 * result -> "hello, world"
	 * 
	 * data1 = "I like".getBytes();  
	 * data2 = new byte[0];
	 * data3 = "this kit!".getBytes();
	 * result = concatSplit(split, data1, data2, data3);
	 * result -> "I like,this kit!"
	 * 
	 * result = concatSplit(split, true, data1, data2, data3);
	 * result -> "I like,,this kit!"
	 * 
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param split 分隔数组
	 * @param canNull 是否可以包含空数组,是则将空数组以0字节数组形式
	 * @param datas 要拼接的数组
	 * @return 拼接后的数组
	 */
	public static byte[] concatSplit(byte[] split, boolean canNull, byte[]... datas) {
		if (split == null || split.length == 0 || datas == null || datas.length == 0) return null;
		if (datas.length == 1) return datas[0];
		
		// 计算操作后的总长度,只多不少
		int size = datas[0].length;
		for (int i = 1; i < datas.length; i++) {
			if (datas[i] != null) size += datas[i].length;
		}
		
		// 加上切割字符的长度
		size += (datas.length - 1) * split.length;
		
		byte[] result = new byte[size];
		size = 0;
		// 先拷贝第一个有效数组,然后遍历 + split + data
		int i = 0;
		while (i < datas.length) {
			if (datas[i] == null || datas[i].length == 0) {
				++i;
				if (canNull) break;
				else continue;
			}
			
			System.arraycopy(datas[i], 0, result, size, datas[i].length);
			size += datas[i].length;
			++i;
			break;
		}
		
		for (; i < datas.length; i++) {
			if (datas[i] == null || datas[i].length == 0) {
				if (canNull) {
					System.arraycopy(split, 0, result, size, split.length);
					size += split.length;
				}
				continue;
			}
			
			System.arraycopy(split, 0, result, size, split.length);
			size += split.length;
			System.arraycopy(datas[i], 0, result, size, datas[i].length);
			size += datas[i].length;
		}
		
		byte[] r = new byte[size];
		System.arraycopy(result, 0, r, 0, r.length);
		
		return r;
	}
	
	/**
	 * 将字节数组按照指定字节数组进行分割.<br>
	 * 当分割后的字节数组为空将抛弃此数组<br>
	 * <pre>
	 * byte[] data = "hello,world".getBytes();
	 * byte[] split = ",".getBytes();
	 * byte[][] result = split(data, split);
	 * result[0] -> "hello"
	 * result[1] -> "world"
	 * 
	 * data = "hello,world,,,,,,,,123".getBytes();
	 * result -> ["hello", "world", "123"]
	 * 
	 * data = "red,green,blue,shendi".getBytes();
	 * result = split(data, split);
	 * result -> ["red", "green", "blue", "shendi"]
	 * 
	 * data = "I like,,this kit!".getBytes();  
	 * result = split(data, split);
	 * result -> ["I like", "this kit!"]
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data 要分割的字节数组
	 * @param split 分割数组
	 * @return 分割后的数组
	 */
	public static byte[][] split(byte[] data, byte[] split) {
		return split(data, split, 0);
	}
	
	/**
	 * 将字节数组按照指定字节数组进行分割,并指定分割数量,从左到右.<br>
	 * 当分割后的字节数组为空将抛弃此数组<br>
	 * <pre>
	 * byte[] data = "hello,world".getBytes();
	 * byte[] split = ",".getBytes();
	 * byte[][] result = split(data, split);
	 * result[0] -> "hello"
	 * result[1] -> "world"
	 * 
	 * data = "hello,world,,,,,,,,123".getBytes();
	 * result -> ["hello", "world", "123"]
	 * 
	 * data = "red,green,blue,shendi".getBytes();
	 * result = split(data, split);
	 * result -> ["red", "green", "blue", "shendi"]
	 * 
	 * data = "I like,,this kit!".getBytes();  
	 * result = split(data, split);
	 * result -> ["I like", "this kit!"]
	 * 
	 * data = "123,456,789,012".getBytes();
	 * result = split(data, split, 3)
	 * result -> ["123", "456", "789"]
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data 要分割的字节数组
	 * @param split 分割数组
	 * @param limit 分割的数量,小于等于0则分割全部.
	 * @return 分割后的数组
	 */
	public static byte[][] split(byte[] data, byte[] split, int limit) {
		return split(data, split, false, limit);
	}
	
	/** 同 split(data, split, canNull, 0); */
	public static byte[][] split(byte[] data, byte[] split, boolean canNull) {
		return split(data, split, canNull, 0);
	}
	
	/**
	 * 将字节数组按照指定字节数组进行分割,并指定分割数量,从左到右.<br>
	 * 当分割后的字节数组为空将抛弃此数组<br>
	 * <pre>
	 * byte[] data = "hello,world".getBytes();
	 * byte[] split = ",".getBytes();
	 * byte[][] result = split(data, split);
	 * result[0] -> "hello"
	 * result[1] -> "world"
	 * 
	 * data = "hello,world,,,,,,,,123".getBytes();
	 * result -> ["hello", "world", "123"]
	 * 
	 * data = "red,green,blue,shendi".getBytes();
	 * result = split(data, split);
	 * result -> ["red", "green", "blue", "shendi"]
	 * 
	 * data = "I like,,this kit!".getBytes();  
	 * result = split(data, split);
	 * result -> ["I like", "this kit!"]
	 * 
	 * data = "123,456,789,012".getBytes();
	 * result = split(data, split, 3)
	 * result -> ["123", "456", "789"]
	 * 
	 * result = split(data, split, true);
	 * data = "hello,world,,123".getBytes();
	 * result -> ["hello", null, "world", "123"]
	 * 
	 * data = ",".getBytes();
	 * result -> [null, null]
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data 		要分割的字节数组
	 * @param split		分割数组
	 * @param canNull 	是否可以包含空
	 * @param limit		分割的数量,小于等于0则分割全部.
	 * @return 分割后的数组
	 */
	public static byte[][] split(byte[] data, byte[] split, boolean canNull, int limit) {
		if (split == null) return null;
		if (split.length == 0) return null;
		
		// 数据没有要切割的则直接返回
		int index = indexOf(data, split);
		if (index == -1) {
			return new byte[][] {data};
		}
		
		// 复制一份,避免污染源数据
		byte[] cdata = new byte[data.length];
		System.arraycopy(data, 0, cdata, 0, data.length);
		
		byte[][] datas = new byte[data.length + 1][];
		int size = 0;
		
		// 开头即是分隔符,可以为空则置空
		if (index == 0) {
			if (canNull) size++;
			if (limit > 0 && size >= limit) return new byte[][] {datas[0]};
			cdata = subByte(cdata, split.length);
		} else {
			byte[] d = new byte[index];
			System.arraycopy(cdata, 0, d, 0, index);
			
			datas[size++] = d;
			
			if (limit > 0 && size >= limit) return new byte[][] {datas[0]};
			cdata = subByte(cdata, index + split.length);
		}
		
		for (int i = 0; i < datas.length; i++) {
			// 方式从左到右,找到一个分割一个
			index = indexOf(cdata, split);
			if (index == -1) {
				if (cdata.length > 0) {
					datas[size++] = cdata;
				} else if (canNull) size++;
				break;
			}
			
			// 连续的分隔符,可以为空则置空
			if (index == 0) {
				cdata = subByte(cdata, split.length);
				if (canNull) size++;
				if (limit > 0 && size >= limit) break;
				continue;
			}
			
			byte[] d = new byte[index];
			System.arraycopy(cdata, 0, d, 0, index);
			
			cdata = subByte(cdata, index + split.length);
			datas[size++] = d;
			
			if (limit > 0 && size >= limit) break;
		}
		
		byte[][] result = new byte[size][];
		for (int i = 0; i < result.length; i++) {
			result[i] = datas[i];
		}
		
		cdata = null;
		datas = null;
		
		return result;
	}
	
	/**
	 * 判断指定字节数组前缀是否为指定数组,与String.startsWith使用方法一致.<br>
	 * <pre>
	 * byte[] data = {1,2,3,4,5,6,7,1,2,3,4};
	 * byte[] start = {1,2,3};
	 * 
	 * boolean isStartsWith = ByteUtil.startsWith(data, start);
	 * isStartsWith -> true
	 * 
	 * data = {2,1,2,3,4,5,6,7,1,2,3,4};
	 * isStartsWith = ByteUtil.startsWith(data, start);
	 * isStartsWith -> false
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data		要判断的数组
	 * @param start		前缀数组
	 * @return true则代表data的前缀为start.
	 */
	public static boolean startsWith(byte[] data, byte[] start) {
		return startsWith(data, start, 0);
	}
	
	/**
	 * 判断指定字节数组前缀是否为指定数组,与String.startsWith使用方法一致.<br>
	 * <pre>
	 * byte[] data = {1,2,3,4,5,6,7,1,2,3,4};
	 * byte[] start = {1,2,3};
	 * 
	 * boolean isStartsWith = ByteUtil.startsWith(data, start, 0);
	 * isStartsWith -> true
	 * 
	 * isStartsWith = ByteUtil.startsWith(data, start, 7);
	 * isStartsWith -> true
	 * 
	 * isStartsWith = ByteUtil.startsWith(data, start, -1);
	 * isStartsWith -> false
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data		要判断的数组
	 * @param start		前缀数组
	 * @param offset	数据起始偏移
	 * @return true则代表data的前缀为start.
	 */
	public static boolean startsWith(byte[] data, byte[] start, int offset) {
		if (data == null || start == null || offset < 0) return false;
		if (offset >= data.length || data.length + offset < start.length) return false;
		
		for (int i = 0; i < start.length; i++) {
			if (data[i + offset] != start[i]) return false;
		}
		
		return true;
	}
	
	/**
	 * 判断指定数据是否以指定数据结尾.
	 * <pre>
	 * byte[] data = {1,2,3,4,5,6,7,1,2,3,4};
	 * byte[] end = {2,3,4};
	 * 
	 * boolean isEndWith = ByteUtil.endsWith(data, end);
	 * isEndWith ->true
	 * 
	 * data = {1,2,3,4,5,6,7,1,2,3,4,5};
	 * isEndWith = ByteUtil.endsWith(data, end);
	 * isEndWith -> false
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data	被判断的数据
	 * @param end	结尾的数据
	 * @return true代表data以end结尾
	 */
	public static boolean endsWith(byte[] data, byte[] end) {
		return endsWith(data, data.length, end);
	}
	
	/**
	 * 判断指定数据是否以指定数据结尾.
	 * <pre>
	 * byte[] data = {1,2,3,4,5,6,7,1,2,3,4};
	 * byte[] end = {2,3,4};
	 * 
	 * boolean isEndWith = ByteUtil.endsWith(data, data.length, end);
	 * isEndWith -> true
	 * 
	 * isEndWith = ByteUtil.endsWith(data, 4, end);
	 * isEndWith -> true
	 * 
	 * isEndWith = ByteUtil.endsWith(data, -1, end);
	 * isEndWith -> false
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data	被判断的数据
	 * @param len	数据的有效长度
	 * @param end	结尾的数据
	 * @return true代表data从len之前的数据end个数据与end相等
	 */
	public static boolean endsWith(byte[] data, int len, byte[] end) {
		if (data == null || end == null || len < 0) return false;
		if (len >= end.length) {
			for (int i = 1; i <= end.length; i++) {
				if (end[end.length - i] != data[len - i]) return false;
			}
			return true;
		} else {
			return false;
		}
	}
	
}
