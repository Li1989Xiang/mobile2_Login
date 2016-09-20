package mobile.page.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public abstract class ProxyFactory {

	public static <T> T getEnhancedProxy(Class<T> requiredClazz, MethodInterceptor interceptor) {
		return getEnhancedProxy(requiredClazz, new Class<?>[] {}, new Object[] {}, interceptor);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getEnhancedProxy(Class<T> requiredClazz, Class<?>[] params, Object[] values,
			MethodInterceptor interceptor) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(requiredClazz);
		enhancer.setCallback(interceptor);
		return (T) enhancer.create(params, values);
	}
}
