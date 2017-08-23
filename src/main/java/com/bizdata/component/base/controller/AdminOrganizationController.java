package com.bizdata.component.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizdata.commons.JsonMessageUtil;
import com.bizdata.component.base.entity.SysOrganization;
import com.bizdata.component.base.service.OrganizationService;
import com.google.gson.Gson;

/**
 * 组织机构
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 下午3:52:55<br/>
 *         描述：
 */
@Controller
@RequestMapping("/admin/organization")
public class AdminOrganizationController {

	@Autowired
	private OrganizationService organizationService;

	@RequiresPermissions("sys:organization:view")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		return "admin_page/organization/organization_list";
	}

	/**
	 * 返回部门list json数据
	 * 
	 * @return
	 */
	@RequiresPermissions("sys:organization:view")
	@RequestMapping(value = "/organizationList", method = RequestMethod.GET)
	@ResponseBody
	public String organizationList() {
		Map<String, Object> organizationMap = new HashMap<String, Object>();
		organizationMap.put("rows", organizationService.findAll());
		return new Gson().toJson(organizationMap);
	}

	/**
	 * 添加部门
	 * 
	 * @return
	 */
	@RequiresPermissions("sys:organization:create")
	@ResponseBody
	@RequestMapping(value = "/addOrganization", method = RequestMethod.POST)
	public String addOrganization(SysOrganization sysOrganization) {
		String json = "";
		try {
			organizationService.insertOrganization(sysOrganization);
			json = JsonMessageUtil.setSuccessJsonString();
		} catch (Exception e) {
			e.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString("新增部门失败");
		}
		return json;
	}

	/**
	 * 部门更新操作
	 * 
	 * @param sysOrganization
	 * @return
	 */
	@RequiresPermissions("sys:organization:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(SysOrganization sysOrganization) {
		String json = "";
		try {
			organizationService.updateOrganization(sysOrganization);
			json = JsonMessageUtil.setSuccessJsonString();
		} catch (Exception e) {
			e.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString("系统异常");
		}
		return json;
	}

	/**
	 * 删除部门
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月14日 下午3:09:19<br/>
	 *         描述：
	 * @param sysResource
	 * @return
	 */
	@RequiresPermissions("sys:organization:delete")
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(Long id) {
		String json = "";
		try {
			if (organizationService.canDeleteOrganization(id)) {
				// 可以删除
				organizationService.deleteOrganization(id);
				json = JsonMessageUtil.setSuccessJsonString();
			} else {
				// 部门下存在人员
				json = JsonMessageUtil.setErrorJsonString("部门或子部门下存在人员，不允许删除！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString("系统异常");
		}
		return json;
	}

	/**
	 * @author 顾剑峰
	 * @description 获取相同父项的组织机构 并包装到TreeModel中给页面提供树形展示
	 * @param id
	 *            父id
	 * @return
	 * @update 2015-5-6 下午1:37:47
	 */
	@RequiresPermissions("sys:organization:view")
	@RequestMapping(value = "/brotherOrganization/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String brotherOrganization(@PathVariable("id") Long id) {
		String json = "";
		try {
			SysOrganization orgCnd = new SysOrganization();
			orgCnd.setParent(Long.valueOf(id));
			List<SysOrganization> orgList = organizationService
					.selectByCnd(orgCnd);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (orgList != null) {
				for (SysOrganization org : orgList) {
					Map<String, Object> map = new HashMap<String, Object>();
					int count = organizationService.selectChildrenCountById(org
							.getId());
					if (count > 0) {
						map.put("type", "folder");
					} else {
						map.put("type", "item");
					}
					map.put("id", org.getId() + "");
					map.put("text", org.getName());
					list.add(map);
				}
			}
			json = new Gson().toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
