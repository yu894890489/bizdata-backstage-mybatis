package com.bizdata.component.base.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 角色
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 上午10:10:51<br/>
 *         描述：
 */
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8735020721380483032L;
	private Long id; // 编号
	private String role; // 角色标识 程序中判断使用,如"admin"
	private String description; // 角色描述,UI界面显示使用
	private List<SysResource> resourceList; // 拥有的资源

	public Role() {
	}

	public Role(String role, String description) {
		this.role = role;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Role role = (Role) o;

		if (id != null ? !id.equals(role.id) : role.id != null)
			return false;

		return true;
	}

	public List<SysResource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<SysResource> resourceList) {
		this.resourceList = resourceList;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Role{" + "id=" + id + ", role='" + role + '\'' + ", description='" + description + '\''
				+ ", resourceList=" + resourceList + '}';
	}
}
