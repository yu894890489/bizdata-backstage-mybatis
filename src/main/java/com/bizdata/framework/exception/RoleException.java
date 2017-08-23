package com.bizdata.framework.exception;

/**
 * 自定义角色异常类
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年6月24日 下午5:38:40<br/>
 *         描述：
 */
public class RoleException extends Exception {

	/**
	 * @fields serialVersionUID
	 */

	private static final long serialVersionUID = 1L;

	public RoleException() {
	}

	public RoleException(String massage) {
		super(massage);
	}
}
