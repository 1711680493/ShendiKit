package shendi.kit.path;

import java.util.HashMap;

/**
 * 路径工厂.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class PathFactory {

	/** 路径池 */
	private static final HashMap<String, Path> PATHS = new HashMap<>();
	
	/** 项目路径 */
	public static final String PROJECT = "project";
	
	/** 源路径 */
	public static final String RESOURCE = "resource";
	
	static {
		PATHS.put(PROJECT, new ProjectPath());
		PATHS.put(RESOURCE, new ResourcePath());
	}
	
	/**
	 * 获取对应路径对象,获取后通过 getPath() 方法来获取对应的文件路径.
	 * <table border='1'>
	 * 	<tr>
	 * 		<th>名称</th>
	 * 		<th>描述</th>
	 * 		<th>类</th>
	 * 	<tr>
	 * 	<tr>
	 * 		<td>{@link #PROJECT}</td>
	 * 		<td>项目路径</td>
	 * 		<td>{@link ProjectPath}</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link #RESOURCE}</td>
	 * 		<td>项目源路径</td>
	 * 		<td>{@link ResourcePath}</td>
	 * 	</tr>
	 * </table>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name mi路径对象对应的名称
	 * @return {@link Path}
	 */
	public static Path get(String name) { return PATHS.get(name); }
	
}
