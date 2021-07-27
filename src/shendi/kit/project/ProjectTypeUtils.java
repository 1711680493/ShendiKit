package shendi.kit.project;

/**
 * 项目类型工具类.<br>
 * 通过此类可以获取到当前项目的类型.<br>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since ShendiKit 1.0
 * @see #TYPE
 */
public class ProjectTypeUtils {
	/**
	 * 代表当前项目的类型.
	 * @since 1.1(有时需要自己设定,改为非final)
	 */
	public static ProjectType type;
	
	static {
		//获取项目类型
		type = new ProjectTypeUtils().getProjectType();
	}
	
	/**
	 * 获取当前项目的类型 只执行一次
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 当前项目的类型
	 */
	private ProjectType getProjectType() {
		try {
			Class.forName("javax.servlet.Servlet");
			return ProjectType.JavaWeb;
		} catch (ClassNotFoundException e) {
			return ProjectType.Java;
		}
	}
}
