package shendi.kit.annotation.scan;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

import shendi.kit.annotation.CommandAnno;
import shendi.kit.annotation.ConsoleAnno;
import shendi.kit.data.Command;
import shendi.kit.log.Log;

/**
 * 控制台注解扫描器.
 * <br>
 * 1.1新增设置组
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.1
 */
public class ConsoleScan implements AnnotationScan {
	
	/** 控制台命令组 k为组名,v为此命令组集合 */
	private final HashMap<String, HashMap<String, Command>> CONSOLE_GROUP = new HashMap<>();
	
	/** 初始化默认命令组 */
	public ConsoleScan() {
		CONSOLE_GROUP.put(ConsoleAnno.DEFAULT_GROUP, new HashMap<>());
	}
	
	@Override public void init() {
		ClassScan.getClasses().forEach((k,v) -> {
			// 检索有控制台注解的类,创建此类对象并获取有命令注解的元素包装存取
			ConsoleAnno anno = v.getAnnotation(ConsoleAnno.class);
			if (anno != null) {
				// 控制台命令组
				String group = anno.value();
				
				if (!CONSOLE_GROUP.containsKey(group))
					CONSOLE_GROUP.put(group, new HashMap<>());
				// 当前类对应组的命令集合
				HashMap<String, Command> commands = CONSOLE_GROUP.get(group);
				
				try {
					Constructor<?> con = v.getConstructor();
					Object obj = con.newInstance();
					
					// 扫描字段
					Field[] fs = v.getFields();
					for (Field f : fs) {
						CommandAnno command = f.getAnnotation(CommandAnno.class);
						
						if (command != null) {
							String value = command.name();
							
							// 指定了组则加入格外组,1.1新增
							if (command.group().equals(ConsoleAnno.DEFAULT_GROUP)) {
								if (commands.containsKey(value)) {
									StringBuilder build = new StringBuilder(50);
									build.append("此字段的命令注解名称已存在,请更改此命令名称 By: ");
									build.append(k); build.append('.');
									build.append(f.getName());
									Log.printErr(build);
									continue;
								}
								commands.put(value, new Command(v, f, obj, command.info()));
							} else {
								if (!CONSOLE_GROUP.containsKey(command.group())) {
									CONSOLE_GROUP.put(command.group(), new HashMap<>());
								}
								
								if (CONSOLE_GROUP.get(command.group()).containsKey(value)) {
									StringBuilder build = new StringBuilder(50);
									build.append("此字段的命令注解名称已存在,请更改此命令名称 By: ");
									build.append(k); build.append('.');
									build.append(f.getName());
									Log.printErr(build);
									continue;
								}
								CONSOLE_GROUP.get(command.group()).put(value, new Command(v, f, obj, command.info()));
							}
						}
					}

					// 扫描方法
					Method[] ms = v.getMethods();
					for (Method m : ms) {
						CommandAnno command = m.getAnnotation(CommandAnno.class);
						
						if (command != null) {
							String value = command.name();
							
							if (m.getParameterCount() == 0) {
								Class<?> rt = m.getReturnType();
								if (rt == String.class) {
									// 指定了组则加入格外组,1.1新增
									if (command.group().equals(ConsoleAnno.DEFAULT_GROUP)) {
										if (commands.containsKey(value)) {
											StringBuilder build = new StringBuilder(50);
											build.append("此方法的命令注解名称已存在,请更改此命令名称 By: ");
											build.append(k); build.append('.');
											build.append(m.getName()); build.append("()");
											Log.printErr(build);
											continue;
										}
										commands.put(value, new Command(v, m, obj, command.info()));
									} else {
										if (!CONSOLE_GROUP.containsKey(command.group())) {
											CONSOLE_GROUP.put(command.group(), new HashMap<>());
										}
										
										if (CONSOLE_GROUP.get(command.group()).containsKey(value)) {
											StringBuilder build = new StringBuilder(50);
											build.append("此方法的命令注解名称已存在,请更改此命令名称 By: ");
											build.append(k); build.append('.');
											build.append(m.getName()); build.append("()");
											Log.printErr(build);
											continue;
										}
										CONSOLE_GROUP.get(command.group()).put(value, new Command(v, m, obj, command.info()));
									}
								} else {
									StringBuilder build = new StringBuilder(50);
									build.append("试图捕获控制台命令时出错,此方法的返回值必须是java.lang.String: ");
									build.append(k); build.append('.');
									build.append(m.getName()); build.append("()");
									Log.printErr(build);
								}
							} else {
								StringBuilder build = new StringBuilder(50);
								build.append("试图捕获控制台命令时出错,此方法必须是无参的,请移除参数: ");
								build.append(k); build.append('.');
								build.append(m.getName()); build.append("()");
								Log.printErr(build);
							}
						}
					}
				} catch (Exception e) {
					Log.printErr("试图创建控制台类对象时出错,请检查此类是否有无参构造: " + k + ",错误信息为: " + e.getMessage());
				}
			}
		});
	}
	
