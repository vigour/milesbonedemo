package com.milesbone.account.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

public class UserListener implements ApplicationListener<UserListenerEvent>{

	private static final  Logger logger = LoggerFactory.getLogger(UserListener.class);
	
	public void onApplicationEvent(UserListenerEvent event) {
		logger.error("userId:" + event.getUser().getUserId());
	}

}
