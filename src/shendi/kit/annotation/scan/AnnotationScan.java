package shendi.kit.annotation.scan;

/**
 * 注解扫描处理的接口,通过 {@link ClassScan} 扫描到的类进行初始化.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @see ClassScan
 */
public interface AnnotationScan {

	/**
	 * 初始化对应注解.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	void init();
	
}
