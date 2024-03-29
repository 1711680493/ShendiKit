package shendi.kit.annotation.scan;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import shendi.kit.ShendiKitInfo;
import shendi.kit.annotation.ConsoleAnno;
import shendi.kit.annotation.EncryptAnno;
import shendi.kit.annotation.PConfig;
import shendi.kit.log.Log;
import shendi.kit.path.PathFactory;
import shendi.kit.util.Entry;
import shendi.kit.util.SKClassLoader;

/**
 * 类扫描器,扫描所有的类的注解并存起来.<br>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class ClassScan {
	
	/** 当前工具包的注解类 */
	public static final List<Class<? extends Annotation>> ANNOS = new ArrayList<>();
	
	/** 扫描了多少个文件 */
	private static int scanNum;
	
	/** 需要处理的 jar 包数组[[jar文件后缀],[此jar的类所在目录]],泛型为?则不会爆警报 */
	private static Entry<?, ?>[] jars;
	
	/** 是否初始化了. */
	private static boolean isInit;
	
	/** 系统类加载器 */
	private static final ClassLoader CL = ClassScan.class.getClassLoader();
	
	static {
		ANNOS.add(PConfig.class);
		ANNOS.add(EncryptAnno.class);
		ANNOS.add(ConsoleAnno.class);
		ANNOS.add(PConfig.class);
		
		// 获取需要处理的 jar 包名.
		File scanFile = new File(PathFactory.getPath(PathFactory.PROJECT, File.separatorChar + "files" + File.separatorChar + "/anno_scan.shendi"));
		if (scanFile.exists() && scanFile.isFile()) {
			try (FileInputStream input = new FileInputStream(scanFile)) {
				byte[] data = new byte[input.available()];
				input.read(data);
				
				if (data.length == 0) {
					Log.printErr("配置文件 anno_scan.shendi 内容为空,请正确填写使用此工具包注解的路径以便正常使用!");
				} else {
					// 数据为No则不扫描
					String d = new String(data);
					if ("No".equals(d)) {
						isInit = true;
					} else if ("No Jar".equals(d)) {
						jars = null;
					} else {
						String[] split = d.split(System.lineSeparator());
						jars = new Entry[split.length];
						for (int i = 0; i < split.length; i++) {
							int len = split[i].indexOf('=');
							if (len == -1 || len == split[i].length() - 1) jars[i] = new Entry<>(split[i], null);
							else {
								jars[i] = new Entry<>(split[i].substring(0, len), split[i].substring(len + 1));
							}
						}
					}
				}
			} catch (IOException e) {
				Log.printErr("在获取配置文件 anno_scan.shendi 时出错,请检查此文件是否被占用: " + e.getMessage());
			}
		} else {
			Log.printErr("获取配置文件 /files/anno_scan.shendi 出错,请检查是否有此文件,如果没有则会影响到注解的使用!");
		}
		
	}
	
	/** 项目中所有有工具包注解的类,全路径与类一一对应. */
	private static final Map<String, Class<?>> CLASSES = new HashMap<>();
	
	/** 当前项目的类路径,如果当前项目已打成jar包,则为null. */
	private static String classPath;
	
	private ClassScan() {}
	
	/**
	 * 扫描目录下所有类,存取.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param path 需要遍历的路径
	 */
	private static void scanClass(String path) {
		File[] files = new File(path).listFiles();
		if (files != null) {
			for (File file : files) {
				String filePath = file.getPath();
				
				if (file.isDirectory()) {
					scanClass(filePath);
				} else if (file.getName().endsWith(ShendiKitInfo.CLASS_SUFFIX)) {
					disposeClass(filePath.substring(classPath.length()));
				}
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
		++scanNum;
		for (String classFilter : ShendiKitInfo.CLASS_FILTER)
			if (classPath.endsWith(classFilter)) return;
		String className = null;
		try {
			className = classPath.substring(0, classPath.length() - ShendiKitInfo.CLASS_SUFFIX.length())
					.replace(File.separatorChar, '.').replace('/', '.');
			Class<?> clazz = CL.loadClass(className);
			
			// 将类添加进集合
			Annotation[] annos = clazz.getAnnotations();
			if (annos.length > 0) {
				for (int i = 0;i < ANNOS.size(); i++) {
					for (int j = 0;j < annos.length;j++) {
						if (ANNOS.get(i) == annos[j].annotationType()) {
							CLASSES.put(className, clazz);
							return;
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			Log.printAlarm("扫描到指定类文件,创建Class失败,找不到类: " + e.getMessage());
		} catch (ExceptionInInitializerError e) {
			Log.printErr("加载此类时出错: " + className + ']' + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Log.printErr("在加载类时出现未知错误: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理指定路径,为jar包并在jars中有则对应处理.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param jarPath 文件路径
	 */
	private static void disposeJar(String jarPath) {
		if (jars == null || jarPath == null) return;
		
		for (Entry<?, ?> jar : jars) {
			if (jarPath.endsWith(jar.key.toString().trim())) {
				if (jarPath.endsWith(ShendiKitInfo.EXPORT_JAR_NAME)) return;
				if (jarPath.endsWith(ShendiKitInfo.JAR_SUFFIX)) {
					try {
						JarFile jarFile = new JarFile(jarPath);
						Enumeration<JarEntry> entry = jarFile.entries();
						
						// 要处理的类的所在文件夹集合,只有在此文件夹内的类才会被解析,解析出来的类全路径不带此路径
						String[] classOkPath = null;
						if (jar.value != null) classOkPath = jar.value.toString().trim().split(";");
						
						while (entry.hasMoreElements()) {
							String name = entry.nextElement().getName();
							// 只处理指定文件夹内的类文件
							if (name.endsWith(ShendiKitInfo.CLASS_SUFFIX)) {
								if (jar.value == null) disposeClass(name);
								else {
									for (String ok : classOkPath) {
										if (name.startsWith(ok)) {
											// anno_scan中的路径不能携带斜杠等
											disposeClass(name.substring(ok.length() + 1));
											break;
										}
									}
								}
							}
							
						}
						
						jarFile.close();
					} catch (IOException e) {
						e.printStackTrace();
						Log.printErr("扫描Jar包进行处理时出错: " + e.getMessage());
					}
				}
			}
		}
	}
	
	/**
	 * 获取当前项目的所有类集合.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return {@link HashMap}
	 */
	public static Map<String, Class<?>> getClasses() {
		// 没有初始化则先初始化
		if (!isInit) {
			// 处理项目使用到的需要处理的 Jar 包,
			String[] classPaths = System.getProperty("java.class.path").split(";");
			if (jars != null) {
				for (String classPath : classPaths) disposeJar(classPath);
			}
			
			// 扫描整个项目,为空则可能是项目已打包,上述代码已做对应操作.
			URL resource = ClassScan.class.getResource("/");
			if (resource != null) {
				ClassScan.classPath = resource.getPath().substring(1);
				
				// Web 项目先扫描 lib 目录下需要处理的 jar
				if (classPath.endsWith("WEB-INF/classes/")) {
					File file = new File(classPath.substring(0, classPath.length() - 8) + "lib");
					if (file.exists() && jars != null) {
						File[] files = file.listFiles();
						for (File f : files) disposeJar(f.getPath());
					}
				}
				
				scanClass(classPath);
			}
			
			// 模块化后的版本(1.9及以上)需要格外处理,当resource不为空则不处理
			// 这种操作经过检验,只有在未打包的高版本普通Java项目中会出现.
			// 备注: 第一步扫描jar,第二步获取源路径,如果当前项目打包getResource为空,1.9以上也会为空,导致前两步操作在1.9未打包项目扫描不到类,需要格外处理
			String version = System.getProperty("java.version");
			// 10以前都是1.x.x
			boolean jdkUpNine = version.startsWith("1.")
					? Integer.parseInt(version.substring(2, 3)) > 8 ? true : false
					: true;

			if (resource == null && jdkUpNine) {
				String projectPath = System.getProperty("user.dir");
				// 路径以分隔符结尾,不然获取到的会以分隔符开头.
				ClassScan.classPath = projectPath + File.separatorChar + "bin" + File.separatorChar;
				scanClass(classPath);
				
				// 处理Jar,扫描根目录除开 classPath 目录
				scanJarByJava9(projectPath);
			}
			
			Log.print("Scan " + scanNum + " file,has kit annotation class number is: " + CLASSES.size());
			
			// 递归完会有大量垃圾,建议JVM回收
			System.gc();
			isInit = true;
		}
		return CLASSES;
	}
	
	/**
	 * 获取带指定注解的类,仅限类注解.
	 * <br>
	 * getClasses(ConsoleAnno.class)
	 * <br>
	 * 创建时间 2024年1月11日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param clazz 注解
	 * @return 注解对应的类map，key为类全路径,value类
	 * @since 1.1.1
	 */
	public static <A extends Annotation> Map<String, Class<?>> getClasses(Class<A> clazz) {
		HashMap<String, Class<?>> result = new HashMap<>();
		getClasses().forEach((k,v) -> {
			A anno = v.getAnnotation(clazz);
			if (anno != null) {
				result.put(k, v);
			}
		});
		
		return result;
	}
	
	/**
	 * 重新扫描加载,用于热更.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	public static synchronized void reload() {
		CLASSES.clear();
		isInit = false;
		getClasses();
	}
	
	/**
	 * 重新加载某个单独的类.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param clazz 类全路径,例如 shendi.kit.Test
	 */
	public static synchronized void reload(String clazz) {
		if (CLASSES.containsKey(clazz)) {
			for (String classFilter : ShendiKitInfo.CLASS_FILTER)
				if (classPath.endsWith(classFilter)) return;
			
			try {
				Class<?> c = SKClassLoader.reloadClass(clazz);
				if (c == null) return;
				// 将类添加进集合
				Annotation[] annos = c.getAnnotations();
				if (annos.length > 0) {
					for (int i = 0;i < ANNOS.size(); i++) {
						for (int j = 0;j < annos.length;j++) {
							if (ANNOS.get(i) == annos[j].annotationType()) {
								CLASSES.put(clazz, c);
								return;
							}
						}
					}
				}
			} catch (ClassNotFoundException e) {
				Log.printAlarm("扫描到指定类文件,创建Class失败,找不到类: " + e.getMessage());
			}
		}
	}
	
}
