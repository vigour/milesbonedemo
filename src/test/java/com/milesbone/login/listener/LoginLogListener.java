package com.milesbone.login.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginLogListener implements UserListener{

	private static final Logger logger = LoggerFactory.getLogger(LoginLogListener.class);

	
	public LoginLogListener() {
		super();
	}

	//注册监听器
	public LoginLogListener(UserEventSource eventSource){
		eventSource.registerListener(this);
	}
	
	@Override
	public void actionPerformed(UserEventSource eventSource) {
		logger.info("日志记录监听器:" + this.getClass().getName() + "开始 ");
		LoginEventSource les = (LoginEventSource) eventSource;
		logger.info("Login message :" + les.getUser().toString() +"");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			logger.error("Sleep has bean interupt");
			e.printStackTrace();
		}
		logger.info("日志记录监听器结束");
	}

	@Override
	public void actionPerformed(UserEventSource eventSource, Object data) {
		//TODO
	}

}
