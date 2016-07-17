package com.milesbone.sysuer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

public class SysuserListener implements ApplicationListener<SysuserListenerEvent>{

	private static final  Logger logger = LoggerFactory.getLogger(SysuserListener.class);
	
	public void onApplicationEvent(SysuserListenerEvent event) {
		logger.error("userId:" + event.getSysuser().getUsername());
	}

}
