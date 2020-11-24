package shendi.kit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 使用此注解在函数/字段上来代表一个命令<br>
 * <br>
 * <b>使用了此注解的方法必须无参并且返回一个字符串,字符串将被显示</b><br>
 * <b>函数/字段必须是public的</b><br>
 * <br>
 * <p>
 * 1.1版本新增group属性,用于指定命令所在组<br>
 * 如果设置了,则优先级高于ConsoleAnno所设置的group<br>
 * 在控制台启动时可以设置使用哪组的命令.
 * </p>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.1
 * @see ConsoleAnno
 */
@Retention(RetentionPolicy.RUNTIME) @Documented
public @interface CommandAnno {
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 命令名称
	 */
	String name();
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 命令描述
	 */
	String info();
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 当前命令所在组,如果命令没有设置此属性则使用类组或默认组
	 */
	String group() default ConsoleAnno.DEFAULT_GROUP;
	
}
