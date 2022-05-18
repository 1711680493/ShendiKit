package shendi.kit.console;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

import shendi.kit.console.command.Command;

/**
 * 命令行控制台.<br>
 * 自带两个命令 help(显示所有命令),reload(重新加载命令),exit(退出)<br>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class CommandConsole extends Console {

	private static Scanner sc = new Scanner(System.in);
	
	@Override protected void register(HashMap<String, Command> commands) {
		System.out.println("************************");
		System.out.println("******命令行控制台******");
		System.out.println("-自带命令---------------");
		System.out.println("-help		显示命令列表");
		System.out.println("-reload		重新加载命令");
		System.out.println("-exit		关闭控制台");
		System.out.println("*****输入命令以操作*****");
		System.out.println("************************");
		System.out.println("########欢迎使用########");
		while (true) {
			String command = sc.nextLine();
			if ("help".equals(command)) {
				System.out.println("1.help\t\t显示命令列表");
				System.out.println("2.reload\t\t重新加载命令");
				System.out.println("3.exit\t\t关闭控制台");

				int num = 4;
				Iterator<Entry<String, Command>> it = commands.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, Command> next = it.next();
					System.out.print(num++);
					System.out.print('.');
					System.out.print(next.getKey());
					System.out.print("\t\t");
					System.out.println(next.getValue().getInfo());
				}
				System.out.println("*------------------------*");
			} else if ("reload".equals(command)) {
				commands = reload();
				System.out.println("重新加载完毕.");
			} else if ("exit".equals(command)) {
				System.out.println("*--已关闭...");
				break;
			} else {
				String result = execute(command);
				if (result == null) {
					System.out.println("*--没有此命令");
				} else {
					System.out.println(result);
				}
			}
		}
	}

	@Override public void destroy() {}

}
