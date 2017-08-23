package com.bizdata.component.base.controller;

import java.util.HashMap;
import java.util.Map;

import com.bizdata.commons.JsonMessageUtil;
import com.bizdata.component.base.entity.SysResource;
import com.bizdata.component.base.service.ResourceService;
import com.google.gson.Gson;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 资源操作Controller
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 下午12:29:48<br/>
 *         描述：
 */
@Controller
@RequestMapping("/admin/resource")
public class AdminResourceController {

	@Autowired
	private ResourceService resourceService;

	/**
	 * 返回资源管理list.jsp页面 对应web-inf下的resource/list.jsp
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月12日 下午4:26:19<br/>
	 *         描述：
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:resource:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		return "admin_page/resource/resource_list";
	}

	/**
	 * 返回资源管理的json数据
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月12日 下午4:27:38<br/>
	 *         描述：
	 * @param role
	 * @return
	 */
	@RequiresPermissions("sys:resource:view")
	@RequestMapping(value = "/resourceList", method = RequestMethod.GET)
	@ResponseBody
	public String resourceList() {
		Map<String, Object> resourceMap = new HashMap<String, Object>();
		resourceMap.put("rows", resourceService.findAll());
		return new Gson().toJson(resourceMap);
	}

	/**
	 * 添加资源
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月13日 下午2:33:21<br/>
	 *         描述：
	 * @param sysResource
	 * @return
	 */
	@RequiresPermissions("sys:resource:create")
	@ResponseBody
	@RequestMapping(value = "/addResource", method = RequestMethod.POST)
	public String addResource(SysResource sysResource) {
		String json = "";
		try {
			resourceService.insertResource(sysResource);
			json = JsonMessageUtil.setSuccessJsonString();
		} catch (Exception e) {
			e.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString("新增资源失败");
		}
		return json;
	}

	/**
	 * 删除资源
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月14日 下午3:09:19<br/>
	 *         描述：
	 * @param sysResource
	 * @return
	 */
	@RequiresPermissions("sys:resource:delete")
	@ResponseBody
	@RequestMapping(value = "/deleteResource", method = RequestMethod.POST)
	public String deleteResource(Long id) {
		String json = "";
		try {
			resourceService.deleteResource(id);
			json = JsonMessageUtil.setSuccessJsonString();
		} catch (Exception e) {
			e.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString("系统异常");
		}
		return json;
	}

	/**
	 * 更新资源
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月14日 下午3:09:19<br/>
	 *         描述：
	 * @param sysResource
	 * @return
	 */
	@RequiresPermissions("sys:resource:update")
	@ResponseBody
	@RequestMapping(value = "/updateResource", method = RequestMethod.POST)
	public String updateResource(SysResource sysResource) {
		String json = "";
		try {
			// 因为jqgrid update操作不传递type，所以从原数据获取
			SysResource old_resource = resourceService
					.findSysResource(sysResource.getId());
			sysResource.setType(old_resource.getType());
			sysResource.setIsleaf(old_resource.getIsleaf());
			resourceService.updateResource(sysResource);
			json = JsonMessageUtil.setSuccessJsonString();
		} catch (Exception e) {
			e.printStackTrace();
			json = JsonMessageUtil.setErrorJsonString("系统异常");
		}
		return json;
	}
}
