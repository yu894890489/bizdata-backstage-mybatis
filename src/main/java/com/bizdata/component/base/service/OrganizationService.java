package com.bizdata.component.base.service;

import java.util.List;

import com.bizdata.component.base.entity.SysOrganization;

/**
 * 组织机构接口
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 上午10:28:06<br/>
 *         描述：
 */
public interface OrganizationService {

	/**
	 * 创建组织机构
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:28:18<br/>
	 *         描述：
	 * @param organization
	 * @return
	 */
	public int insertOrganization(SysOrganization sysOrganization);

	/**
	 * 更新组织机构
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:28:45<br/>
	 *         描述：
	 * @param organization
	 * @return
	 */
	public int updateOrganization(SysOrganization sysOrganization);

	/**
	 * 删除组织机构
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:28:55<br/>
	 *         描述：
	 * @param organizationId
	 */
	public void deleteOrganization(Long organizationId);

	/**
	 * 查找组织机构
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:29:25<br/>
	 *         描述：根据组织机构id查找组织机构
	 * @param organizationId
	 * @return
	 */
	SysOrganization findOne(Long organizationId);

	/**
	 * 查找组织机构列表
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:30:11<br/>
	 *         描述：
	 * @return
	 */
	List<SysOrganization> findAll();
	
	/**
	 * 根据条件查询
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:30:59<br/>
	 *         描述：
	 * @param excludeOraganization
	 * @return
	 */
	List<SysOrganization> selectByCnd(SysOrganization excludeOraganization);

	/**
	 * @author 顾剑峰
	 * @description 获取直系子节点个数
	 * @param id
	 * @return int
	 * @update 2015-5-7 上午10:00:08
	 */
	int selectChildrenCountById(Long id);
	
	/**
	 * 根据父id找到孩子们
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月14日 下午3:52:47<br/>
	 *         描述：
	 * @param parent_id
	 * @return
	 */
	public List<SysOrganization> findChildens(Long parent_id);
	
	/**
	 * 判断组织结构是否可以删除(规则：如果部门下存在用户则无法删除)
	 */
	public boolean canDeleteOrganization(Long id);
}
