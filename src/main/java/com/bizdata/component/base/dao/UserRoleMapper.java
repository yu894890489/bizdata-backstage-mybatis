package com.bizdata.component.base.dao;

import java.util.List;

import com.bizdata.component.base.entity.UserRole;

public interface UserRoleMapper {
	int insert(UserRole record);

	int insertSelective(UserRole record);

	List<Long> selectRoleidByUserid(String userid);

	/**
	 * @author 顾剑峰
	 * @description 批量插入
	 * @param userid
	 * @return Long
	 * @update 2015-4-29 下午3:36:25
	 */
	long insertBatch(List<UserRole> userid);


	void deleteUserRoleBycond(UserRole userRole);
}