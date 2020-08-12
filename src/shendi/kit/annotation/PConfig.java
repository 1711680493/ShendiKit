package shendi.kit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import shendi.kit.config.ConfigurationFactory;

/**
 * Properties Configuration.<br>
 * 使用此注解放到类上可以方便的创建策略类等.<br>
 * 使用 {@link #config()} 代表配置文件名<br>
 * 使用 {@link #name()} 代表配置文件内对应键<br>
 * 
 * 通过 {@link ConfigurationFactory#getConfig(String)}
 * 获取对应 Properties,然后通过name获取到对应的全路径类名<br>
 * 
 * 也可以直接通过 {@link ConfigurationFactory#getPConfigAnnoClass(String, String)} 直接获取对应类.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME) @Documented
public @interface PConfig {
	/** 注解配置的开头. */
	String NAME = "PC";
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 使用的对应配置文件
	 */
	String config();
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 配置文件内键名称
	 */
	String name();
	
}
