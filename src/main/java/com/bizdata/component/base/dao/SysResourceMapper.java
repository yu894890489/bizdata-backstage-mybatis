package com.bizdata.component.base.dao;

import java.util.List;

import com.bizdata.component.base.entity.SysResource;

public interface SysResourceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysResource record);

    int insertSelective(SysResource record);

    SysResource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysResource record);

    int updateByPrimaryKey(SysResource record);
    
    List<SysResource> findAll();
    
    List<SysResource> findChildens(Long parent_id);
    
    int selectCountByParent(Long parent);
    
}