package com.bizdata.component.base.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录验证controller
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 下午1:05:39<br/>
 *         描述：
 */
@Controller
@RequestMapping("/admin")
public class AdminLoginController {

	/**
	 * 登录controller
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 下午2:46:56<br/>
	 *         描述：其实认证操作交由shiro authc拦截器进行登录，如果认证失败则抛出异常存入shiroLoginFailure属性中!
	 *         因为login.jsp中，action不需要指定，所以即使认证失败仍然跳转自己，进行错误的展示。
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String showLoginForm(HttpServletRequest req, Model model) {
		String exceptionClassName = (String) req
				.getAttribute("shiroLoginFailure");
		String error = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (IncorrectCredentialsException.class.getName().equals(
				exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (LockedAccountException.class.getName().equals(
				exceptionClassName)) {
			error = "该账号被锁定";
		} else if (exceptionClassName != null) {
			error = "认证失败";
		}
		model.addAttribute("error", error);
		return "admin_page/login";
	}

}
