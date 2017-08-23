package com.bizdata.framework.shiro.credentials;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 顾剑峰<br/>
 *         创建时间：2015年7月28日 下午5:09:22<br/>
 *         描述：继承自HashedCredentialsMatcher，用于凭证验证
 *         此处重写doCredentialsMatch，加入了密码输入次数验证功能
 */
public class RetryLimitHashedCredentialsMatcher extends
		HashedCredentialsMatcher {

	/**
	 * @Fields passwordRetryCache : 密码重试缓存
	 */
	private Cache<String, AtomicInteger> passwordRetryCache;

	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}

	/**
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年7月28日 下午5:15:53<br/>
	 *         描述：重写凭证验证方法 加入密码输入错误次数统计
	 * @param token
	 * @param info
	 * @return
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		String username = (String) token.getPrincipal();
		// retry count + 1
		AtomicInteger retryCount = passwordRetryCache.get(username);
		if (retryCount == null) {
			retryCount = new AtomicInteger(0);
			passwordRetryCache.put(username, retryCount);
		}
		if (retryCount.incrementAndGet() > 5) {
			// if retry count > 5 throw
			throw new ExcessiveAttemptsException();
		}

		// 调用原先的凭证验证方法，也就是盐值hash验证
		boolean matches = super.doCredentialsMatch(token, info);
		if (matches) {
			// clear retry count
			passwordRetryCache.remove(username);
		}
		return matches;
	}
}
