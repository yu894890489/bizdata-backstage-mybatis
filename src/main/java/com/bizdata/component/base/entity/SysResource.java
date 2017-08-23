package com.bizdata.component.base.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SysResource implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6256300436336646868L;

	private Long id;

	private String name;

	/**
	 * 资源类型
	 */
	private ResourceType type = ResourceType.menu;

	private String url;

	private String permission;

	private String icon;

	private Boolean isInitialized=false;

	private Integer sortNum;

	private Long parent;

	private Boolean expanded=true;

	private Boolean loaded=true;

	private String level;

	private Boolean isleaf=true;
	
	/**
	 * 如果是column 栏目类型，则有包含的menu 菜单
	 */
	private List<SysResource> menus = new ArrayList<SysResource>();

	/**
	 * 枚举类型
	 * 
	 * @author 顾剑峰
	 *
	 */
	public static enum ResourceType {
		root("资源根节点"),column("栏目"), menu("菜单"), action("动作");

		private final String info;

		private ResourceType(String info) {
			this.info = info;
		}

		public String getInfo() {
			return info;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission == null ? null : permission.trim();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

	public Boolean getIsInitialized() {
		return isInitialized;
	}

	public void setIsInitialized(Boolean isInitialized) {
		this.isInitialized = isInitialized;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public Boolean getLoaded() {
		return loaded;
	}

	public void setLoaded(Boolean loaded) {
		this.loaded = loaded;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level == null ? null : level.trim();
	}

	public Boolean getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(Boolean isleaf) {
		this.isleaf = isleaf;
	}

	public List<SysResource> getMenus() {
		return menus;
	}

	public void setMenus(List<SysResource> menus) {
		this.menus = menus;
	}

	public boolean isRootNode() {
		return parent == 0;
	}
	
	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}
}