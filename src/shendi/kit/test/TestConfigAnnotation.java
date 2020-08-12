package shendi.kit.test;

import shendi.kit.annotation.PConfig;
import shendi.kit.config.ConfigurationFactory;

/**
 * 测试配置的注解.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
class TestConfigAnnotation {
	public static void main(String[] args) throws Exception {
		// 获取类名
		String classA = ConfigurationFactory.getConfig("config_annotation").getProperty("a");
		String classB = ConfigurationFactory.getConfig("config_annotation").getProperty("b");
		System.out.println(classA);
		System.out.println(classB);
		
		// 直接获取类调用
		Class<?> clazzA = ConfigurationFactory.getPConfigAnnoClass("config_annotation", "a");
		TestConfigAnnotationS sA = (TestConfigAnnotationS) clazzA.getDeclaredConstructor().newInstance();
		sA.exe();
		Class<?> clazzB = ConfigurationFactory.getPConfigAnnoClass("config_annotation", "b");
		TestConfigAnnotationS sB = (TestConfigAnnotationS) clazzB.getDeclaredConstructor().newInstance();
		sB.exe();
	}
	
}

interface TestConfigAnnotationS {
	void exe();
}

@PConfig(config = "config_annotation", name = "a")
class TestConfigAnnotationA implements TestConfigAnnotationS {
	@Override
	public void exe() {
		System.out.println("我是TestConfigAnnotationA");
	}
}

@PConfig(config = "config_annotation", name = "b")
class TestConfigAnnotationB implements TestConfigAnnotationS {
	@Override
	public void exe() {
		System.out.println("我是TestConfigAnnotationB");
	}
}
