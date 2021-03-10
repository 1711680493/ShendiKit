package shendi.kit.console;

import java.util.HashMap;

import shendi.kit.annotation.ConsoleAnno;
import shendi.kit.annotation.scan.ConsoleScan;
import shendi.kit.console.command.Command;
import shendi.kit.console.command.ExecuteCommand;

/**
 * 控制台抽象类,实现此类来实现一个控制台视图<br>
 * <br>
 * 一个控制台类的主要职责为传递命令与显示命令执行结果<br>
 * 
 * <br>
 * 使用 {@link #register()} 来注册一个控制台<br>
 * 命令行控制台: {@link CommandConsole}<br>
 * 窗体控制台: {@link DeskAppConsole}<br>
 * <br>
 * 1.1版本新增可通过组注册控制台.<br>
 * <br>
 * <b>内置命令</b>
 * <table>
 * 	<tr>
 * 		<th>名称</th>
 * 		<th>描述</th>
 * 		<th>参数</th>
 * 	</tr>
 * 	<tr>
 * 		<td>execute</td>
 * 		<td>执行Java语句</td>
 * 		<td>
 * 			<ul>
 * 				<li>/c(指定执行的语句)</li>
 * 			</ul>
 * 		</td>
 * 	</tr>
 * </table>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.1
 * @see Command
 * @see CommandConsole
 * @see DeskAppConsole
 */
public abstract class Console {
	
	/** 控制台的命令集合 */
	protected HashMap<String, Command> commands;
	
	/** 命令扫描器 */
	protected ConsoleScan cs = new ConsoleScan();
	
	/** 当前控制台命令所在组 */
	protected String group;
	
	/** 默认控制台命令,默认组. */
	public Console() {}
	
	/**
	 * 在单独的线程注册控制台,使用默认控制台命令组.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	public void register() { register(ConsoleAnno.DEFAULT_GROUP); }
	
	/**
	 * 在单独的线程注册控制台,使用指定的控制台命令组.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param group 命令组
	 */
	public void register(String group) {
		this.group = group;
		
		cs.init();
		
		this.commands = cs.getCommand(group);
		
		// 初始化内置命令
		initCommand();
		
		new Thread(() -> register(commands)).start();
	}
	
	/**
	 * 控制台注册操作,根据命令集合创建控制台,关于命令,请参阅 {@link Command}<br>
	 * 控制台注册应接收用户输入并解析,获取对应命令,并执行对应操作<br>
	 * 提供了函数 {@link #execute(String)} 用以解析执行命令,并返回命令执行结果.<br>
	 * <br>
	 * 在此类已保存命令集合 {@link #commands}<br>
	 * <br>
	 * 可参考 {@link CommandConsole}
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param commands 命令集合
	 * @see CommandConsole
	 */
	protected abstract void register(HashMap<String, Command> commands);
	
	/**
	 * 销毁当前控制台.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	public abstract void destroy();
	
	/**
	 * 解析并执行命令,对于如何解析/命令格式,可以参考说明文档 (<a href='https://github.com/1711680493/ShendiKit/blob/master/README.md'>README.md</a>).
	 * @param input 用户输入的命令
	 * @return 命令执行结果,null则没有此命令
	 */
	public String execute(String input) {
		// 命令格式为: 命令 /参数名 [参数值] /参数名 [参数值]...
		// 注意空格,其中每一个参数名对应一个参数值,没有参数值则此参数的值为""
		// 每个/参数名 前都必须有一个空格
		String[] split = input.split("/");
		String name = split[0].trim();
		
		Command c = commands.get(name);
		if (c == null) return null;
		if (split.length < 2) return c.execute();
		
		HashMap<String, String> args = new HashMap<String, String>();
		for (int i = 1; i < split.length; i++) {
			String arg = split[i].trim();
			int len = arg.indexOf(' ');
			if (len == -1) args.put(arg, "");
			else args.put(arg.substring(0, len), arg.substring(len + 1));
		}
		
		String result = c.execute(args);
		return result == null ? "" : result;
	}
	
	/**
	 * 重新加载命令,用于热更.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 加载后的命令集合
	 */
	protected HashMap<String, Command> reload() {
		cs.reload(group);
		commands = cs.getCommand(group);
		
		// 重新加载也需要重新添加内置命令
		initCommand();
		
		return commands;
	}
	
	/**
	 * 初始化内置命令,内置命令将在控制台吧被初始化时,命令被重新加载时添加进命令集合,如果集合中已有重名命令则直接覆盖.<br>
	 * 子类可以重写此函数以移除当前控制台的内置命令<br>
	 * 对于新增内置命令,可以重写 extraCommand 函数
	 */
	protected void initCommand() {
		commands.put("execute", new ExecuteCommand());
		
		// 内置命令扩展
		extraCommand();
	}
	
	/** 如果想要添加控制台内置命令,则需要重写此函数,此函数职责为创建命令添加进入 {@link #commands}. */
	protected void extraCommand() {}
}
