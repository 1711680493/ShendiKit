package shendi.kit.path;

import shendi.kit.log.Log;
import shendi.kit.project.ProjectTypeUtils;

/**
 * 从项目的绝对路径获取指定文件,返回指定文件的路径<br>
 * (目前web项目 和 普通Java项目是没有问题的)<br>
 * 自己的类不能有名为Servlet的类
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @since ShendiKit 1.0
 * @version 1.0
 */
public final class ProjectPath implements Path {
	
	/**
	 * 根据传递的参数从项目根目录获取指定文件的路径.<br>
	 * <table border='1'>
	 * 	<tr>
	 * 		<th>Type</th>
	 * 		<th>path</th>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>Java项目</td>
	 * 		<td>当前项目的文件夹下</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>Web项目</td>
	 * 		<td>项目的WebContent下</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>Spring Boot项目</td>
	 * 		<td>项目的根目录</td>
	 * 	</tr>
	 * </table>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param path 传递 "" 代表为获取项目根路径,如果想要获取项目下的xx.txt文件,path应为 /xx.txt
	 * @return 返回对应文件的路径.
	 */
	public String getPath(String path) {
		//根据不同项目 返回不同路径
		switch (ProjectTypeUtils.TYPE) {
		case JavaWeb:
			Object pathObj = ProjectPath.class.getResource("/");
			//路径不为空则获取指定文件判断是否有
			if (pathObj != null) {
				return ProjectPath.class.getResource("/").getPath().concat("../../").concat(path);
			}
			Log.printErr("从项目路径获取文件路径失败 path=" + pathObj);
			break;
		case Java:
			return System.getProperty("user.dir") + path;
		default:
			Log.printErr("从项目路径获取文件路径失败,当前项目类型未知");
			return null;
		}
		return null;
	}
	
}
