package mobile.preprocess;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <ul>
 * <li>用于标注需要预处理的测试类，在Test方法执行前会自动按照value中设置的Preprocessor类进行预处理操作</li>
 * <li>预处理执行成功后才会执行后续测试，若执行失败则按照Preprocessor中的FailStrategy来决定后续操作</li>
 * <li>所有预处理操作只执行一次</li>
 * </ul>
 * 使用场景举例：测试方法需要登录
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface NeedPreprocess {
	Class<? extends Preprocessor>[] value() default {};
}
