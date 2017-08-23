package com.bizdata.framework.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 回退操作拦截器,判断是否是从首页执行回退
 *
 * @version
 *
 * @author sdevil507
 */
public class HistoryBackInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Subject subject = SecurityUtils.getSubject();
		if (null != subject.getPrincipal()) {
			// 执行跳转到登陆页
			response.sendRedirect(request.getContextPath() + "/admin/");
		}
		return true;
	}
}
