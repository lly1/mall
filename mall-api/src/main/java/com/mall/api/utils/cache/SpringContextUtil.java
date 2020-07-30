package com.mall.api.utils.cache;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lly
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext context;
	/**  
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * 实现该接口的setApplicationContext(ApplicationContext context)方法，并保存ApplicationContext 对象。Spring初始化时，会通过该方法将ApplicationContext对象注入。
	 * @param applicationContext  
	 * @throws BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(context == null){
			context = applicationContext;
		}
	}

	/**  
	 * @return ApplicationContext  
	 */
	public static ApplicationContext getApplicationContext() {
		return context;
	}

	/**  
	 * 获取对象     
	 * @param name  
	 * @return Object 一个以所给名字注册的bean的实例  
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return context.getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
			return getApplicationContext().getBean(clazz);
	}

	public static <T> T getBean(String name,Class<T> clazz) {
		return getApplicationContext().getBean(name,clazz);
	}

	private static String[] getNullPropertyNames (Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for(java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null) emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	public static void copyPropertiesIgnoreNull(Object src, Object target){
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

}
