package com.bizdata.component.base.dao;

import java.util.List;

import com.bizdata.component.base.entity.SysOrganization;

public interface SysOrganizationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysOrganization record);

    int insertSelective(SysOrganization record);

    SysOrganization selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysOrganization record);

    int updateByPrimaryKey(SysOrganization record);
    
    List<SysOrganization> selectAll();
    
    List<SysOrganization> selectByCnd(SysOrganization excludeOraganization);
    
    int selectChildrenCountById(Long id);
    
    List<SysOrganization> findChildens(Long parent_id);
    
    int selectCountByParent(Long parent);
}