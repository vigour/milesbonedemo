package com.milesbone.login.listener;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.milesbone.account.entity.User;
import com.milesbone.base.AbstractServiceTestCase;

public class TestUserListener extends AbstractServiceTestCase{

	private LoginEventSource loginEventSource;
	
	
	private static final Logger logger = LoggerFactory.getLogger(TestUserListener.class);
	
	
	
	public LoginEventSource getLoginEventSource() {
		return loginEventSource;
	}

	@Resource(name = "loginEventSource")
	public void setLoginEventSource(LoginEventSource loginEventSource) {
		this.loginEventSource = loginEventSource;
	}



	@Test
	public void testUserListener(){
		String a =null;
		logger.debug("ceshi :{}", a);
		
		
		User user = new User("admin", "管理员", new Date(), 10000.0);
		loginEventSource.setUser(user);
//		loginEventSource.notifyListener();
		loginEventSource.asynNotifyListener();  
        
        try {
        	logger.info("线程继续执行");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
