package shendi.kit.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import shendi.kit.annotation.PConfig;
import shendi.kit.annotation.scan.PConfigScan;
import shendi.kit.log.Log;
import shendi.kit.path.PathFactory;

/**
 * 配置文件工厂,用于获取指定的存在的配置文件.<br>
 * 如需使用此类,请在项目根目录(Web项目则为WebContent)下新建一个files文件夹,并在files文件夹中新建 main.properties 文件.<br>
 * main.properties用于存储其他配置文件的路径.<br>
 * 例: 普通Java项目的根目录的files目录下有 config.properties 配置文件,我们需要在 main.properties 中添加以下内容才能获取到config.properties文件
 * <b>config=/files/config.properties</b><br>
 * 通过 {@link #getConfig(String)} 来获取一个在main.properties中对应的另一个Properties的文件,默认处理编码UTF-8.
 * 如果需要指定处理编码,请使用 {@link #getConfig(String, String)}
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.1
 */
public class ConfigurationFactory {
	
	/** Properties 配置的对象 */
	private static final PropertiesConfiguration CONFIG = new PropertiesConfiguration();
	
	static { new PConfigScan(CONFIG).init(); }
	
	/**
	 * 获取对应的 Properties 配置文件,通过配置文件名,使用的编码为 UTF-8.
	 * @param name main.properties中对应的名,只允许英文数字(不能为中文,否则获取不到)
	 * @return 返回null则代表没有此配置文件
	 * @see #getConfig(String name,String encoding)
	 */
	public static Properties getConfig(String name) { return getConfig(name, "UTF-8"); }
	
	/**
	 * 获取 Properties 配置文件根据指定的编码
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name main.properties中配置的配置文件名,不能出现中文
	 * @param encoding 将内容通过指定编码处理
	 * @return 返回指定配置文件 null则代表没有此配置,或者 main.properties 不存在.
	 */
	public static Properties getConfig(String name, String encoding) {
		// 先判断获取的是不是注解配置 name开头为指定字符串则是注解配置,否则直接获取对应配置文件内容.
		String text = PConfig.NAME + name;
		Properties anno = CONFIG.getConfigs().get(text);
		if (anno != null) {
			return anno;
		} else {
			if (CONFIG.getMain() == null) { return null; }
			//配置文件读取的是ISO-8859-1 转码
			String configUrl = CONFIG.getMain().getProperty(name);
			if (configUrl != null) {
				try { configUrl = new String(configUrl.getBytes("ISO-8859-1"),encoding); }
				catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					Log.printErr("在配置工厂中获取配置文件失败!获取配置文件转码出错! configUrl=" + configUrl);
				}
				
				//获取此文件,用于判断文件是否存在/修改
				File file = CONFIG.getFiles().get(name);
				if (file == null) {
					file = new File(PathFactory.getPath(PathFactory.PROJECT, configUrl));
					CONFIG.getFiles().put(name, file);
				}
				if (!file.exists()) {
					Log.printErr("配置文件获取失败！文件不存在 : path=" + file.getPath());
					return null;
				}
				
				//判断Map里是否存取了此配置
				if (CONFIG.getConfigs().containsKey(name)) {
					//判断此配置文件是否改变
					if (CONFIG.getFileLastModified().get(name) < file.lastModified()) {
						try {
							CONFIG.getConfigs().get(name).clear();
							CONFIG.getConfigs().get(name).load(new FileInputStream(file));
							CONFIG.getFileLastModified().put(name, file.lastModified());
						} catch (IOException e) {
							e.printStackTrace();
							Log.printErr("重新加载配置文件失败：" + name + e.getMessage());
						}
					}
					return CONFIG.getConfigs().get(name);
				}
				
				Properties properties = new Properties();
				try {
					properties.load(new FileInputStream(file));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					Log.printErr("在配置工厂中获取配置文件失败!获取配置文件出错" + e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
					Log.printErr("在配置工厂中获取配置文件失败!获取配置文件出错" + e.getMessage());
				}
				//存入Map
				CONFIG.getFileLastModified().put(name, file.lastModified());
				CONFIG.getConfigs().put(name, properties);
				
				return properties;
			} else {
				Log.printErr("配置工厂获取不到指定配置文件,name=" + name);
				return null;
			}
		}
	}
	
	/**
	 * 对于 properties 配置文件,使用此方法可直接简易获取.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param config 配置文件名
	 * @param name 键名
	 * @return 指定配置文件内键的值
	 * @since 1.1
	 */
	public static String getProperty(String config, String name) { return getProperty(config, name, null); }
	
	/**
	 * 对于 properties 配置文件,可直接设置编码.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param config	配置文件名
	 * @param name		键名
	 * @param encode	属性值的编码,为null则使用默认编码
	 * @return 指定配置文件内键的值
	 * @since 1.1
	 */
	public static String getProperty(String config, String name, String encode) {
		String value = getConfig(config).getProperty(name);
		if (encode == null) return value;
		try {
			// Properties使用ISO-8859-1编码
			return new String(value.getBytes("ISO-8859-1"), encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log.printErr("此编码不被支持: " + encode);
			return value;
		}
	}
	
	/**
	 * 获取使用了 PConfig 注解的对应类,config 与 name 对应.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param config 配置文件
	 * @param name 配置文件内键的名称
	 * @return 对应的类,为null则无.
	 */
	public static Class<?> getPConfigAnnoClass(String config,String name) {
		return CONFIG.getClasses().get(config + name);
	}
	
}
