package com.bizdata.component.base.dao;

import java.util.List;

import com.bizdata.component.base.entity.Role;

public interface RoleMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Role record);

	int insertSelective(Role record);

	Role selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Role record);

	int updateByPrimaryKey(Role record);

	List<Role> selectAll();

	/**
	 * @author 顾剑峰
	 * @description 查询所有角色，同时查询出角色中的资源
	 * @return List<Role>
	 * @update 2015-4-30 下午5:15:00
	 */
	List<Role> selectAllWithRes();

	Role selectWithResByPrimaryKey(Long roleId);

}