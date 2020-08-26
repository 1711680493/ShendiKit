package shendi.kit.encrypt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import shendi.kit.log.Log;

/**
 * 加密算法工具类,方便的对文件进行加密/解密操作.<br>
 * 如果需要加密数据,而非文件,请参阅 {@link EncryptFactory}<br>
 * 使用 {@link #encryptFiles(String, Encrypt)} 可以将指定路径的文件/文件夹加密(包括子文件夹)<br>
 * 有以下重载
 * <table border='1'>
 * 	<tr>
 * 		<td>{@link #encryptFiles(File, Encrypt)}</td>
 * 		<td>加密文件使用File和加密类型</td>
 * 	<tr>
 * 	<tr>
 * 		<td>{@link #encryptFiles(String, String, Encrypt)}</td>
 * 		<td>加密文件使用指定文件路径并指定加密文件的保存路径和加密类型</td>
 * 	</tr>
 * 	<tr>
 * 		<td>{@link #encryptFiles(File, String, Encrypt)}</td>
 * 		<td>加密文件使用指定File并指定加密文件的保存路径和加密类型</td>
 * 	</tr>
 * </table>
 * <br>
 * 加密的路径为文件会以 Shendi_Encrypt 开头.<br>
 * 如果是文件夹则文件夹名为 Shendi_Encrypt<br>
 * <br>
 * 使用 {@link #decodeFiles(String, String, Encrypt)} 来解密文件,重载和功能与加密对应.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @see EncryptFactory
 */
public final class EncryptUtils {
	
	/**
	 * 加密数据,根据指定的加密方法.<br>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 加密方法的名称,通常通过 {@link EncryptFactory} 来获取
	 * @param data 要加密的数据
	 * @return 加密后的字节数组
	 */
	public static byte[] encrypt(String name, Object data) {
		return EncryptFactory.getEncrypt(name).encrypt(data.toString().getBytes());
	}
	
	/**
	 * 加密数据,根据指定的加密方法,加密后数据返回为字符串形式,RS(Return String)
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 加密方法的名称,通常通过 {@link EncryptFactory} 来获取
	 * @param data 要加密的数据
	 * @return 加密后数据的字符串表示形式
	 */
	public static String encryptRS(String name, Object data) {
		return new String(EncryptFactory.getEncrypt(name).encrypt(data.toString().getBytes()));
	}
	
	/**
	 * 解密数据,根据指定的解密方法.<br>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 解密方法的名称,通常通过 {@link EncryptFactory} 来获取
	 * @param data 要解密的数据
	 * @return 解密后的字节数组
	 */
	public static byte[] decode(String name, Object data) {
		return EncryptFactory.getEncrypt(name).decode(data.toString().getBytes());
	}
	
	/**
	 * 解密数据,根据指定的解密方法,解密后数据返回为字符串形式,RS(Return String)
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 解密方法的名称,通常通过 {@link EncryptFactory} 来获取
	 * @param data 要解密的数据
	 * @return 解密后数据的字符串表示形式
	 */
	public static String decodeRS(String name, Object data) {
		return new String(EncryptFactory.getEncrypt(name).decode(data.toString().getBytes()));
	}
	
	/**
	 * 加密指定路径的文件/文件夹<br>
	 * 如果是文件夹,则会将此文件夹下所有项加密(包括子文件夹的子文件夹).
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param path 要加密的文件/文件夹的路径
	 * @param encrypt 使用的加密类型.
	 */
	public static void encryptFiles(String path,Encrypt encrypt) { encryptFiles(new File(path), encrypt); }
	
	/**
	 * 加密指定文件/文件夹.<br>
	 * 如果是文件夹,会将此文件夹下的所有项加密.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param file 指定文件/文件夹
	 * @param encrypt 使用的加密类型
	 */
	public static void encryptFiles(File file,Encrypt encrypt) {
		if (!file.exists()) {
			Log.printErr("文件不存在！");
			return;
		}
		if (file.isDirectory()) {
			encryptFiles(file, file.getParent() + File.separatorChar + "Shendi_Encrypt", encrypt);
		} else if (file.isFile()) {
			encryptFiles(file, file.getParent() + File.separatorChar + "Shendi_Encrypt_"+file.getName(), encrypt);
		}
	}
	
