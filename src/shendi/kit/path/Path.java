package shendi.kit.path;

/**
 * 通常,我们会需要获取一些路径,比如项目根路径等.<br>
 * 此接口用于获取对应的路径.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @since ShendiKit 1.0
 * @version 1.0
 * @see ProjectPath
 */
public interface Path {
	/**
	 * 根据给定的相对路径获取绝对路径
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param path 相对路径
	 * @return 处理后的绝对路径
	 */
	String getPath(String path);
}
