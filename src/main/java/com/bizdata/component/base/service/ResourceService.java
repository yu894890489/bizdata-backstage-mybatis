package com.bizdata.component.base.service;

import java.util.List;
import java.util.Set;

import com.bizdata.component.base.entity.SysResource;

/**
 * 资源接口
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 上午10:22:57<br/>
 *         描述：
 */
public interface ResourceService {

	/**
	 * 创建资源
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:23:17<br/>
	 *         描述：
	 * @param resource
	 * @return Resource
	 */
	public int insertResource(SysResource resource) throws Exception;

	/**
	 * 更新资源
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:24:00<br/>
	 *         描述：
	 * @param resource
	 * @return Resource
	 */
	public int updateResource(SysResource resource);

	/**
	 * 删除资源
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:24:23<br/>
	 *         描述：根据资源id删除资源
	 * @param resourceId
	 */
	public void deleteResource(Long resourceId) throws Exception;

	/**
	 * 查找资源列表
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:25:22<br/>
	 *         描述：
	 * @return List<Resource>
	 */
	List<SysResource> findAll();


	/**
	 * 根据用户权限得到菜单
	 * 
	 * @author 顾剑峰
	 * @description 描述这个方法做什么
	 * @param permissions
	 * @return
	 * @update 2015-4-13 下午3:26:11
	 */
	public String getUserMenus(Set<String> permissions);

	/**
	 * 根据父id获取孩子节点
	 * @param parent_id
	 * @return
	 */
	public List<SysResource> findChildens(Long parent_id);
	
	/**
	 * 根据id获取SysResource元素
	 * @param id
	 * @return
	 */
	public SysResource findSysResource(Long id);
}
