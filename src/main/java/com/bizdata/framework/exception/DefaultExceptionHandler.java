package com.bizdata.framework.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * springmvc全局异常捕获
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 下午2:41:10<br/>
 *         描述：
 */
@ControllerAdvice
public class DefaultExceptionHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultExceptionHandler.class);

	/**
	 * 没有权限 异常,跳转到没权限异常处理页面
	 * <p/>
	 * 后续根据不同的需求定制即可
	 */
	@ExceptionHandler({ UnauthorizedException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String processUnauthenticatedException(NativeWebRequest request,
			UnauthorizedException e) {
		return "redirect:/error/401";
	}

	/**
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年7月16日 下午3:00:53<br/>
	 *         描述：异常统一处理，对外隐藏异常细节
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ Exception.class })
	public String processException(NativeWebRequest request, Exception e) {
		// 将系统捕获的异常写入日志
		logger.error("系统异常捕获", e);

		// 统一跳转到错误页面，显示错误提示
		return "redirect:/error/500";
	}
}
