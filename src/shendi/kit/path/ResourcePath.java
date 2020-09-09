package shendi.kit.path;

/**
 * 从项目中的源路径获取指定文件路径
 * <br>
 * 一般为 bin 目录下.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class ResourcePath implements Path {

	/** bin 目录 */
	private static final String RESOURCE_PATH = ClassLoader.getSystemResource("").getPath().substring(1);
	
	@Override public String getPath(String path) {
		System.out.println(RESOURCE_PATH);
		return RESOURCE_PATH.concat(path);
	}

}
