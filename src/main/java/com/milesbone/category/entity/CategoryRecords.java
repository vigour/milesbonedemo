package com.milesbone.category.entity;

import java.util.List;

public class CategoryRecords {

	private String cid;
	
	private List<String> cats;
	
	private List<String> catCodes;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public List<String> getCats() {
		return cats;
	}

	public void setCats(List<String> cats) {
		this.cats = cats;
	}

	
	public List<String> getCatCodes() {
		return catCodes;
	}

	public void setCatCodes(List<String> catCodes) {
		this.catCodes = catCodes;
	}

	public String toString() {
		return "CategoryRecords [cid=" + cid + ", cats=" + cats + ", catCodes=" + catCodes + "]";
	}
	
}
