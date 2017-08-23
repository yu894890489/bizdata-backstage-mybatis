package com.bizdata.component.base.service;

import com.bizdata.component.base.entity.SysLoginLogout;
import com.github.pagehelper.PageInfo;

/**
 * @author 顾剑峰<br/>
 *         创建时间：2015年7月20日 下午3:04:47<br/>
 *         描述：执行登录登出操作日志记录
 */
public interface SysLoginLogoutService {
	/**
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年7月20日 下午3:05:51<br/>
	 *         描述：执行日志记录
	 */
	public void log(SysLoginLogout sysLoginLogout);

	/**
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年7月22日 上午11:04:02<br/>
	 *         描述：根据条件，分页查询出SysLoginLogout list数据
	 * @param pageC
	 * @param sysLoginLogout
	 * @return
	 */
	public PageInfo<SysLoginLogout> selectSysLoginLogoutByCondByPage(int page,
			int rows);

}
