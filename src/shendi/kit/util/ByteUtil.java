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
	
}
