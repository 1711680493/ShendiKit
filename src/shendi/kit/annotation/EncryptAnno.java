package shendi.kit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import shendi.kit.encrypt.EncryptFactory;

/**
 * 加密类注解,使用此注解的类为一个加密类,并可以通过加密工厂获取<br>
 * 要使用此注解首先需要实现 {@link shendi.kit.encrypt.Encrypt} 接口<br>
 * 通过 {@link EncryptFactory#getEncrypt(String)} 来获取对应加密类,参数为当前注解的值 {@link #value()}<br>
 * <br>
 * <b>必须提供一个无参构造函数</b><br>
 * <br>
 * 以下例子可通过 EncryptFactory.getEncrypt("A") 来获取对象
 * <pre>
 * @Encrypt("A")
 * class A implements Encrypt {
 * 	public byte[] encrypt(byte[] data) {
 * 		return null;
 * 	}
 * 
 * 	public byte[] decode(byte[] data) throws NonsupportDecodeException {
 * 		return null;
 * 	}
 * 
 * }
 * </pre>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME) @Documented
public @interface EncryptAnno {
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 加密类的名称
	 */
	String value();
	
}
