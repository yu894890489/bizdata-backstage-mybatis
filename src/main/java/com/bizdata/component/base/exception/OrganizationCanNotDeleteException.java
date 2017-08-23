package com.bizdata.component.base.exception;

/**
 * 自定义组织机构无法删除异常
 * @author 顾剑峰
 *
 */
public class OrganizationCanNotDeleteException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OrganizationCanNotDeleteException(String msg) {
		super(msg);
	}
}
