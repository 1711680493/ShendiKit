package shendi.kit.annotation.scan;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import shendi.kit.ShendiKitInfo;
import shendi.kit.log.Log;

/**
 * 类扫描器,扫描所有的类并存起来.<br>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class ClassScan {
	
	/** 项目中所有的类,全路径与类一一对应. */
	private static final HashMap<String,Class<?>> CLASSES = new HashMap<>();
	
	/** 是否初始化了. */
	private static boolean isInit;
	
	/** 当前项目的类路径,如果当前项目已打成jar包,则为null. */
	private static String classPath;
	
	/**
	 * 扫描目录下所有类,存取.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param path 需要遍历的路径
	 */
	private static void scanClass(String path) {
		File[] files = new File(path).listFiles();
		
		for (File file : files) {
			String filePath = file.getPath();
			
			if (file.isDirectory()) {
				scanClass(filePath);
			} else if (file.getName().endsWith(ShendiKitInfo.CLASS_SUFFIX)) {
				disposeClass(filePath.substring(classPath.length()));
			}
		}
	}
	
	/**
	 * 扫描指定目录下的Jar包,获取对应类,用于JDK9以上(模块化).
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param path 项目根路径
	 */
	private static void scanJarByJava9(String path) {
		// classPath 目录则不需要处理
		if (path.equals(classPath)) { return; }
		File[] files = new File(path).listFiles();
		
		for (File file : files) {
			String filePath = file.getPath();
			
			if (file.isDirectory()) {
				scanJarByJava9(filePath);
			} else {
				disposeJar(filePath);
			}
		}
	}
	
	/**
	 * 处理指定路径的类.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param classPath 类的路径,带文件路径,例如 shendi/test/test.class
	 */
	private static void disposeClass(String classPath) {
		for (String classFilter : ShendiKitInfo.CLASS_FILTER)
			if (classPath.endsWith(classFilter)) return;
		
		try {
			String className = classPath.substring(0, classPath.length() - ShendiKitInfo.CLASS_SUFFIX.length())
					.replace(File.separatorChar, '.').replace('/', '.');
			
			Class<?> clazz = Class.forName(className);
			// 将类添加进集合
			CLASSES.put(className, clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Log.printAlarm("扫描到指定类文件,创建Class失败,找不到类: " + e.getMessage());
		}
	}
	
	/**
	 * 处理指定路径,为jar包则对应处理.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param jarPath 文件路径
	 */
	private static void disposeJar(String jarPath) {
		if (jarPath.endsWith(ShendiKitInfo.EXPORT_JAR_NAME)) return;
		if (jarPath.endsWith(ShendiKitInfo.JAR_SUFFIX)) {
			try {
				JarFile jar = new JarFile(jarPath);
				Enumeration<JarEntry> entry = jar.entries();
				
				while (entry.hasMoreElements()) {
					String name = entry.nextElement().getName();
					if (name.endsWith(ShendiKitInfo.CLASS_SUFFIX)) disposeClass(name);
				}
				
				jar.close();
			} catch (IOException e) {
				e.printStackTrace();
				Log.printErr("扫描Jar包进行处理时出错: " + e.getMessage());
			}
		}
	}
	
	/**
	 * 获取当前项目的所有类集合.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return {@link HashMap}
	 */
	public static HashMap<String, Class<?>> getClasses() {
		// 没有初始化则先初始化
		if (!isInit) {
			// 处理项目使用到的 Jar 包,
			String[] classPaths = System.getProperty("java.class.path").split(";");
			for (String classPath : classPaths) {
				disposeJar(classPath);
			}
			
			// 扫描整个项目,为空则可能是项目已打包,上述代码已做对应操作.
			URL resource = ClassScan.class.getResource("/");
			if (resource != null) {
				ClassScan.classPath = resource.getPath().substring(1);
				// Web 项目先扫描 lib 目录下的 jar
				if (classPath.endsWith("WEB-INF/classes/")) {
					File file = new File(classPath.substring(0, classPath.length() - 8) + "lib");
					if (file.exists()) {
						File[] files = file.listFiles();
						for (File f : files) disposeJar(f.getPath());
					}
				}
				scanClass(classPath);
			}
			
			// 模块化后的版本(1.9及以上)需要格外处理,上述操作没有处理任何类则做此操作.
			// 这种操作经过检验,只有在未打包的高版本普通Java项目中会出现.
			String[] version = System.getProperty("java.version").split("\\.");
			if (CLASSES.size() == 0 && version.length > 1 &&
					!(Integer.parseInt(version[0]) <= 1 && Integer.parseInt(version[1]) <= 8)) {
				String projectPath = System.getProperty("user.dir");
				// 路径以分隔符结尾,不然获取到的会以分隔符开头.
				ClassScan.classPath = projectPath + File.separatorChar + "bin" + File.separatorChar;
				scanClass(classPath);
				
				// 处理Jar,扫描根目录除开 classPath 目录
				scanJarByJava9(projectPath);
			}
			
			// 递归完会有大量垃圾,建议JVM回收
			System.gc();
			isInit = true;
		}
		return CLASSES;
	}
	
}
