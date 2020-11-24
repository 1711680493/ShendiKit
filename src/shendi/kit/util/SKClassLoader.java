package shendi.kit.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import shendi.kit.ShendiKitInfo;
import shendi.kit.log.Log;
import shendi.kit.path.PathFactory;

/**
 * 类加载器,简化使用.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.1
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
			clazz = "/".concat(clazz.replace('.', '/').concat(ShendiKitInfo.CLASS_SUFFIX));
			byte[] classData = null;
			
			// 在高版本打包后使用class.getResourceAsStream, 未打包使用路径方式获取.
			InputStream input = SKClassLoader.class.getResourceAsStream(clazz);
			try {
				if (input == null) input = new FileInputStream(PathFactory.get(PathFactory.RESOURCE).getPath(clazz));
				
				classData = new byte[input.available()];
				input.read(classData, 0, classData.length);
			} catch (IOException e) {
				Log.printErr("重新加载指定类获取出错: " + clazz + "---" + e.getMessage());
				return null;
			} finally {
				try {
					if (input != null) input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sk.defineClass(null, classData, 0, classData.length);
		}
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> c = findLoadedClass(name);
		if (c == null) {
			sk = new SKClassLoader();
			name = "/".concat(name.replace('.', '/').concat(ShendiKitInfo.CLASS_SUFFIX));
			byte[] classData = null;
			// 在高版本打包后使用class.getResourceAsStream, 未打包使用路径方式获取.
			InputStream input = SKClassLoader.class.getResourceAsStream(name);
			try {
				if (input == null) input = new FileInputStream(PathFactory.get(PathFactory.RESOURCE).getPath(name));
				
				classData = new byte[input.available()];
				input.read(classData, 0, classData.length);
			} catch (IOException e) {
				Log.printErr("重新加载指定类获取出错: " + name + "---" + e.getMessage());
				return null;
			} finally {
				try {
					if (input != null) input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sk.defineClass(null, classData, 0, classData.length);
		}
		return c;
	}
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return {@link SKClassLoader} 的实例
	 */
	public static SKClassLoader getSk() { return sk; }
	
}
