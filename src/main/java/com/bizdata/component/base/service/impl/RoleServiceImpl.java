package com.bizdata.component.base.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizdata.component.base.dao.RoleMapper;
import com.bizdata.component.base.dao.RoleResourceMapper;
import com.bizdata.component.base.dao.SysResourceMapper;
import com.bizdata.component.base.dao.UserRoleMapper;
import com.bizdata.component.base.entity.Role;
import com.bizdata.component.base.entity.RoleResource;
import com.bizdata.component.base.entity.SysResource;
import com.bizdata.component.base.entity.UserRole;
import com.bizdata.component.base.service.RoleService;
import com.bizdata.framework.exception.RoleException;

/**
 * service实现
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 下午3:51:19<br/>
 *         描述：
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RoleResourceMapper roleResourceMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private SysResourceMapper sysResourceMapper;

	public int insertRole(Role role) {
		return roleMapper.insert(role);
	}

	public int updateRole(Role role) {
		return roleMapper.updateByPrimaryKey(role);
	}

	public void deleteRole(Long roleId) {
		// 删除角色
		roleMapper.deleteByPrimaryKey(roleId);
		// 删除角色资源关联
		RoleResource roleResource = new RoleResource();
		roleResource.setRoleid(roleId);
		roleResourceMapper.deleteRoleResourceBycond(roleResource);
		// 删除人员角色关联
		UserRole userRole = new UserRole();
		userRole.setRoleid(roleId);
		userRoleMapper.deleteUserRoleBycond(userRole);
	}

	@Override
	public Role findOne(Long roleId) {
		return roleMapper.selectWithResByPrimaryKey(roleId);
	}

	@Override
	public List<Role> findAll() {
		return roleMapper.selectAllWithRes();
	}

	/**
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月1日 下午2:30:47<br/>
	 *         描述：根据角色获取对应的资源
	 * @param roleIds
	 * @return
	 */
	@Override
	public Set<String> findPermissions(List<Role> roleList) {
		Set<String> resourceSet = new HashSet<String>();
		for (Role role : roleList) {
			// 根据角色id获取角色对象，并同时将资源信息转换为list
			role = roleMapper.selectWithResByPrimaryKey(role.getId());
			if (role != null && role.getResourceList() != null) {
				// 执行一下资源id的去重
				for (SysResource res : role.getResourceList()) {
					resourceSet.add(res.getPermission());
				}
			}
		}
		return resourceSet;
	}

	@Override
	public void addRelation(RoleResource roleResource) {
		roleResourceMapper.insert(roleResource);
	}

	@Override
	public void disassociate(RoleResource roleResource) throws RoleException {
		// 判断是否是删除超级管理员角色的系统功能关系，若是则不能删除
		if (roleResource.getRoleid() == 1) {
			SysResource res = sysResourceMapper.selectByPrimaryKey(roleResource.getResourceid());
			if (res.getIsInitialized() != null && res.getIsInitialized().equals(true)) {
				throw new RoleException("超级管理员角色与初始化的系统资源关联关系不可以解除。");
			}
		}
		roleResourceMapper.deleteRoleResourceBycond(roleResource);
	}
}
