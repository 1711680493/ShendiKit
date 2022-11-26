package shendi.kit.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import shendi.kit.log.Log;
import shendi.kit.path.PathFactory;
import shendi.kit.util.ByteUtil;

/**
 * 磁盘缓存,缓存将被写入磁盘.
 * <br>
 * 创建时间 2022年11月26日
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 */
public class DiskCache implements Cache {

	private String name;
	private final File file;
	
	/** 上一次操作时间, 用于磁盘缓存工厂关闭流使用 */
	private long upOptionTime;
	
	/** 文件读取流 */
	private FileInputStream fi;
	/** 文件写入流 */
	private FileOutputStream fo;
	
	/** 文件数据 */
	protected byte[] data;
	
	/** 文件是否接着写入 */
	private boolean isAppend = true;
	
	/**
	 * 创建一个磁盘缓存,其中name为文件在磁盘存放的位置.
	 * @param name 文件存放的位置,相对项目根路径的cache文件夹
	 * @throws IOException 创建文件出错时抛出
	 */
	public DiskCache(String name)  {
		this.name = name;
		
		String path = PathFactory.getPath(PathFactory.PROJECT, "/cache/".concat(name));
		file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			Log.printExcept("文件创建失败,error=%s, path=%s", e.getMessage(), path);
		}
		
		init();
	}
	
	@Override
	public void init() {
		try {
			getFi();
			data = new byte[fi.available()];
			fi.read(data, 0, data.length);
			
			initAfter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 在 init 执行完后执行,用于子类实现.
	 * <br>
	 * 创建时间 2022年11月26日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	public void initAfter() {}

	@Override
	public void write(byte[] data) {
		try {
			writeBefore(data);
			
			synchronized (file) {
				getFo(true);
				fo.write(data);
				fo.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 在文件保存之前执行,继承此类一般需要重写此函数.
	 * <br>
	 * 创建时间 2022年11月26日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param data 要写入的数据
	 */
	protected void writeBefore(byte[] data) {
		this.data = ByteUtil.concat(this.data, data);
	}

	@Override
	public void rewrite(byte[] data) {
		try {
			synchronized (file) {
				getFo(false);
				fo.write(data);
				fo.flush();
				
				this.data = data;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] readAll() {
		return data;
	}

	@Override
	public void clear() {
		try {
			synchronized (file) {
				getFo(false);
				fo.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取读取流.
	 * <br>
	 * 创建时间 2022年11月26日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 读取流
	 * @throws FileNotFoundException
	 */
	protected FileInputStream getFi() throws FileNotFoundException {
		upOptionTime = System.currentTimeMillis();
		if (fi == null) fi = new FileInputStream(file);
		return fi;
	}
	
	/**
	 * 获取写入流
	 * <br>
	 * 创建时间 2022年11月26日
	 * @param isAppend 输出流是否继续写入
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 写入流
	 * @throws IOException 
	 */
	protected FileOutputStream getFo(boolean isAppend) throws IOException {
		upOptionTime = System.currentTimeMillis();
		if (fo == null) fo = new FileOutputStream(file, isAppend);
		else if (this.isAppend != isAppend) {
			fo.close();
			fo = new FileOutputStream(file, isAppend);
			
			this.isAppend = isAppend;
		}
		return fo;
	}
	
	/**
	 * 将流关闭,继续使用时自动重新打开
	 * <br>
	 * 创建时间 2022年11月26日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	protected void close() {
		if (fi != null)
			try {
				fi.close();
				fi = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		synchronized (file) {
			if (fo != null) {
				try {
					fo.close();
					fo = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获取此缓存的名称
	 * <br>
	 * 创建时间 2022年11月26日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 创建时传递的名称
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 获取上一次的操作时间.
	 * <br>
	 * 创建时间 2022年11月26日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 操作时间
	 */
	protected long getUpOptionTime() {
		return upOptionTime;
	}
	
	/**
	 * 文件内容.
	 * <br>
	 * 创建时间 2022年11月26日
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 文件内容
	 */
	public byte[] getData() {
		return data;
	}
	
}

