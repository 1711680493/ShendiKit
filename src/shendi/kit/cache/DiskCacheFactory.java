package shendi.kit.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 磁盘缓存工厂,管理磁盘缓存对象.<br>
 * 在此工厂创建的缓存对象会被监视,当两秒没有任何读写操作,则会关闭读写流,需要时再重新打开以节省开销.
 * <br>
 * 创建时间 2022年11月26日
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @since SK 1.1
 * @version 1.0
 */
public class DiskCacheFactory {

	private static final List<DiskCache> DCS = new ArrayList<>();
	
	static {
		Thread t = new Thread(() -> {
			while (true) {
				DCS.forEach((cache) -> {
					if (System.currentTimeMillis() - cache.getUpOptionTime() > 2000) {
						cache.close();
					}
				});
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.setName("DiskCacheFactory Thread");
		t.start();
	}
	
	/**
	 * 创建一个磁盘缓存.
	 * <br>
	 * 创建时间 2022年11月26日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 缓存名称
	 * @return 磁盘缓存
	 * @throws IOException 
	 */
	public static DiskCache create(String name) throws IOException {
		DiskCache cache = new DiskCache(name);
		DCS.add(cache);
		return cache;
	}
	
	/**
	 * 将磁盘缓存添加到工厂.
	 * <br>
	 * 创建时间 2022年11月26日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param cache 缓存
	 */
	public static void add(DiskCache cache) {
		DCS.add(cache);
	}
	
}
