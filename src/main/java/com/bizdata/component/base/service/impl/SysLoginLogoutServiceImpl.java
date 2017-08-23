package com.bizdata.component.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizdata.component.base.dao.SysLoginLogoutMapper;
import com.bizdata.component.base.entity.SysLoginLogout;
import com.bizdata.component.base.service.SysLoginLogoutService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class SysLoginLogoutServiceImpl implements SysLoginLogoutService {

	@Autowired
	private SysLoginLogoutMapper sysLoginLogoutMapper;

	@Override
	public void log(SysLoginLogout sysLoginLogout) {
		sysLoginLogoutMapper.insert(sysLoginLogout);
	}

	@Override
	public PageInfo<SysLoginLogout> selectSysLoginLogoutByCondByPage(int page,
			int rows) {
		PageHelper.startPage(page, rows);
		List<SysLoginLogout> list = sysLoginLogoutMapper
				.selectSysLoginLogoutByCondByPage();
		return new PageInfo<SysLoginLogout>(list);
	}

}
