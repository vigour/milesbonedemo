package com.milesbone.entity;

import com.milesbone.common.entity.BaseEntity;

public class Category extends BaseEntity {

	private String site;

	private String siteName;

	private Integer cid;

	private String cname;

	private String name;

	private String code;
	
	private String state;

	public Category() {
		super();
	}

	public Category(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}


	public Category(String site, String siteName, Integer cid, String cname, String name, String code, String state) {
		super();
		this.site = site;
		this.siteName = siteName;
		this.cid = cid;
		this.cname = cname;
		this.name = name;
		this.code = code;
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String toString() {
		return "Category [site=" + site + ", siteName=" + siteName + ", cid=" + cid + ", cname=" + cname + ", name="
				+ name + ", code=" + code + "]";
	}

}
