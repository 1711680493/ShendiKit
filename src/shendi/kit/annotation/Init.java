package shendi.kit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解表示初始化.<br>
 * 如果需要给SK工具的属性初始化(例如缓存工具),则需要在类和属性上加上此注解<br>
 * <br>
 * 例如
 * <pre>
 * 
 * </pre>
 * <br>
 * 创建时间 2022年11月26日
 * @author Shendi
 * @since 1.1
 * @version 1.0
 */
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME) @Documented
public @interface Init {
	
}
