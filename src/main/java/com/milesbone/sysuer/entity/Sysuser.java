package com.milesbone.sysuer.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.milesbone.common.entity.BaseEntity;

@Document(collection = "xx_user")
public class Sysuser extends BaseEntity{
    private String username;

    private String password;

    private Byte enabled;
    
    @DBRef
    private SysuserRoles sysuserRoles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

	public SysuserRoles getSysuserRoles() {
		return sysuserRoles;
	}

	public void setSysuserRoles(SysuserRoles sysuserRoles) {
		this.sysuserRoles = sysuserRoles;
	}
}