package com.bizdata.framework.listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import com.bizdata.component.base.entity.SysLoginLogout;
import com.bizdata.component.base.service.SysLoginLogoutService;

/**
 * @author 顾剑峰<br/>
 *         创建时间：2015年7月21日 下午6:32:27<br/>
 *         描述：用户session，拓展SessionListenerAdapter
 */
public class UserSessionListener extends SessionListenerAdapter {

	@Autowired
	private SysLoginLogoutService sysLoginLogoutService;

	/**
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年7月21日 下午6:32:56<br/>
	 *         描述：当session超时，进行处理,写入用户为超时退出
	 * @param session
	 */
	@Override
	public void onExpiration(Session session){
		if(null!=session.getAttribute("username")&&null!=session.getAttribute("ip")){
			String username = session.getAttribute("username").toString();
			String ip = session.getAttribute("ip").toString();
			logTimeOut(username, ip);
		}
	}

	/**
	 * @author 顾剑峰<br/>
	 *         创建时间：2015年7月21日 下午3:44:08<br/>
	 *         描述：执行session超时退出日志记录
	 */
	private void logTimeOut(String username, String ip) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 时间
		Date date = new Date();
		// 格式化时间
		String dateString = dateFormat.format(date);
		// 操作内容
		String content = username + " 于 " + dateString + " session超时退出系统 ";
		// 日志类型
		String type = "timeout";

		// 封装对象
		SysLoginLogout sysLoginLogout = new SysLoginLogout();
		sysLoginLogout.setUsername(username);
		sysLoginLogout.setContent(content);
		sysLoginLogout.setDate(date);
		sysLoginLogout.setType(type);
		sysLoginLogout.setIp(ip);
		// 执行入库
		sysLoginLogoutService.log(sysLoginLogout);
	}

}
