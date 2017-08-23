package com.bizdata.framework.filter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bizdata.component.base.entity.SysLoginLogout;
import com.bizdata.component.base.service.SysLoginLogoutService;


/**
 * 登录过滤器，继承FormAuthenticationFilter，重写onLoginSuccess方法 如果登录成功并且记录日志
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public class LoginFilter extends FormAuthenticationFilter {
	@Autowired
	private SysLoginLogoutService sysLoginLogoutService;

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		// 将request，response转成对应类
		HttpServletRequest req = WebUtils.toHttp(request);
		HttpServletResponse resp = WebUtils.toHttp(response);
		// 当登录成功后，重定向到项目根路径+xml中配置的SuccessUrl
		// 解决原先如果用户通过url指定链接，则登录后不会进入首页问题
		// 当然此处的设置应与shiro中配置的successUrl一致
		resp.sendRedirect(req.getContextPath() + super.getSuccessUrl());
		// 执行登录日志的记录
		saveLog(request);
		return false;
	}

	/**
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年12月20日 下午10:13:59<br/>
	 *         描述：表示当访问拒绝时是否已经处理了
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)
				&& !isAjaxRequest(WebUtils.toHttp(request))) {
			if (isLoginSubmission(request, response)) {
				return executeLogin(request, response);
			} else {
				return true;
			}
		} else {
			if (isAjaxRequest(WebUtils.toHttp(request))) {
				HttpServletResponse res = WebUtils.toHttp(response);
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				redirectToLogin(request, response);
			}
			return false;
		}
	}

	
	/**
	 * 判断是否是ajax请求
	 *
	 * @param request 请求
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest"
				.equals(request.getHeader("X-Requested-With").toString()));
	}

	
	/**
	 * 记录登录日志，并且入库
	 *
	 * @param request 请求
	 */
	private void saveLog(ServletRequest request) {
		// 获取当前用户对象
		Subject currentUser = SecurityUtils.getSubject();
		// 对象session
		Session session = currentUser.getSession();
		// 是否通过login认证通过,与isRemembered()是互斥的
		boolean authenticated = currentUser.isAuthenticated();
		// 是否通过remember认证登录
		boolean remembered = currentUser.isRemembered();
		// 如果认证通过，并且session没有值，则进行记录,作为新增用户。
		// 如果认证通过，session有值，则不记录，说明已经登录过的。
		if ((authenticated || remembered)
				&& null == session.getAttribute("authenticated")) {
			session.setAttribute("authenticated", "true");// session记录是否认证
			String username = currentUser.getPrincipal().toString();// 用户名
			String ip = request.getRemoteAddr();// ip
			session.setAttribute("username", username);// session记录username，方便后面超时时进行日志写入
			session.setAttribute("ip", ip);// session记录ip，方便后面超时时进行日志写入
			sysLoginLogoutService.log(loginFormat(username, ip));
		} else {
			// 不进行任何操作
		}
	}

	/**
	 * 登录日志格式
	 * 
	 * @param username
	 * @param ip
	 */
	private SysLoginLogout loginFormat(String username, String ip) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 时间
		Date date = new Date();
		// 格式化时间
		String dateString = dateFormat.format(date);
		// 操作内容
		String content = username + " 于 " + dateString + " 成功登录后台管理系统 ";
		// 日志类型
		String type = "login";

		// 封装对象
		SysLoginLogout sysLoginLogout = new SysLoginLogout();
		sysLoginLogout.setUsername(username);
		sysLoginLogout.setContent(content);
		sysLoginLogout.setDate(date);
		sysLoginLogout.setType(type);
		sysLoginLogout.setIp(ip);
		return sysLoginLogout;
	}
}
