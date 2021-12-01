package shendi.kit.test;

import shendi.kit.util.SKClassLoader;

/**
 * 测试类加载器的重新加载
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class TestClassLoader {
	public static void main(String[] args) throws Exception {
		new TestClassLoader().a();
		Thread.sleep(5000);
		Class<?> rc = SKClassLoader.reloadClass("shendi.kit.test.TestClassLoader");
		rc.getMethod("a").invoke(rc.getConstructor().newInstance());
	}
	
	public void a() {
		// 在运行后五秒内请修改此输出来查看是否重新加载
		System.out.println("dde");
	}
	
}
