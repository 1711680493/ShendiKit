package shendi.kit.encrypt;

import java.util.HashMap;

/**
 * 加密工厂,提供各种不同加密方法.<br>
 * 使用 {@link #getEncrypt(String)} 来获取对应的加密方法,请查阅此方法.<br>
 * 通过实现 {@link Encrypt} 来自定义一个加密类,自定义的加密类通过 {@link #addEncrypt(String, Encrypt)} 来添加进工厂<br>
 * 具体操作请参见 {@link Encrypt}
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @see Encrypt
 */
public final class EncryptFactory {
	/** 存储加密类的对象,享元模式 */
	private static final HashMap<String,Encrypt> ENCRYPTS = new HashMap<>();

	/** 加密形式为加一算法 */
	public static final String ADD_ONE = "Add_One";
	
	/** 加密形式为密码形式 */
	public static final String PWD = "pwd";
	
	/** 加密形式为求和取余算法 */
	public static final String SUM_REMAINDER = "Sum_Remainder";
	
	
	/** 单例模式 */
	private EncryptFactory() {}
	
	/** 初始化加密形式 */
	static {
		ENCRYPTS.put(ADD_ONE, new AddOneEncrypt());
		ENCRYPTS.put(PWD, new PasswordEncrypt());
		ENCRYPTS.put(SUM_REMAINDER, new SumRemainderEncrypt());
	}
	
	/**
	 * 获取对应加密功能的加密类.
	 * <table border='1'>
	 * 	<tr>
	 * 		<th>名称</th>
	 * 		<th>描述</th>
	 * 		<th>是否可逆</th>
	 * 		<th>使用方式</th>
	 * 	<tr>
	 * 	<tr>
	 * 		<td>Add_One</td>
	 * 		<td>给数据每个字节+1</td>
	 * 		<td>可逆</td>
	 * 		<td>通过{@link #getEncrypt(EncryptFactory.ADD_ONE)}</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>pwd</td>
	 * 		<td>使用密码加密数据</td>
	 * 		<td>可逆</td>
	 * 		<td>通过{@link #getPwdEncrypt(String)} 来设置密码并获取,设置密码后也可以通过{@link #getEncrypt(EncryptFactory.PWD)}来获取.<br>未设置密码也可以通过此方法获取,使用的是默认的密码</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>Sum_Remainder</td>
	 * 		<td>相邻数据相加并取余</td>
	 * 		<td>不可逆</td>
	 * 		<td>通过{@link #getEncrypt(EncryptFactory.SUM_REMAINDER)}</td>
	 * 	</tr>
	 * </table>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 加密方法的名称
	 * @return {@link Encrypt},如果没有此加密类则返回null.
	 */
	public static Encrypt getEncrypt(String name) { return ENCRYPTS.get(name); }
	
	/**
	 * 获取通过指定密码加密的的加密方法.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param pwd 密码
	 * @return {@link PasswordEncrypt}
	 */
	public static Encrypt getPwdEncrypt(String pwd) {
		PasswordEncrypt pwdEncrypt = (PasswordEncrypt)ENCRYPTS.get(PWD);
		pwdEncrypt.setPassword(pwd);
		return pwdEncrypt;
	}
	
	/**
	 * 添加一个加密方法到加密工厂.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 获取时需要填入的名
	 * @param e 加密类
	 */
	public static void addEncrypt(String name, Encrypt e) { ENCRYPTS.put(name, e); }
	
}
