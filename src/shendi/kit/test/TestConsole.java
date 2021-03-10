package shendi.kit.test;

import java.util.HashMap;

import shendi.kit.annotation.CommandAnno;
import shendi.kit.annotation.ConsoleAnno;
import shendi.kit.console.CommandConsole;

/**
 * 测试控制台.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
@ConsoleAnno
public class TestConsole {
	public static void main(String[] args) {
		new CommandConsole().register();
//		new DeskAppConsole().register();
	}
	
	@CommandAnno(name="test1", info="测试1") public String test1 = "我是测试数据1";
	@CommandAnno(name="ti", info="测试int类型数据") public int testInt = 123;
	
	@CommandAnno(name="version", info="当前类版本") public String version = "1.0";
	@CommandAnno(name="name", info="我的昵称") public String name = "Shendi";
	
	@CommandAnno(name="hello", info="你好")
	public String hello(HashMap<String,String> args) {
		System.out.println("hello函数接收到name参数为: " + args.get("name"));
		return "!你好呀";
	}
	
	@CommandAnno(name="what", info="表示疑问")
	public String what(HashMap<String,String> args) {
		return "what are you doing?";
	}
}
