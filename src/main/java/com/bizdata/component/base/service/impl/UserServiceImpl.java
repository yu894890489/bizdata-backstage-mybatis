package com.bizdata.component.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizdata.component.base.dao.UserMapper;
import com.bizdata.component.base.dao.UserRoleMapper;
import com.bizdata.component.base.entity.Role;
import com.bizdata.component.base.entity.User;
import com.bizdata.component.base.entity.UserRole;
import com.bizdata.component.base.service.PasswordHelper;
import com.bizdata.component.base.service.RoleService;
import com.bizdata.component.base.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * service实现
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 下午3:51:30<br/>
 *         描述：
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private PasswordHelper passwordHelper;

	@Autowired
	private RoleService roleService;

	/**
	 * 创建用户
	 * 
	 * @param user
	 */
	@Override
	public long insertUser(User user, String[] roleids) {

		// 加密密码
		passwordHelper.encryptPassword(user);
		// 持久化用户信息表
		userMapper.insert(user);
		List<UserRole> userRoleList = null;
		userRoleList = new ArrayList<UserRole>();
		// 持久化用户角色
		if (roleids != null && roleids.length > 0) {
			for (String roleid : roleids) {
				UserRole userRole = new UserRole();
				userRole.setRoleid(Long.valueOf(roleid));
				userRole.setUserid(user.getId());
				userRoleList.add(userRole);
			}
			// 批量插入人员角色关系表
			userRoleMapper.insertBatch(userRoleList);
		}

		return user.getId();
	}

	@Override
	public int updateUser(User user, String[] roleids) {
		List<UserRole> userRoleList = null;
		// 先删除用户角色,然后添加新的角色
		UserRole cond = new UserRole();
		cond.setUserid(user.getId());
		userRoleMapper.deleteUserRoleBycond(cond);
		if (roleids != null && roleids.length > 0) {
			userRoleList = new ArrayList<UserRole>();
			for (String roleid : roleids) {
				UserRole userRole = new UserRole();
				userRole.setRoleid(Long.valueOf(roleid));
				userRole.setUserid(user.getId());
				userRoleList.add(userRole);
			}
			// 批量插入人员角色关系表
			userRoleMapper.insertBatch(userRoleList);
		}
		if (user.getPassword().length() == 32) {// 密码长度为32时表示为原来的加密后的密码，不需要修改
			user.setPassword(null);
		} else {
			passwordHelper.encryptPassword(user);
		}
		// 更新用户
		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public void deleteUser(Long userId) {
		// 删除用户信息
		userMapper.deleteByPrimaryKey(userId);
		// 删除用户角色关联
		UserRole userRole = new UserRole();
		userRole.setUserid(userId);
		userRoleMapper.deleteUserRoleBycond(userRole);
	}

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPassword
	 */
	public void changePassword(Long userId, String newPassword) {
		User user = userMapper.selectByPrimaryKey(userId);
		user.setPassword(newPassword);
		passwordHelper.encryptPassword(user);
		userMapper.updateByPrimaryKey(user);
	}

	@Override
	public User findOne(Long userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public List<User> findAll() {
		return userMapper.selectAll();
	}

	/**
	 * 根据用户名查找用户
	 * 
	 * @param username
	 * @return
	 */
	public User selectUserDetailByUsername(String username) {
		return userMapper.selectUserDetailByUsername(username);
	}

	/**
	 * 根据用户名查找其角色
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findRoles(String username) {
		User user = selectUserDetailByUsername(username);
		if (user == null) {
			return Collections.emptySet();
		}
		Set<String> roles = new HashSet<String>();
		for (Role role : user.getRoleList()) {
			roles.add(role.getRole());
		}
		return roles;
	}

	/**
	 * 根据用户名查找其权限
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findPermissions(String username) {
		// 根据用户名获取用户
		User user = selectUserDetailByUsername(username);
		if (user == null) {
			return Collections.emptySet();
		}
		// 返回对应的权限
		Set<String> permissions = roleService.findPermissions(user.getRoleList());
		return permissions;
	}

	@Override
	public PageInfo<User> selectUserByCondByPage(int page, int rows, User user) {
		PageHelper.startPage(page, rows);
		List<User> list = userMapper.selectUserByCondByPage(user);
		return new PageInfo<User>(list);
	}
}
