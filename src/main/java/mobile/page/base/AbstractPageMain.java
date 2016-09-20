package mobile.page.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.openqa.selenium.WebElement;

import up.light.utils.ArgumentUtil;

/**
 * 所有PageMain的父类，提供入口点对象自动初始化，，进入指定界面，重新进入上次界面等功能
 */
public class AbstractPageMain extends AbstractPage {
	private static Method method;
	private IEntryPoint lastEntryPoint;

	public AbstractPageMain() {
		Class<?> clazz = this.getClass();
		if (clazz.getSimpleName().indexOf("EnhancerByCGLIB") != -1) {
			// 如果是CGLIB代理类，则获取原始类
			clazz = clazz.getSuperclass();
		}

		EntranceClass classAnno = clazz.getAnnotation(EntranceClass.class);
		if (classAnno == null) {
			return;
		}

		Class<?> entrnceClazz = classAnno.value();
		try {
			// 触发类初始化
			Class.forName(entrnceClazz.getName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		Field[] fs = clazz.getDeclaredFields();
		Method m = getMethod();
		Entrance fieldAnno = null;
		EntrancesBase eb = null;

		// 遍历所有字段
		for (Field f : fs) {
			if (!WebElement.class.isAssignableFrom(f.getType())) {
				continue;
			}

			fieldAnno = f.getAnnotation(Entrance.class);
			if (fieldAnno != null) {
				String name = fieldAnno.value().toUpperCase();
				// 根据注解中的值设置对应的element
				try {
					eb = (EntrancesBase) m.invoke(null, name);
					f.setAccessible(true);
					eb.setElement((WebElement) f.get(this));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 进入到指定的界面
	 * @param point 界面入口点
	 */
	public void doEnter(IEntryPoint point) {
		ArgumentUtil.notNull(point, "IEntryPoint must not be null");
		point.doEnter();
		lastEntryPoint = point;
	}

	/**
	 * 重新进入上次界面（一般用于场景恢复）
	 */
	public void doLastEnter() {
		doEnter(lastEntryPoint);
	}

	/**
	 * 获取{@link EntrancesBase#fromString(String)}并缓存
	 * @return
	 */
	private Method getMethod() {
		if (method == null) {
			Class<?> clazz = EntrancesBase.class;
			try {
				method = clazz.getMethod("fromString", String.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return method;
	}
}
