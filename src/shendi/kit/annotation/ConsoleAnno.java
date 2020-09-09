package shendi.kit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用此注解在类上来代表此类使用了控制台<br>
 * 使用了此注解的类会被解析,将使用了 {@link CommandAnno} 的方法解析到控制台,用于获取对应信息.<br>
 * <br>
 * <b>必须提供一个无参构造函数</b><br>
 * <br>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @see CommandAnno
 */
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME) @Documented
public @interface ConsoleAnno {}
