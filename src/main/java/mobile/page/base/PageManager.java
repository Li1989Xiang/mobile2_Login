package mobile.page.base;

import java.util.HashMap;
import java.util.Map;

import mobile.page.proxy.PageProxy;
import mobile.page.proxy.ProxyFactory;
import up.light.exceptions.LightReflectionException;
import up.light.utils.ArgumentUtil;
import up.light.utils.ClassUtil;

/**
 * <p>Page实例统一管理容器</p>
 * <ul>
 * <li>每个Page都为单例模式</li>
 * <li>对于非{@link IModule}类，会返回动态代理，以便进行界面跟踪</li>
 * <li>对于加了{@link SetMain}的类，会自动调用{@linkplain AbstractPage#setMain(AbstractPageMain)
 * setMain(AbstractPageMain)}方法进行初始化</li>
 * </ul>
 */
public abstract class PageManager {
	private static Map<Class<? extends AbstractPage>, AbstractPage> pages = new HashMap<>();
	private static Class<? extends AbstractPage> lastUsed;

	/**
	 * 获取Page实例
	 * @param clazz Page类型
	 * @return Page实例
	 */
	@SuppressWarnings("unchecked")
	public static <T extends AbstractPage> T getPage(Class<T> clazz) {
		ArgumentUtil.notNull(clazz, "class must not be null");
		AbstractPage p = pages.get(clazz);

		if (p == null) {
			p = InstancePage(clazz);
			pages.put(clazz, p);
		}
		return (T) p;
	}

	/**
	 * 获取最后使用的Page类，可通过{@link #getPage(Class)}获取其实例
	 * @return 最后使用的Page类
	 */
	public static Class<? extends AbstractPage> getLastUsed() {
		return lastUsed;
	}

	/**
	 * 请勿调用，仅供动态代理使用
	 * @param clazz
	 */
	public static void setLastUsed(Class<? extends AbstractPage> clazz) {
		lastUsed = clazz;
	}

	/*
	 * 生成Page实例，调用setMain方法，生成动态代理
	 */
	private static AbstractPage InstancePage(Class<? extends AbstractPage> clazz) {
		AbstractPage p = null;

		if (!IModule.class.isAssignableFrom(clazz)) {
			// create proxy
			p = ProxyFactory.getEnhancedProxy(clazz, new PageProxy());
		} else {
			// instance
			try {
				p = ClassUtil.getInstance(clazz);
			} catch (LightReflectionException e) {
				throw new RuntimeException(e.getCause());
			}
		}

		// setMain
		SetMain anno = clazz.getAnnotation(SetMain.class);

		if (anno != null) {
			Class<? extends AbstractPageMain> c = anno.value();

			if (clazz.equals(c)) {
				throw new IllegalArgumentException("pageMain is same with current class: " + clazz.getName());
			}
			// TODO check circle
			p.setMain(getPage(c));
		}

		return p;
	}
}
