package shendi.kit.console;

import java.util.HashMap;

import shendi.kit.annotation.scan.ConsoleScan;
import shendi.kit.data.Command;

/**
 * 控制台抽象类,实现此类来实现一个控制台视图<br>
 * 使用 {@link #register()} 来注册一个控制台<br>
 * 命令行控制台: {@link CommandConsole}<br>
 * 窗体控制台: {@link DeskAppConsole}
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @see Command
 * @see CommandConsole
 * @see DeskAppConsole
 */
public abstract class Console {
	
	/** 控制台的命令集合 */
	protected HashMap<String, Command> commands;
	
	/** 命令扫描器 */
	protected ConsoleScan cs = new ConsoleScan();
	
	public void register() {
		cs.init();
		
		this.commands = cs.getCommand();
		
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
		cs.reload();
		commands = cs.getCommand();
		return commands;
	}
}
