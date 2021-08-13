package shendi.kit.path;

import shendi.kit.project.ProjectTypeUtils;

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
//		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
//		StackTraceElement callInfo = stackTraceElements[stackTraceElements.length - 1];
//		String path = null;
//		String packageName = null;
//		try {
//			Class<?> c = Class.forName(callInfo.getClassName());
//			// 获取到此类所在目录 - 包名则是源目录
//			path = c.getResource("").getPath();
//			packageName = c.getPackage().getName().replace(".", "/");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		RESOURCE_PATH = path.substring(1, path.indexOf(packageName));
		String path  = null;
		switch (ProjectTypeUtils.type) {
		case JavaWeb:
			Object pathObj = ResourcePath.class.getResource("/");
			//路径不为空则获取指定文件判断是否有
			if (pathObj != null) {
				path = ResourcePath.class.getResource("/").getPath().concat("../../");
			}
			break;
		case Java:
			path = System.getProperty("user.dir");
		}
		RESOURCE_PATH = path + "/bin/";
	}
	
	@Override public String getPath(String path) {
		return RESOURCE_PATH.concat(path);
	}
	
}
