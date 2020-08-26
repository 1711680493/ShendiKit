package shendi.kit.encrypt;

import java.util.HashMap;

import shendi.kit.annotation.scan.EncryptScan;

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
	
	/** 加载注解 */
	static { new EncryptScan().init(); }

	/** 加密形式为加一算法 */
	public static final String ADD_ONE = "Add_One";
	
	/** 加密形式为密码形式 */
	public static final String PWD = "Pwd";
	
	/** 加密形式为求和取余算法 */
	public static final String SUM_REMAINDER = "Sum_Remainder";
	
	/** 加密形式为双密码加密算法 */
	public static final String TWO_PWD = "Two_Pwd";
	
	/** 单例模式 */
	private EncryptFactory() {}
	
	/** 初始化加密形式 */
	static {
		ENCRYPTS.put(ADD_ONE, new AddOneEncrypt());
		ENCRYPTS.put(PWD, new PasswordEncrypt());
		ENCRYPTS.put(SUM_REMAINDER, new SumRemainderEncrypt());
		ENCRYPTS.put(TWO_PWD, new TwoPasswordEncrypt());
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
	 * 		<td>通过{@link #getPwdEncrypt(Object)} 来设置密码并获取,设置密码后也可以通过{@link #getEncrypt(EncryptFactory.PWD)}来获取.<br>未设置密码也可以通过此方法获取,使用的是默认的密码</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>two pwd</td>
	 * 		<td>使用两个密码加密数据,较于单密码,更安全,对于比较大的数据处理效率更高.</td>
	 * 		<td>可逆</td>
	 * 		<td>通过{@link #getTwoPwdEncrypt(Object, Object)} 来设置密码并获取,设置密码后也可以通过{@link #getEncrypt(EncryptFactory.TWO_PWD)}来获取.<br>未设置密码也可以通过此方法获取,使用的是默认的密码</td>
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
	 * 获取通过指定密码加密的的加密算法.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param pwd 密码
	 * @return {@link PasswordEncrypt}
	 */
	public static Encrypt getPwdEncrypt(Object pwd) {
		PasswordEncrypt pwdEncrypt = (PasswordEncrypt)ENCRYPTS.get(PWD);
		pwdEncrypt.setPassword(pwd);
		return pwdEncrypt;
	}
	
	/**
	 * 获取通过指定双密码加密的的加密算法.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param pwd1 密码1
	 * @param pwd1 密码2
	 * @return {@link TwoPasswordEncrypt}
	 */
	public static Encrypt getTwoPwdEncrypt(Object pwd1, Object pwd2) {
		TwoPasswordEncrypt pwdEncrypt = (TwoPasswordEncrypt)ENCRYPTS.get(TWO_PWD);
		pwdEncrypt.setPassword(pwd1, pwd2);
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
