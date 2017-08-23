package com.bizdata.component.base.dao;

import com.bizdata.component.base.entity.RoleResource;

public interface RoleResourceMapper {
	int insert(RoleResource record);

	int insertSelective(RoleResource record);

	void deleteRoleResourceBycond(RoleResource roleResource);

	void deleteRoleResourceByResourceId(Long resourceid);
}