package shendi.kit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 使用此注解在函数/字段上来代表一个命令<br>
 * <br>
 * <b>使用了此注解的方法必须无参并且返回一个字符串,字符串将被显示</b><br>
 * <br>
 * <br>
 * <b>函数/字段必须是public的</b><br>
 * <br>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
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
	
}
