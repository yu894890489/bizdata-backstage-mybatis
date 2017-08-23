package com.bizdata.component.base.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizdata.commons.JsonMessageUtil;
import com.bizdata.commons.UserNameSessionIdMap;
import com.bizdata.commons.annotation.Loggable;
import com.bizdata.component.base.entity.User;
import com.bizdata.component.base.service.UserService;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;


/**
 * 用户操作Controller
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

	@Autowired
	private UserService userService;

	@Autowired(required = true)
	private SessionManager sessionManager;

	@Loggable
	private Logger logger;

	/**
	 * 用户信息展示
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 下午2:55:29<br/>
	 *         描述：@RequiresPermissions指定需要的权限信息
	 * @param model
	 * @return String
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, User user) {
		if (logger.isInfoEnabled()) {
			logger.info("执行我自定义的测试咯...");
		}
		return "admin_page/user/user_list";
	}

	/**
	 * @author 顾剑峰
	 * @description 异步获取用户列表信息
	 * @param user
	 * @return String
	 * @update 2015-5-7 下午8:46:51
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	@ResponseBody
	public String read(User user, boolean _search) {
		Map<String, Object> userMap = new HashMap<String, Object>();
		PageInfo<User> userList = userService.selectUserByCondByPage(user.getPage(), user.getRows(), user);
		userMap.put("rows", userList.getList());
		userMap.put("currentPage", user.getPage());
		userMap.put("totalPageSize", userList.getPages());
		userMap.put("totalRecords", userList.getTotal());

		return new Gson().toJson(userMap);
	}

	/**
	 * @author 顾剑峰
	 * @description 新增用户
	 * @param user
	 * @param roles
	 *            用户信息
	 * @param response
	 *            用户角色id
	 * @return String
	 * @update 2015-5-7 下午8:16:35
	 */
	@RequiresPermissions("sys:user:create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public String create(User user, String[] roles) {
		String json = "";
		if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
			json = JsonMessageUtil.setErrorJsonString("用户信息不完整，请确认后重新提交");
			return json;
		}
		try {
			userService.insertUser(user, roles);
			json = JsonMessageUtil.setSuccessJsonString();
		} catch (Exception e) {
			e.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString("新增用户失败,请确认用户名不重复！");
		}
		return json;
	}

	/**
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年7月24日 下午3:12:08<br/>
	 *         描述：用户更新
	 * @param user
	 * @param roles
	 * @return String
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(User user, String[] roles) {
		String json = "";
		if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
			json = JsonMessageUtil.setErrorJsonString("用户信息不完整，请确认后重新提交");
			return json;
		}

		// 如果被修改的用户是登录着的用户,而且状态设置为禁用,则踢除该用户
		String kickoutSessionId = UserNameSessionIdMap.get(user.getUsername());
		if (null != kickoutSessionId && "1".equals(user.getLocked())) {
			try {
				Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
				if (kickoutSession != null) {
					// 设置会话的kickout属性表示踢出了
					kickoutSession.setAttribute("kickout", true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			userService.updateUser(user, roles);
			json = JsonMessageUtil.setSuccessJsonString();
		} catch (Exception e) {
			e.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString("新增用户失败,请确认用户名不重复！");
		}
		return json;
	}

	/**
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年7月24日 下午3:12:22<br/>
	 *         描述：执行用户删除
	 * @param id
	 * @return String
	 */
	@RequiresPermissions("sys:user:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(Long id) {
		String json = "";
		if (id == 1) {
			json = JsonMessageUtil.setErrorJsonString("admin为系统超级管理员，初始数据不可以删除！");
			return json;
		}
		try {
			userService.deleteUser(id);
			json = JsonMessageUtil.setSuccessJsonString();
		} catch (Exception e) {
			e.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString("系统异常");
		}
		return json;
	}
}
