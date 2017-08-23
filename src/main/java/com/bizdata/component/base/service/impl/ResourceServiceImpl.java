package com.bizdata.component.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bizdata.component.base.dao.RoleResourceMapper;
import com.bizdata.component.base.dao.SysResourceMapper;
import com.bizdata.component.base.entity.SysResource;
import com.bizdata.component.base.entity.SysResource.ResourceType;
import com.bizdata.component.base.service.ResourceService;
import com.google.gson.Gson;

/**
 * service实现
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 下午3:51:08<br/>
 *         描述：
 */
@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private SysResourceMapper sysResourceMapper;

	@Autowired
	private RoleResourceMapper RoleResourceMapper;

	@Override
	public int insertResource(SysResource sysResource) {
		// 获取父元素
		SysResource parentResource = sysResourceMapper.selectByPrimaryKey(sysResource.getParent());
		// 设置新增节点类型
		switch (parentResource.getType()) {
		case root:
			// 父节点是root节点
			// 则子节点类型肯定为column
			sysResource.setType(ResourceType.column);
			break;
		case column:
			// 父节点是栏目节点
			// 则子节点类型肯定为menu
			sysResource.setType(ResourceType.menu);
			break;
		case menu:
			// 父节点为菜单节点
			// 则子节点类型肯定为action
			sysResource.setType(ResourceType.action);
			break;
		case action:
			// 按钮节点
			// 按钮节点下面无法添加子节点
			break;
		default:
			break;
		}

		// 统一增加新节点层级
		sysResource.setLevel(String.valueOf((Integer.parseInt(parentResource.getLevel().trim()) + 1)));

		// 设置当前子节点的父节点如果是叶子类型设置为不是叶子类型
		if (parentResource.getIsleaf()) {
			parentResource.setIsleaf(false);
			sysResourceMapper.updateByPrimaryKey(parentResource);
		}

		// 执行插入操作
		return sysResourceMapper.insert(sysResource);
	}

	@Override
	public int updateResource(SysResource resource) {
		return sysResourceMapper.updateByPrimaryKeySelective(resource);
	}

	/**
	 * 根据id递归删除资源与它的孩子们
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月14日 下午3:56:56<br/>
	 *         描述：
	 * @param resourceId
	 */
	@Override
	public void deleteResource(Long resourceId) throws Exception {
		// 根据resourceId获取要删除的SysResource
		SysResource delSysResource = sysResourceMapper.selectByPrimaryKey(resourceId);
		// 根据id删除resource中记录
		sysResourceMapper.deleteByPrimaryKey(resourceId);
		// 根据id删除role_resource中记录
		RoleResourceMapper.deleteRoleResourceByResourceId(resourceId);
		// 根据父id字段查出孩子们
		List<SysResource> childrens = findChildens(resourceId);
		for (SysResource sysResource : childrens) {
			// 递归删除
			deleteResource(sysResource.getId());
		}
		// 根据parentId查询
		int count = sysResourceMapper.selectCountByParent(delSysResource.getParent());
		if (count < 1) {
			// 如果父节点下不存在子节点了，则设置父节点为叶子
			SysResource parentSysResource = sysResourceMapper.selectByPrimaryKey(delSysResource.getParent());
			if (null != parentSysResource) {
				parentSysResource.setIsleaf(true);
				sysResourceMapper.updateByPrimaryKeySelective(parentSysResource);
			}
		}
	}

	/**
	 * 查询出所有资源并封装成jqgrid treegrid需要的格式结构
	 */
	@Override
	public List<SysResource> findAll() {
		return createFormatResourceList(sysResourceMapper.findAll());
	}

	/**
	 * 创建符合jqgrid treegrid格式的资源列表
	 * 
	 * @param list
	 * @return
	 */
	private List<SysResource> createFormatResourceList(List<SysResource> list) {
		// 返回的list
		List<SysResource> rootArray = new ArrayList<SysResource>();
		for (SysResource resource : list) {
			if (resource.getParent() == 0) {
				rootArray.add(resource);
				rootArray.addAll(getNextLevelResource(resource, list));
			}
		}
		return rootArray;
	}

	/**
	 * 递归查找获取下一级的resource
	 * 
	 * @return
	 */
	private List<SysResource> getNextLevelResource(SysResource currentNode, List<SysResource> list) {
		List<SysResource> temp_list = new ArrayList<SysResource>();
		for (SysResource newNode : list) {
			if (newNode.getParent() != null && newNode.getParent().compareTo(currentNode.getId()) == 0) {
				temp_list.add(newNode);
				temp_list.addAll(getNextLevelResource(newNode, list));
			}
		}
		return temp_list;
	}

	/**
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月1日 下午2:47:34<br/>
	 *         描述：判断该资源，当前用户是否有权限
	 * @param permissions
	 * @param resource
	 * @return
	 */
	private boolean hasPermission(Set<String> permissions, SysResource resource) {
		if (StringUtils.isEmpty(resource.getPermission())) {
			return true;
		}
		for (String permission : permissions) {
			WildcardPermission p1 = new WildcardPermission(permission);
			WildcardPermission p2 = new WildcardPermission(resource.getPermission());
			if (p1.implies(p2) || p2.implies(p1)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getUserMenus(Set<String> permissions) {
		// 获取全部资源
		List<SysResource> allResources = findAll();

		// 获取展示的栏目与菜单
		List<SysResource> columns_menus = new ArrayList<SysResource>();

		// 循环遍历取出栏目与菜单
		for (SysResource resource : allResources) {
			// 判断是否根节点
			if (resource.isRootNode()) {
				continue;
			}
			// 判断资源类型是否为栏目
			if (resource.getType() != SysResource.ResourceType.column) {
				continue;
			}
			// 判断是否有权限
			if (!hasPermission(permissions, resource)) {
				continue;
			}
			// 遍历取出菜单
			for (SysResource menu : allResources) {
				if (menu.isRootNode()) {
					continue;
				}
				if (menu.getType() != SysResource.ResourceType.menu) {
					continue;
				}
				// 判断是否有权限
				if (!hasPermission(permissions, menu)) {
					continue;
				}
				// 判断此menu父id是否是资源Id
				if (menu.getParent().equals(resource.getId())) {
					resource.getMenus().add(menu);
				}
			}
			// 返回栏目与菜单
			columns_menus.add(resource);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menus", columns_menus);
		Gson gson = new Gson();
		return gson.toJson(map);
	}

	/**
	 * 根据父id找到孩子们
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月14日 下午3:52:47<br/>
	 *         描述：
	 * @param parent_id
	 * @return
	 */
	@Override
	public List<SysResource> findChildens(Long parent_id) {
		return sysResourceMapper.findChildens(parent_id);
	}

	@Override
	public SysResource findSysResource(Long id) {
		return sysResourceMapper.selectByPrimaryKey(id);
	}
}
