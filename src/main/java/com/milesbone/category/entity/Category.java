package com.milesbone.category.entity;

public class Category {
    private Integer fskId;

    private String fskSite;

    private String fskSiteName;

    private Integer fskCid;

    private String fskCname;

    private String fskCategoryName;

    private String fskCategoryCode;

    private String fskStates;

    public Category() {
		super();
	}

	public Category(String fskSite, String fskSiteName, Integer fskCid, String fskCname, String fskCategoryName,
			String fskCategoryCode, String fskStates) {
		super();
		this.fskSite = fskSite;
		this.fskSiteName = fskSiteName;
		this.fskCid = fskCid;
		this.fskCname = fskCname;
		this.fskCategoryName = fskCategoryName;
		this.fskCategoryCode = fskCategoryCode;
		this.fskStates = fskStates;
	}

	public Integer getFskId() {
        return fskId;
    }

    public void setFskId(Integer fskId) {
        this.fskId = fskId;
    }

    public String getFskSite() {
        return fskSite;
    }

    public void setFskSite(String fskSite) {
        this.fskSite = fskSite == null ? null : fskSite.trim();
    }

    public String getFskSiteName() {
        return fskSiteName;
    }

    public void setFskSiteName(String fskSiteName) {
        this.fskSiteName = fskSiteName == null ? null : fskSiteName.trim();
    }

    public Integer getFskCid() {
        return fskCid;
    }

    public void setFskCid(Integer fskCid) {
        this.fskCid = fskCid;
    }

    public String getFskCname() {
        return fskCname;
    }

    public void setFskCname(String fskCname) {
        this.fskCname = fskCname == null ? null : fskCname.trim();
    }

    public String getFskCategoryName() {
        return fskCategoryName;
    }

    public void setFskCategoryName(String fskCategoryName) {
        this.fskCategoryName = fskCategoryName == null ? null : fskCategoryName.trim();
    }

    public String getFskCategoryCode() {
        return fskCategoryCode;
    }

    public void setFskCategoryCode(String fskCategoryCode) {
        this.fskCategoryCode = fskCategoryCode == null ? null : fskCategoryCode.trim();
    }

    public String getFskStates() {
        return fskStates;
    }

    public void setFskStates(String fskStates) {
        this.fskStates = fskStates == null ? null : fskStates.trim();
    }

	public String toString() {
		return "Category [fskId=" + fskId + ", fskSite=" + fskSite + ", fskSiteName=" + fskSiteName + ", fskCid="
				+ fskCid + ", fskCname=" + fskCname + ", fskCategoryName=" + fskCategoryName + ", fskCategoryCode="
				+ fskCategoryCode + ", fskStates=" + fskStates + "]";
	}
    
    
}