package com.milesbone.login.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.milesbone.account.entity.User;


/**
 * 监听器实体类
 * @author miles
 * @date 2016-08-10 下午6:45:10
 */
public class LoginEventSource extends UserEventSource {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginEventSource.class);
	
	
	
	
	public LoginEventSource() {
		super();
	}
	
	

	public LoginEventSource(User user) {
		super();
		this.user = user;
	}



	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	//回调函数,当验证通过运行该方法
	public String loginSuccess(){
		logger.info(this.getClass().getName() + " print: Login Success");
		return "SUCCESS";
	}
	
	//回调函数,当验证不通过运行该方法
	public String loginFail(){
		logger.info(this.getClass().getName() + " print: Login Fail");
		return "Fail";
	}
	
	
}
