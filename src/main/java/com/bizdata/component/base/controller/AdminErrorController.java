package com.bizdata.component.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户管理授权失败Controller，用于跳转到
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Controller
@RequestMapping("/error")
public class AdminErrorController {
	
	/**
	 * 跳转到401未授权页面
	 *
	 * @return String类型url值
	 */
	@RequestMapping(value = "/401", method = RequestMethod.GET)
	public String error_401() {
		return "error/401-error";
	}

	
	/**
	 * 跳转到500系统错误页面
	 *
	 * @return String类型url值
	 */
	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public String error_500() {
		return "error/500-error";
	}
}
