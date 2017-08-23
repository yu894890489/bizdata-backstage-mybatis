package com.bizdata.component.base.controller;

import java.util.HashMap;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizdata.component.base.entity.SysLoginLogout;
import com.bizdata.component.base.service.SysLoginLogoutService;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author 顾剑峰<br/>
 *         创建时间：2015年7月22日 上午10:04:50<br/>
 *         描述：系统菜单，登录登出日志展示Controller
 */

@Controller
@RequestMapping("/admin/loginlogout")
public class AdminLoginLogoutController {
	@Autowired
	private SysLoginLogoutService loginLogoutService;

	/**
	 * 侧边栏打开角色管理路径 返回对应的list.jsp页面 对应在后台资源列表中填入的url
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月12日 下午4:24:25<br/>
	 *         描述：
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:loginlogout:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		return "admin_page/login_logout_log/login_logout_log";// 跳转到对应登录登出日志url
	}

	/**
	 * @author 顾剑峰
	 * @description 异步获取登录登出日志列表信息
	 * @param user
	 * @return
	 * @update 2015-5-7 下午8:46:51
	 */
	@RequiresPermissions("sys:loginlogout:view")
	@RequestMapping(value = "/loginlogoutList", method = RequestMethod.GET)
	@ResponseBody
	public String loginlogoutList(int page, int rows) {
		Map<String, Object> sysLoginLogoutMap = new HashMap<String, Object>();
		PageInfo<SysLoginLogout> pageInfo = loginLogoutService
				.selectSysLoginLogoutByCondByPage(page, rows);
		sysLoginLogoutMap.put("rows", pageInfo.getList());
		sysLoginLogoutMap.put("currentPage", page);
		sysLoginLogoutMap.put("totalPageSize", pageInfo.getPages());
		sysLoginLogoutMap.put("totalRecords", pageInfo.getTotal());
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		String json = gson.toJson(sysLoginLogoutMap);
		return json;
	}
}
