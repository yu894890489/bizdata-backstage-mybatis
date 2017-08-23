package com.bizdata.framework.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Spring初始化执行的方法，用于容器启动后执行
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年6月25日 上午10:57:39<br/>
 *         描述：
 */
@Component
public class InstantiationTracingBeanPostProcessor implements
		ApplicationListener<ContextRefreshedEvent> {
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			System.out.println("!!!!!!!!!~~~~~~~~初始化之后执行...");
		}
	}
}
