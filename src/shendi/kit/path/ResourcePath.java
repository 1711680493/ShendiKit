package shendi.kit.path;

import java.net.URL;

/**
 * 从项目中的源路径获取指定文件路径
 * <br>
 * 一般为 bin 目录下.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class ResourcePath implements Path {

	/** bin 目录 */
	private static final String RESOURCE_PATH;
	static {
		URL r = ClassLoader.getSystemResource("");
		if (r == null) {
			RESOURCE_PATH = ResourcePath.class.getResource("/").getPath().substring(1);
		} else {
			RESOURCE_PATH = r.getPath().substring(1);
		}
	}
	
	@Override public String getPath(String path) {
		return RESOURCE_PATH.concat(path);
	}
	
}
