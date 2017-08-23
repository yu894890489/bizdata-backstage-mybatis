package com.bizdata.component.base.entity;

/**
 * 
 * @author 顾剑峰<br/>
 *         创建时间：2015年11月13日 下午11:47:38<br/>
 *         描述：用于jqgrid前端传递过来的数据封装，提供给一般entity进行继承
 */
public class BaseEntity {
	/**
	 * 当前页数 jqgrid传递过来
	 */
	private int page;

	/**
	 * 每页显示记录数 jqgrid传递过来
	 */
	private int rows;

	/**
	 * 排序字段名 jqgrid传递过来
	 */
	private String sidx;

	/**
	 * 排序方式 asc(默认),desc
	 * 
	 */
	private String sord;

	/**
	 * 是否是查询操作bool
	 */
	private boolean search;

	/**
	 * 查询字段jqgrid传递过来
	 */
	private String searchField;

	/**
	 * 查询条件 jqgrid传递过来
	 */
	private String searchString;

	/**
	 * 查询操作jqgrid传递过来
	 */
	private String searchOper;

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		String opt = "";
		// TODO 此处搜索条件,用到时再进行添加
		if (null != searchOper) {
			switch (searchOper) {
			case "eq":// 等于操作
				opt = "=";
				break;
			case "ne":// 不等于
				opt = "<>";
				break;
			case "lt":// 小于
				opt = "<";
				break;
			case "le":// 小于等于
				opt = "<=";
				break;
			case "gt":// 大于
				opt = ">";
				break;
			case "ge":// 大于等于
				opt = ">=";
				break;
			case "bw":// 以xxx%开头
				opt = "like";
				break;
			case "bn":// 不以xxx%开头
				opt = "not like";
				break;
			case "in":// 在
				opt = "in";
				break;
			case "ni":// 不在
				opt = "not in";
				break;
			case "ew":// 以%xxx结尾
				opt = "like";
				break;
			case "en":// 不以%xxx结尾
				opt = "not like";
				break;
			case "cn":// 包含
				opt = "like";
				break;
			case "nc":// 不包含
				opt = "not like";
				break;
			case "nu":
				opt="is null";
				break;
			case "nn":
				opt="is not null";
				break;
			default:
				opt = "";
				break;
			}
		}
		return opt;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

}
