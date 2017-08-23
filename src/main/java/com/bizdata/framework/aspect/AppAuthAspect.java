package com.bizdata.framework.aspect;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * App接口鉴权切面
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Aspect
public class AppAuthAspect {

	@Pointcut("@annotation(com.bizdata.framework.annotation.AppAuth)")
	public void appAuth() {
	}

	@Around("appAuth()")
	public Object doAppAuth(ProceedingJoinPoint pjp) {
		System.out.println("拦截到方法:" + pjp.getSignature().getName());
		// 获取参数
		Object[] args = pjp.getArgs();
		HttpServletRequest request = (HttpServletRequest) args[0];
		HttpServletResponse response = (HttpServletResponse) args[1];
		System.out.println((String) args[2]);
		// 验证head判断token鉴权是否通过
		boolean state = judgeHeader(request);
		Object retVal = null;
		if (state) {
			System.out.println("鉴权成功...");
			// 如果鉴权成功，继续执行下面方法
			try {
				retVal = pjp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("鉴权失败...");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = null;
			// 如果失败,直接返回鉴权失败
			try {
				out = response.getWriter();
				out.append("error");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (null != out)
					out.close();
			}
		}
		System.out.println("拦截方法之后执行...");
		return retVal;
	}

	/**
	 * 对客户端传递过来的token进行验证
	 *
	 * @param request
	 */
	private boolean judgeHeader(HttpServletRequest request) {
		String token = request.getHeader("token");
		String timestamp = request.getHeader("timestamp");
		boolean state = false;
		if ("123456".equals(token) && "654321".equals(timestamp)) {
			state = true;
		}
		return state;
	}

}
