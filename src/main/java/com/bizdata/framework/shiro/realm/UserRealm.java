package com.bizdata.framework.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.bizdata.commons.UserNameSessionIdMap;
import com.bizdata.component.base.entity.User;
import com.bizdata.component.base.service.UserService;

/**
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 下午3:46:14<br/>
 *         描述：用户认证授权相关，类似dataSource，负责用户账户验证，获取所持有的角色
 *         继承AuthorizingRealm（授权），其继承了AuthenticatingRealm
 *         （即身份验证），而且也间接继承了CachingRealm（带有缓存实现）
 */
public class UserRealm extends AuthorizingRealm {

	/**
	 * @Fields userService :注入userService
	 */
	@Autowired
	private UserService userService;

	/**
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年7月28日 上午10:25:50<br/>
	 *         描述：返回对应用户所拥有的角色
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取用户名
		String username = (String) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(userService.findRoles(username));
		authorizationInfo.setStringPermissions(userService.findPermissions(username));
		return authorizationInfo;
	}

	/**
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年7月28日 上午10:42:56<br/>
	 *         描述：根据用户名，执行相关的验证操作
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String username = (String) token.getPrincipal();

		User user = null;
		try {
			user = userService.selectUserDetailByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (user == null) {
			throw new UnknownAccountException();// 没找到帐号
		}

		// 0:为未锁定，1:为锁定！如果为1，则返回账号锁定
		if ("1".equals(user.getLocked())) {
			throw new LockedAccountException(); // 帐号锁定
		}

		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
		String salt = user.getCredentialsSalt();
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), // 用户名
				user.getPassword(), // 密码
				ByteSource.Util.bytes(salt), // 此处为盐值，salt=username+salt
				getName() // realm name
		);

		// 验证成功,设置用户名与session_id的映射
		UserNameSessionIdMap.put(user.getUsername(), SecurityUtils.getSubject().getSession().getId().toString());
		// 如果身份认证验证成功，返回一个AuthenticationInfo实现；
		return authenticationInfo;
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

}
