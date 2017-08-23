package com.bizdata.component.base.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年4月10日 上午10:11:22<br/>
 *         描述：
 */
public class User extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -1709237487678134636L;
	/**
	 * id
	 */
	private Long id;
	/**
	 * 所属机构
	 */
	private Long organizationId;

	/**
	 * 所属机构名称
	 */
	private String organizationName;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 加密密码的盐
	 */
	private String salt;
	/**
	 * 拥有的角色列表
	 */
	private List<Role> roleList;
	/**
	 * 是否被锁定
	 */
	private String locked;

	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCredentialsSalt() {
		return username + salt;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User user = (User) o;

		if (id != null ? !id.equals(user.id) : user.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", organizationId=" + organizationId
				+ ", username='" + username + '\'' + ", password='" + password
				+ '\'' + ", salt='" + salt + '\'' + ", roleList=" + roleList
				+ ", locked=" + locked + '}';
	}
}
