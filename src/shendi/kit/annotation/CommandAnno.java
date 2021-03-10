package shendi.kit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 使用此注解在函数/字段上来代表一个命令<br>
 * <b>命令必须在控制台内(类须有 {@link ConsoleAnno} 注解</b>
 * <br>
 * 当一个命令为字段时,须遵循以下规则<br>
 * <ul>
 * 	<li>权限修饰符必须为 public</li>
 * </ul>
 * 直接使用此命令则返回此字段的 toString() 形式<br>
 * 如果需要设置此字段的值,则输入命令时需要使用 /set 参数<br>
 * 例如 name /set hello<br>
 * <br>
 * 当一个命令为函数时,需遵照以下规则
 * <ul>
 * 	<li>权限修饰符必须为 public</li>
 * 	<li>返回值必须为String(此返回值将会被显示在控制台)</li>
 * 	<li>参数必须为HashMap<String, String>(命令参数,参数名=参数值),map.size() == 0则代表命令无参数</li>
 * </ul>
 * 每一个函数命令都应有对应的使用说明,通过命令参数 /?<br>
 * 函数的每一个参数都将与命令参数一一对应<br>
 * 例如 public String test(String a, String b);<br>
 * 对应的命令参数为 /a /b<br>
 * 如果要给参数设置值,只需要在命令参数后填写值即可
 * 对于此详细操作,可以参考说明文档 (<a href='https://github.com/1711680493/ShendiKit/blob/master/README.md'>README.md</a>)
 * <br>
 * <p>
 * <b>1.1版本新增group属性,用于指定命令所在组</b><br>
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