	/**
	 * 加密指定文件/文件夹(文件夹下所有项),输出到指定路径.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param path 要加密的文件/文件夹路径
	 * @param savePath 加密文件的保存路径
	 * @param encrypt 使用的加密类型
	 */
	public static void encryptFiles(String path, String savePath, Encrypt encrypt) {
		encryptFiles(new File(path), savePath, encrypt);
	}
	
	/**
	 * 加密指定文件/文件夹(文件夹下所有项),输出到指定路径.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param file 要加密的文件/文件夹
	 * @param savePath 加密文件的保存路径
	 * @param encrypt 使用的加密类型
	 */
	public static void encryptFiles(File file, String savePath, Encrypt encrypt) {
		if (file.exists()) {
			// 是文件夹则递归,并在加密文件夹创建对应文件夹.
			// 不是文件夹则进行加密放到对应加密文件夹内
			if (file.isDirectory()) {
				
				File save = new File(savePath);
				save.mkdir();
				
				File[] files = file.listFiles();
				for (File f : files)
					new Thread(() -> encryptFiles(f ,savePath + File.separatorChar + f.getName(), encrypt)).start();
			} else {
				try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
						BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(savePath))) {
					byte[] data = new byte[input.available()];
					input.read(data);
					output.write(encrypt.encrypt(data));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					Log.printErr(e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
					Log.printErr(e.getMessage());
				}
			}
		} else Log.printErr("加密失败,文件不存在: " + file.getPath());
	}
	
	/**
	 * 解密被加密的文件,与加密方法的使用一致.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param path 要解密的文件路径
	 * @param encrypt 使用的解密类型
	 */
	public static void decodeFiles(String path, Encrypt encrypt) { decodeFiles(new File(path), encrypt); }
	
	/**
	 * 解密被加密的文件,与加密方法的使用一致.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param file 要解密的文件
	 * @param encrypt 使用的解密类型
	 */
	public static void decodeFiles(File file, Encrypt encrypt) {
		if (!file.exists()) {
			Log.printErr("文件不存在！");
			return;
		}
		if (file.isDirectory()) {
			decodeFiles(file, file.getParent() + File.separatorChar + "Shendi_Decode", encrypt);
		} else if (file.isFile()) {
			decodeFiles(file, file.getParent() + File.separatorChar + "Shendi_Decode_" + file.getName(), encrypt);
		}
	}
	
	/**
	 * 解密被加密的文件,与加密方法的使用一致.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param path 要解密的文件路径
	 * @param savePath 解密文件的保存路径
	 * @param encrypt 使用的解密类型
	 */
	public static void decodeFiles(String path, String savePath, Encrypt encrypt) {
		decodeFiles(new File(path), savePath, encrypt);
	}
	
	/**
	 * 解密被加密的文件,与加密方法的使用一致.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param file 要解密的文件
	 * @param savePath 解密文件的保存路径
	 * @param encrypt 使用的解密类型
	 */
	public static void decodeFiles(File file, String savePath, Encrypt encrypt) {
		if (file.exists()) {
			// 是文件夹则递归,并在解密文件夹创建对应文件夹.
			// 不是文件夹则进行加密放到对应加密文件夹内
			if (file.isDirectory()) {
				
				File save = new File(savePath);
				save.mkdir();
				
				File[] files = file.listFiles();
				for (File f : files)
					new Thread(() -> encryptFiles(f ,savePath + File.separatorChar + f.getName(), encrypt)).start();
			} else {
				try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
						BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(savePath))) {
					byte[] data = new byte[input.available()];
					input.read(data);
					output.write(encrypt.decode(data));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					Log.printErr(e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
					Log.printErr(e.getMessage());
				}
			}
		} else Log.printErr("加密失败,文件不存在: " + file.getPath());
	}

}
