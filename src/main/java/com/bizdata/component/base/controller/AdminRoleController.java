package com.bizdata.component.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizdata.commons.JsonMessageUtil;
import com.bizdata.component.base.entity.Role;
import com.bizdata.component.base.entity.RoleResource;
import com.bizdata.component.base.service.RoleService;
import com.bizdata.framework.exception.RoleException;
import com.google.gson.Gson;

/**
 * 用户角色
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 下午3:53:09<br/>
 *         描述：
 */
@Controller
@RequestMapping("/admin/role")
public class AdminRoleController {

	@Autowired
	private RoleService roleService;

	/**
	 * 侧边栏打开角色管理路径 返回对应的list.jsp页面
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月12日 下午4:24:25<br/>
	 *         描述：
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		return "admin_page/role/role_list";
	}

	/**
	 * 返回对应的角色json数据
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月12日 下午4:25:22<br/>
	 *         描述：
	 * @param role
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "/roleList", method = RequestMethod.GET)
	@ResponseBody
	public String roleList(Role role) {
		Gson gson = new Gson();
		Map<String, Object> roleMap = new HashMap<String, Object>();
		List<Role> roleList = roleService.findAll();
		roleMap.put("rows", roleList);
		String json = gson.toJson(roleMap);
		return json;
	}

	/**
	 * 新增角色
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月12日 下午2:46:45<br/>
	 *         描述：
	 * @param role
	 * @return
	 */
	@RequiresPermissions("sys:role:create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public String create(Role role) {
		String json = "";
		try {
			roleService.insertRole(role);
			json = JsonMessageUtil.setSuccessJsonString();
		} catch (Exception e) {
			e.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString("添加角色失败,请确认角色名称不重复！");
		}
		return json;
	}

	/**
	 * 更新角色
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月12日 下午2:47:39<br/>
	 *         描述：
	 * @param role
	 * @return
	 */
	@RequiresPermissions("sys:role:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(Role role) {
		String json = "";
		try {
			roleService.updateRole(role);
			json = JsonMessageUtil.setSuccessJsonString();
		} catch (Exception e) {
			e.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString("修改角色失败,请确认角色名不重复!");
		}
		return json;
	}

	/**
	 * 角色删除
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月12日 下午2:47:49<br/>
	 *         描述：
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:role:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(Long id) {
		String json = "";
		if (id == 1) {
			json = JsonMessageUtil.setErrorJsonString("admin为超级管理员角色,初始化数据不可以删除！");
			return json;
		}
		try {
			roleService.deleteRole(id);
			json = JsonMessageUtil.setSuccessJsonString();
		} catch (Exception e) {
			e.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString("系统异常");
		}
		return json;
	}

	/**
	 * 添加角色与资源之间的关系
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月8日 上午10:55:45<br/>
	 *         描述：
	 * @param roleResource
	 */
	@ResponseBody
	@RequestMapping(value = "/relation/role/{roleid}/resource/{resourceid}", method = RequestMethod.POST)
	public String addRelation(RoleResource roleResource) {
		String json = "";
		try {
			roleService.addRelation(roleResource);
			json = JsonMessageUtil.setSuccessJsonString();
		} catch (Exception e) {
			e.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString("系统异常");
		}
		return json;
	}

	/**
	 * 解除角色与资源间的关系
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月8日 下午12:15:31<br/>
	 *         描述：
	 * @param roleResource
	 */
	@ResponseBody
	@RequestMapping(value = "/disassociate/role/{roleid}/resource/{resourceid}", method = RequestMethod.POST)
	public String disassociate(RoleResource roleResource) {
		String json = "";
		if (roleResource.getRoleid() == null || roleResource.getResourceid() == null) {
			json = JsonMessageUtil.setErrorJsonString("参数异常，请重试");
			return json;
		}
		try {
			roleService.disassociate(roleResource);
			json = JsonMessageUtil.setSuccessJsonString();
		} catch (RoleException b) {
			b.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString(b.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString("系统异常");
		}
		return json;
	}
}
