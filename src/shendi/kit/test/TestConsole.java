package shendi.kit.test;

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
	@CommandAnno(name="test2", info="测试2") public String test2 = "我是测试数据2";
	@CommandAnno(name="test3", info="测试3") public String test3 = "我是测试数据3";
	@CommandAnno(name="test4", info="测试4") public String test4 = "我是测试数据4";
	@CommandAnno(name="test5", info="测试5") public String test5 = "我是测试数据5";
	@CommandAnno(name="test6", info="测试6") public String test6 = "我是测试数据6";
	@CommandAnno(name="test7", info="测试7") public String test7 = "我是测试数据7";
	@CommandAnno(name="test8", info="测试8") public String test8 = "我是测试数据8";
	@CommandAnno(name="test9", info="测试9") public String test9 = "我是测试数据9";
	@CommandAnno(name="test10", info="测试10") public String test10 = "我是测试数据10";
	@CommandAnno(name="test11", info="测试11") public String test11 = "我是测试数据11";
	@CommandAnno(name="test12", info="测试12") public String test12 = "我是测试数据12";
	@CommandAnno(name="test13", info="测试13") public String test13 = "我是测试数据13";
	@CommandAnno(name="test14", info="测试14") public String test14 = "我是测试数据14";
	@CommandAnno(name="test15", info="测试15") public String test15 = "我是测试数据15";
	@CommandAnno(name="test16", info="测试16") public String test16 = "我是测试数据16";
	@CommandAnno(name="test17", info="测试17") public String test17 = "我是测试数据17";
	@CommandAnno(name="test18", info="测试18") public String test18 = "我是测试数据18";
	@CommandAnno(name="test19", info="测试19") public String test19 = "我是测试数据19";
	@CommandAnno(name="test20", info="测试20这个比较长 很长很长") public String test20 = "我是测试数据20";
	@CommandAnno(name="test222", info="测试222 这个是重新加载的测试数据") public String test222 = "我是测试数据222";
	
	@CommandAnno(name="version", info="当前类版本") public String version = "1.0";
	@CommandAnno(name="name", info="我的昵称") public String name = "Shendi";
	
	@CommandAnno(name="hello", info="你好")
	public String hello() {
		return "!你好呀";
	}
	@CommandAnno(name="what", info="表示疑问")
	public String what() {
		return "what are you doing?";
	}
}
