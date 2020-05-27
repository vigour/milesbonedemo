package com.milesbone.account.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class User {
    private String userId;

    private String userName;

    
    private Date userBirthday;

    private Double userSalary;

    public User() {
		super();
	}

    
	public User(String userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}



	public User(String userId, String userName, Date userBirthday, Double userSalary) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userBirthday = userBirthday;
		this.userSalary = userSalary;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
    
    @JSONField(format = "yyyy-MM-dd")  
    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public Double getUserSalary() {
        return userSalary;
    }

    public void setUserSalary(Double userSalary) {
        this.userSalary = userSalary;
    }

	public String toString() {
		return JSON.toJSONString(this);
	}
    
}