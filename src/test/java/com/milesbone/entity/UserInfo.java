package com.milesbone.entity;

public class UserInfo {
	
	
	
	public UserInfo() {
	}
	

	public UserInfo(String usercode, String password) {
		super();
		this.usercode = usercode;
		this.password = password;
	}



	private String usercode;
	
	private String password;

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "UserInfo [usercode=" + usercode + ", password=" + password + "]";
	}
	
	

}
