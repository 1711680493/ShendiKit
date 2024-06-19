package shendi.kit;

/**
 * 工具集的一些信息,包含版本,名称等.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class ShendiKitInfo {
	
	/** 版本号 */
	public static final int VERSION = 3;
	
	/** 版本名 */
	public static final String VERSION_NAME = "1.1.3";
	
	/** 包名 */
	public static final String PACKAGE_NAME = "shendi-kit";
	
	/** class 文件的后缀 */
	public static final String CLASS_SUFFIX = ".class";
	
	/** jar 文件的后缀 */
	public static final String JAR_SUFFIX = ".jar";
	
	/** 导出的 Jar包名称 */
	public static final String EXPORT_JAR_NAME = PACKAGE_NAME + '-' + VERSION_NAME + JAR_SUFFIX;
	
	/** 需要过滤的 class 文件 */
	public static final String[] CLASS_FILTER = {"module-info.class", "package-info.class"};
}