	/**
	 * 重新加载默认组的命令
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	public void reload() { reload(ConsoleAnno.DEFAULT_GROUP); }
	
	/**
	 * 重新加载指定组的命令
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param group 命令组
	 */
	public void reload(String group) {
		HashMap<String, Command> commands = CONSOLE_GROUP.get(group);
		if (commands == null) Log.printErr("当前命令组不存在!");
		
		Collection<Command> values = commands.values();
		values.forEach((v) -> ClassScan.reload(v.getClazz().getName()));
		
		commands.clear();
		
		// 重新扫描
		ClassScan.getClasses().forEach((k,v) -> {
			ConsoleAnno anno = v.getAnnotation(ConsoleAnno.class);
			
			if (anno != null) {
				boolean classGroupOk = anno.value().equals(group);
				
				try {
					Constructor<?> con = v.getConstructor();
					Object obj = con.newInstance();
					
					// 扫描字段
					Field[] fs = v.getFields();
					for (Field f : fs) {
						CommandAnno command = f.getAnnotation(CommandAnno.class);
						if (command != null) {
							String value = command.name();
							if (classGroupOk) {
								if (!(command.group().equals(group) || command.group().equals(ConsoleAnno.DEFAULT_GROUP)))
									continue;
							} else {
								if (!command.group().equals(group))
									continue;
							}
							
							if (commands.containsKey(value)) {
								StringBuilder build = new StringBuilder(50);
								build.append("此字段的命令注解名称已存在,请更改此命令名称 By: ");
								build.append(k); build.append('.');
								build.append(f.getName());
								Log.printErr(build);
								continue;
							}
							
							commands.put(value, new Command(v, f, obj, command.info()));
						}
					}

					// 扫描方法
					Method[] ms = v.getMethods();
					for (Method m : ms) {
						CommandAnno command = m.getAnnotation(CommandAnno.class);
						if (command != null) {
							String value = command.name();
							if (classGroupOk) {
								if (!(command.group().equals(group) || command.group().equals(ConsoleAnno.DEFAULT_GROUP)))
									continue;
							} else {
								if (!command.group().equals(group))
									continue;
							}
							
							if (commands.containsKey(value)) {
								StringBuilder build = new StringBuilder(50);
								build.append("此方法的命令注解名称已存在,请更改此命令名称 By: ");
								build.append(k); build.append('.');
								build.append(m.getName()); build.append("()");
								Log.printErr(build);
								continue;
							}
							
							if (m.getParameterCount() == 0) {
								Class<?> rt = m.getReturnType();
								if (rt == String.class) {
									commands.put(value, new Command(v, m, obj, command.info()));
								} else {
									StringBuilder build = new StringBuilder(50);
									build.append("试图捕获控制台命令时出错,此方法的返回值必须是java.lang.String: ");
									build.append(k); build.append('.');
									build.append(m.getName()); build.append("()");
									Log.printErr(build);
								}
							} else {
								StringBuilder build = new StringBuilder(50);
								build.append("试图捕获控制台命令时出错,此方法必须是无参的,请移除参数: ");
								build.append(k); build.append('.');
								build.append(m.getName()); build.append("()");
								Log.printErr(build);
							}
						}
					}
				} catch (Exception e) {
					Log.printErr("试图创建控制台类对象时出错,请检查此类是否有无参构造: " + k + ",错误信息为: " + e.getMessage());
				}
			}
		});
	}
	
	/**
	 * 获取默认命令组集合.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return HashMap
	 */
	public HashMap<String, Command> getCommand() { return getCommand(ConsoleAnno.DEFAULT_GROUP); }
	
	/**
	 * 获取指定命令组集合.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param group 命令组
	 * @return HashMap
	 */
	public HashMap<String, Command> getCommand(String group) { return CONSOLE_GROUP.get(group); }
	
	/**
	 * 获取所有控制台命令组.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 控制台命令组集合
	 */
	public HashMap<String, HashMap<String, Command>> getConsoleGroup() { return CONSOLE_GROUP; }
	
}
