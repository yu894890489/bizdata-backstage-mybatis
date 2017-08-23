package com.bizdata.component.base.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bizdata.component.base.entity.SysLoginLogout;
import com.bizdata.component.base.service.SysLoginLogoutService;

/**
 * @author 顾剑峰<br/>
 *         创建时间：2015年7月20日 下午1:48:24<br/>
 *         描述：安全退出操作
 */
@Controller
@RequestMapping("/admin")
public class AdminLogoutController {

	@Autowired
	private SysLoginLogoutService sysLoginLogoutService;

	/**
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Subject subject = SecurityUtils.getSubject();
		if (null != subject.getPrincipal()) {
			// 执行入库操作
			sysLoginLogoutService.log(logoutFormat(subject, request));
			if (subject.isAuthenticated()||subject.isRemembered()) {
				subject.logout();// session会销毁，在SessionListener监听session销毁，清理权限缓存
			}
		}
		// 执行跳转到登陆页
		response.sendRedirect(request.getContextPath() + "/admin/login");
	}

	/**
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年7月21日 下午3:44:08<br/>
	 *         描述：封装退出操作日志对象
	 * @param currentUser
	 */
	private SysLoginLogout logoutFormat(Subject currentUser, HttpServletRequest request) {
		// 用户名
		String username = currentUser.getPrincipal().toString();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 时间
		Date date = new Date();
		// 格式化时间
		String dateString = dateFormat.format(date);
		// 操作内容
		String content = username + " 于 " + dateString + " 安全退出系统 ";
		// 日志类型
		String type = "logout";
		// 获取ip
		String ip = request.getRemoteAddr();
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
