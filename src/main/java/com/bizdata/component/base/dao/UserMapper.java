package com.bizdata.component.base.dao;

import java.util.List;

import com.bizdata.component.base.entity.User;

public interface UserMapper {
	int deleteByPrimaryKey(Long id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Long id);

	/**
	 * @author 顾剑峰
	 * @description 根据用户名查询用户信息,同时查询出用户角色id列表 List<Long> roleidList
	 * @param username
	 * @return User
	 * @update 2015-4-27 下午4:14:18
	 */
	User selectUserDetailByUsername(String username);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	List<User> selectAll();

	/**
	 * @author 顾剑峰
	 * @description 根据搜索或者排序条件分页查询用户信息
	 * @param pageC
	 * @param user
	 * @return PageMyBatis<User>
	 * @update 2015-5-27 下午1:29:22
	 */
	List<User> selectUserByCondByPage(User user);

	/**
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年11月29日 下午10:34:55<br/>
	 *         描述：根据组织机构id查询出该组织机构id下存在的用户数量
	 * @param organizationId
	 * @return
	 */
	int seleteCountByOrganizationId(Long organizationId);
}