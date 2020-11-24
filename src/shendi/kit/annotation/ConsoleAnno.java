package shendi.kit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用此注解在类上来代表此类为控制台命令类<br>
 * 使用了此注解的类会被解析,将使用了 {@link CommandAnno} 的方法解析到控制台,用于获取对应信息.<br>
 * <br>
 * <b>必须提供一个无参构造函数</b><br>
 * <br>
 * 在 1.1 版本中新增可设置此类默认组通过 @ConsoleAnno("group name").
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.1
 * @see CommandAnno
 */
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME) @Documented
public @interface ConsoleAnno {
	/** 默认命令组 */
	String DEFAULT_GROUP = "default";
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 当前控制台所在组
	 */
	String value() default DEFAULT_GROUP;
	
}
