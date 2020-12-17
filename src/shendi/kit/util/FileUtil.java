package shendi.kit.util;

import java.io.File;
import java.io.FileOutputStream;

import shendi.kit.path.PathFactory;
import shendi.kit.path.ProjectPath;

/**
 * 文件工具类.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since 1.1
 */
public class FileUtil {
	
	/**
	 * 使用指定字节数据替换对应路径的文件,如果文件不存在则新建.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param path 路径
	 * @param data 数据
	 */
	public static void update(String path, byte[] data) {
		File file = new File(path);
		if (!file.getParentFile().exists()) { file.mkdirs(); }
		try (FileOutputStream output = new FileOutputStream(file)) {
			output.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 使用指定字节数据替换项目中的文件,如果文件不存在则新建.<br>
	 * 项目根路径参考 {@link ProjectPath}.
	 * <pre>
	 * 在将项目根目录下的Test.class内容替换为data
	 * updateByPro("/Test.class", data);
	 * </pre>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param path 相对于项目的路径,以/开头
	 * @param data 文件数据
	 */
	public static void updateByPro(String path, byte[] data) {
		update(PathFactory.get(PathFactory.PROJECT).getPath(path), data);
	}
	
}
