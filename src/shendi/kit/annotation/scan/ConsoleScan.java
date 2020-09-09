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
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class ConsoleScan implements AnnotationScan {
	
	/** 控制台命令集合 k为命令名,v为具体命令 */
	private final HashMap<String, Command> COMMANDS = new HashMap<>();
	
	@Override
	public void init() {
		ClassScan.getClasses().forEach((k,v) -> {
			// 检索有控制台注解的类,创建此类对象并获取有命令注解的元素包装存取
			ConsoleAnno anno = v.getAnnotation(ConsoleAnno.class);
			if (anno != null) {
				try {
					Constructor<?> con = v.getConstructor();
					Object obj = con.newInstance();
					
					// 扫描字段
					Field[] fs = v.getFields();
					for (Field f : fs) {
						CommandAnno command = f.getAnnotation(CommandAnno.class);
						if (command != null) {
							String value = command.name();
							if ("".equals(value)) {
								StringBuilder build = new StringBuilder(50);
								build.append("此字段的命令注解名称是空,请设置此命令名称 By: ");
								build.append(k); build.append('.');
								build.append(f.getName());
								Log.printErr(build);
								continue;
							} else if (COMMANDS.containsKey(value)) {
								StringBuilder build = new StringBuilder(50);
								build.append("此字段的命令注解名称已存在,请更改此命令名称 By: ");
								build.append(k); build.append('.');
								build.append(f.getName());
								Log.printErr(build);
								continue;
							}
							
							Command c = new Command(v, f, obj, command.info());
							COMMANDS.put(value, c);
						}
					}

					// 扫描方法
					Method[] ms = v.getMethods();
					for (Method m : ms) {
						CommandAnno command = m.getAnnotation(CommandAnno.class);
						if (command != null) {
							String value = command.name();
							if ("".equals(value)) {
								StringBuilder build = new StringBuilder(50);
								build.append("此方法的命令注解名称是空,请设置此命令名称 By: ");
								build.append(k); build.append('.');
								build.append(m.getName()); build.append("()");
								Log.printErr(build);
								continue;
							} else if (COMMANDS.containsKey(value)) {
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
									Command c = new Command(v, m, obj, command.info());
									COMMANDS.put(value, c);
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
	 * 重新加载命令
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	public void reload () {
		Collection<Command> values = COMMANDS.values();
		values.forEach((v) -> ClassScan.reload(v.getClazz().getName()));
		
		COMMANDS.clear();
		init();
	}
	
	/**
	 * 获取命令集合.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return HashMap
	 */
	public HashMap<String, Command> getCommand() { return COMMANDS; }
	
}
