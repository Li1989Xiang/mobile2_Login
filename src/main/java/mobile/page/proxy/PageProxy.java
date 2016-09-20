package mobile.page.proxy;

import java.lang.reflect.Method;

import mobile.page.base.PageManager;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class PageProxy implements MethodInterceptor {

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		if (method.getName().startsWith("do")) {
			Class clazz = obj.getClass().getSuperclass();
			PageManager.setLastUsed(clazz);
		}

		return proxy.invokeSuper(obj, args);
	}

}
