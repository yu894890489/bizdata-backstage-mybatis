package com.bizdata.component.base.dao;

import java.util.List;

import com.bizdata.component.base.entity.SysLoginLogout;

public interface SysLoginLogoutMapper {
	int deleteByPrimaryKey(Long id);

	int insert(SysLoginLogout record);

	int insertSelective(SysLoginLogout record);

	SysLoginLogout selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(SysLoginLogout record);

	int updateByPrimaryKey(SysLoginLogout record);

	/**
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年7月22日 上午11:01:11<br/>
	 *         描述：根据条件、分页查询SysLoginLogout
	 * @param pageC
	 * @param record
	 * @return
	 */
	List<SysLoginLogout> selectSysLoginLogoutByCondByPage();

}