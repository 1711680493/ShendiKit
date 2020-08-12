package shendi.kit.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import shendi.kit.log.Log;
import shendi.kit.path.ProjectPath;

/**
 * Properties配置类,用于获取对应Properties文件.<br>
 * 需要使用此类请在资源目录下创建 files/main.properties文件.<br>
 * main.properties 用于存取其他配置文件的位置,例如根目录下有配置文件a.properties,并且我们想取名为a,则内容为 a=/a.properties
 * @author <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>Shendi</a>
 * @version 1.0
 */
public final class PropertiesConfiguration {
	/** 用于存取其他Properties配置文件的池子. */
	private HashMap<String,Properties> CONFIGS = new HashMap<>();
	
	/** 配置文件文件最后修改时间(用于判断文件是否被修改) */
	private final HashMap<String,Long> FILE_LAST_MODIFIED = new HashMap<>();
	
	/** 所有配置文件 */
	private final HashMap<String,File> FILES = new HashMap<>();
	
	/** 带有注解的配置类.键为配置文件名+配置文件内键的名称. */
	private final HashMap<String,Class<?>> CLASSES = new HashMap<>();
	
	/** 主配置文件. */
	private final File file;
	
	/** 主配置文件的Properties类. */
	private final Properties MAIN = new Properties();
	
	/** 主配置文件的最后修改时间. */
	private long fileLastModified;
	
	public PropertiesConfiguration() {
		//获取项目根路径的文件
		file = new File(new ProjectPath().getPath("/files/main.properties"));
		if (!file.exists()) {
			return;
		}
		
		//获取此文件最后修改时间
		fileLastModified = file.lastModified();
		
		try {
			MAIN.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.printErr(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Log.printErr(e.getMessage());
		}
	}
	
	/**
	 * 获取其他配置文件的池子.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 存有配置文件的池子
	 */
	public HashMap<String, Properties> getConfigs() { return CONFIGS; }
	
	/**
	 * 获取主配置.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 主要的配置文件
	 */
	public Properties getMain() {
		
		// 配置文件为空则先获取
		if (MAIN == null) {
			if (file.exists()) {
				fileLastModified = file.lastModified();
				try {
					MAIN.load(new FileInputStream(file));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					Log.printErr(e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
					Log.printErr(e.getMessage());
				}
			} else {
				Log.printErr("配置文件初始化失败！Main.properties不存在 : path=" + file.getPath());
				return null;
			}
		}
		
		//判断配置文件有无修改
		if (fileLastModified < file.lastModified()) {
			//重新加载
			try {
				MAIN.clear();
				MAIN.load(new FileInputStream(file));
				fileLastModified = file.lastModified();
			} catch (IOException e) {
				e.printStackTrace();
				Log.printErr("重新加载配置文件失败:main");
			}
			Log.print("配置文件重新加载了:main");
		}
		
		return MAIN;
	}
	
	/**
	 * 获取所有文件的最后修改时间.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 配置文件的修改时间
	 */
	public HashMap<String, Long> getFileLastModified() { return FILE_LAST_MODIFIED; }
	
	/**
	 * 获取当前的所有配置文件.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 配置文件集合
	 */
	public HashMap<String, File> getFiles() { return FILES; }
	
	/**
	 * 获取带有配置注解的类.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 对应类.
	 */
	public HashMap<String, Class<?>> getClasses() { return CLASSES; }
	
}
