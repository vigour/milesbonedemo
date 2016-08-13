package com.milesbone.login.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author miles
 * @date 2016-08-10 下午7:08:46
 */
public class LoginMailListener implements UserListener {

	private static final Logger logger = LoggerFactory.getLogger(LoginMailListener.class);

	
	public LoginMailListener() {
		super();
	}
	
	
	public LoginMailListener(UserEventSource eventSource) {
		eventSource.registerListener(this);
	}

	@Override
	public void actionPerformed(UserEventSource eventSource) {
		logger.info("发送邮件监听器: " + this.getClass().getName() + "开始");
		LoginEventSource les = (LoginEventSource) eventSource;
		logger.info("Mail message :" + les.getUser().toString() +"");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			logger.error("Sleep has bean interupt");
			e.printStackTrace();
		}
		logger.info("发送邮件监听器结束");
	}

	@Override
	public void actionPerformed(UserEventSource eventSource, Object data) {
		// TODO Auto-generated method stub

	}

}
