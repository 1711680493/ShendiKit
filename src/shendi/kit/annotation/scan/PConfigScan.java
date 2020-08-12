package shendi.kit.annotation.scan;

import java.util.Properties;

import shendi.kit.annotation.PConfig;
import shendi.kit.config.PropertiesConfiguration;
import shendi.kit.log.Log;

/**
 * Properties 配置文件扫描器.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class PConfigScan implements AnnotationScan {
	
	/** Properties 主配置文件. */
	private PropertiesConfiguration pConfig;
	
	/**
	 * 通过主配置文件来创建一个配置文件扫描器.
	 * @param pConfig 主配置文件类.
	 */
	public PConfigScan(PropertiesConfiguration pConfig) { this.pConfig = pConfig; }

	@Override
	public void init() {
		ClassScan.getClasses().forEach((k,v) -> {
			// 注解存在则添加进 Properties 和 类集合里
			try {
				PConfig anno = v.getAnnotation(PConfig.class);
				String config = anno.config();
				Properties p = pConfig.getConfigs().get(PConfig.NAME + config);
				if (p == null) {
					p = new Properties();
					pConfig.getConfigs().put(PConfig.NAME + config, p);
				}
				
				// 如果这个配置已经存在则需要提醒,直接替换
				String name = anno.name();
				String upClass = p.getProperty(name);
				if (upClass != null) {
					StringBuilder b = new StringBuilder("注解name重复,将覆盖之前的类: ");
					b.append(name);
					b.append("\n已存在的类为: "); b.append(upClass);
					b.append("\n当前类为: "); b.append(k);
					Log.printAlarm(b);
				}
				p.setProperty(name, k);
				
				pConfig.getClasses().put(config + name, v);
			} catch (NullPointerException npe) {}
		});
	}
	
}
