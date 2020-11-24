package shendi.kit.console;

import java.util.HashMap;

import shendi.kit.annotation.ConsoleAnno;
import shendi.kit.annotation.scan.ConsoleScan;
import shendi.kit.data.Command;

/**
 * 控制台抽象类,实现此类来实现一个控制台视图<br>
 * 使用 {@link #register()} 来注册一个控制台<br>
 * 命令行控制台: {@link CommandConsole}<br>
 * 窗体控制台: {@link DeskAppConsole}<br>
 * <br>
 * 1.1版本新增可通过组注册控制台.
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
		
		new Thread(() -> register(commands)).start();
	}
	
	/**
	 * 注册控制台,根据对应命令执行对应操作,请参阅 {@link Command}<br>
	 * <br>
	 * 在此类已保存命令集合 {@link #commands}
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param commands 命令集合
	 */
	protected abstract void register(HashMap<String, Command> commands);
	
	/**
	 * 销毁当前控制台.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	public abstract void destroy();
	
	/**
	 * 重新加载命令,用于热更.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 加载后的命令集合
	 */
	protected HashMap<String, Command> reload() {
		cs.reload(group);
		commands = cs.getCommand(group);
		return commands;
	}
}
