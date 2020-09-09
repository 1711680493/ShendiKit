package shendi.kit.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import shendi.kit.ShendiKitInfo;
import shendi.kit.log.Log;

/**
 * 类加载器,简化使用.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public final class SKClassLoader extends ClassLoader {
	private SKClassLoader() {}
	
	/** 唯一的对象 */
	private static SKClassLoader sk = new SKClassLoader();
	
	/**
	 * 重新加载指定类,用于热更.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param clazz 类的全路径名
	 * @return 重新加载的类
	 * @throws ClassNotFoundException 如果类没有找到则抛出
	 */
	public static Class<?> reloadClass(String clazz) throws ClassNotFoundException {
		if (clazz == null) return null;
		else {
			sk = new SKClassLoader();
			clazz = File.separator.concat(clazz.replace('.', File.separatorChar).concat(ShendiKitInfo.CLASS_SUFFIX));
			byte[] classData = null;
			
			try (InputStream input = sk.getResourceAsStream(clazz)) {
				classData = new byte[input.available()];
				input.read(classData, 0, classData.length);
			} catch (IOException e) {
				Log.printErr("重新加载指定类获取出错: " + clazz + "---" + e.getMessage());
				return null;
			}
			
			return sk.defineClass(null, classData, 0, classData.length);
		}
	}
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return {@link SKClassLoader} 的实例
	 */
	public static SKClassLoader getSk() { return sk; }
	
}
