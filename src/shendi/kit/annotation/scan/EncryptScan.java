package shendi.kit.annotation.scan;

import java.lang.reflect.InvocationTargetException;

import shendi.kit.annotation.EncryptAnno;
import shendi.kit.encrypt.Encrypt;
import shendi.kit.encrypt.EncryptFactory;
import shendi.kit.log.Log;

/**
 * Encrypt 注解扫描器.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class EncryptScan implements AnnotationScan {
	
	@Override
	public void init() {
		ClassScan.getClasses().forEach((k,v) -> {
			try {
				EncryptAnno anno = v.getAnnotation(EncryptAnno.class);
				String name = anno.value();
				
				// 如果这个配置已经存在则需要提醒,直接替换
				shendi.kit.encrypt.Encrypt e = EncryptFactory.getEncrypt(name);
				if (e != null) {
					StringBuilder b = new StringBuilder("加密注解名重复,将覆盖之前的类: ");
					b.append(name);
					b.append("\n已存在的类为: "); b.append(e.getClass().getName());
					b.append("\n当前类为: "); b.append(k);
					Log.printAlarm(b);
				} else {
					try {
						EncryptFactory.addEncrypt(name, (Encrypt) v.getDeclaredConstructor().newInstance());
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1) {
						Log.printErr("加密类注解的对象创建失败,请检查有无无参构造,类为:" + k + "\n错误信息: " + e1.getMessage());
						e1.printStackTrace();
					}
				}
			} catch (NullPointerException npe) {}
		});
	}
	
}
