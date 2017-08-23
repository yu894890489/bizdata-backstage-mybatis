package com.bizdata.component.base.controller;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizdata.component.base.service.ResourceService;
import com.bizdata.component.base.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

	@Autowired
	private ResourceService resourceService;
	@Autowired
	private UserService userService;

	/**
	 * 展示系统菜单
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 下午2:46:28<br/>
	 *         描述：
	 * @param loginUser
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/")
	public String index(Model model, HttpServletRequest request,
			HttpServletResponse response, String page) throws IOException,
			ServletException {
		if (page == null) {
			return "include/outer";
		} else if ("index".equals(page)) {
			return "admin_page/index";
		} else {
			response.sendRedirect(page);
			return null;
		}
	}

	/**
	 * 
	 * @author 顾剑峰
	 * @description 查询系统菜单
	 * @param loginUser
	 * @param model
	 * @return
	 * @update 2015-4-13 下午3:13:17
	 */
	@RequestMapping(value = "/getAllUserMenus")
	@ResponseBody
	public String getAllUserMenus() {
		// 根据登录用户名获取权限
		Set<String> permissions = userService.findPermissions(SecurityUtils
				.getSubject().getPrincipal().toString());
		return resourceService.getUserMenus(permissions);
	}
}
