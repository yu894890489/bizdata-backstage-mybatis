package com.bizdata.component.base.service;

import com.bizdata.component.base.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

/**
 * 用户角色接口
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 上午10:35:14<br/>
 *         描述：
 */
public interface UserService {

	/**
	 * 创建用户
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:35:41<br/>
	 *         描述：
	 * @param user
	 * @param roles
	 * @return
	 */
	public long insertUser(User user, String[] roles);

	/**
	 * 更新用户
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:35:54<br/>
	 *         描述：
	 * @param user
	 * @param roles
	 * @return
	 */
	public int updateUser(User user, String[] roles);

	/**
	 * 删除用户
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:36:07<br/>
	 *         描述：
	 * @param userId
	 */
	public void deleteUser(Long userId);

	/**
	 * 更改密码
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:36:31<br/>
	 *         描述：
	 * @param userId
	 * @param newPassword
	 */
	public void changePassword(Long userId, String newPassword);

	/**
	 * 根据用户id查找用户
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:36:38<br/>
	 *         描述：
	 * @param userId
	 * @return
	 */
	User findOne(Long userId);

	/**
	 * 查找所有用户
	 * 
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年4月10日 上午10:36:52<br/>
	 *         描述：
	 * @return
	 */
	List<User> findAll();

	/**
	 * @author 顾剑峰
	 * @description 根据用户名查找用户详细信息，包括角色信息
	 * @param username
	 * @return User
	 * @update 2015-5-8 上午9:02:46
	 */
	public User selectUserDetailByUsername(String username);

	/**
	 * 根据用户名查找其角色字符串
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findRoles(String username);

	/**
	 * 根据用户名查找其权限字符串
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findPermissions(String username);

	/**
	 * @author 顾剑峰
	 * @description 根据条件分页查询用户信息
	 * @param user
	 * @return PageMyBatis<User>
	 * @update 2015-5-4 上午11:15:22
	 */
	public PageInfo<User> selectUserByCondByPage(int page, int rows, User user);

}
