package com.bizdata.commons;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import com.bizdata.commons.annotation.Loggable;

/**
 * Logger注入到@InitLogger注解标记的字段上,完成初始化操作,代替以往的日志初始化方式
 *
 * @version
 *
 * @author sdevil507
 */
@Component
public class LoggerInjector implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
		ReflectionUtils.doWithFields(bean.getClass(), new FieldCallback() {
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				// make the field accessible if defined private
				ReflectionUtils.makeAccessible(field);
				if (field.getAnnotation(Loggable.class) != null) {
					Logger log = LoggerFactory.getLogger(bean.getClass());
					field.set(bean, log);
				}
			}
		});
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, final String beanName) throws BeansException {
		return bean;
	}
}
