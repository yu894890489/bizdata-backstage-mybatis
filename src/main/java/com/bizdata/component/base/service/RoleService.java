package com.bizdata.component.base.service;

import java.util.List;
import java.util.Set;

import com.bizdata.component.base.entity.Role;
import com.bizdata.component.base.entity.RoleResource;
import com.bizdata.framework.exception.RoleException;

/**
 * 角色接口
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 上午10:32:36<br/>
 *         描述：
 */
public interface RoleService {

	/**
	 * 新建角色
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:32:56<br/>
	 *         描述：
	 * @param role
	 * @return
	 */
	public int insertRole(Role role);

	/**
	 * 更新角色
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:33:05<br/>
	 *         描述：
	 * @param role
	 * @return
	 */
	public int updateRole(Role role);

	/**
	 * 删除角色
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:33:13<br/>
	 *         描述：
	 * @param roleId
	 */
	public void deleteRole(Long roleId);

	/**
	 * 根据角色ID查找角色
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:33:21<br/>
	 *         描述：
	 * @param roleId
	 * @return
	 */
	public Role findOne(Long roleId);

	/**
	 * 查找所有
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:33:56<br/>
	 *         描述：
	 * @return
	 */
	public List<Role> findAll();

	/**
	 * 根据角色得到权限字符串列表
	 * 
	 * @param list
	 * @return
	 */
	Set<String> findPermissions(List<Role> list);

	/**
	 * 在角色资源关系表中，插入新的关系记录
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月8日 上午11:08:27<br/>
	 *         描述：
	 * @param role_id
	 * @param resource_id
	 */
	public void addRelation(RoleResource roleResource);

	/**
	 * 在角色资源关系表中，删除一条关系记录
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年5月8日 下午12:16:32<br/>
	 *         描述：
	 * @param roleResource
	 * @throws RoleException
	 */
	public void disassociate(RoleResource roleResource)
			throws RoleException;
}
