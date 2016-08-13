package com.milesbone.login.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.milesbone.account.entity.User;

public class UserEventSource {

	private static final Logger logger = LoggerFactory.getLogger(UserEventSource.class);

	private List<UserListener> listeners = new ArrayList<UserListener>();
	
	
	public UserEventSource() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	public UserEventSource(List<UserListener> listeners) {
		super();
	}




	/**
	 * 注册监听
	 * 
	 * @param listener
	 */
	public void registerListener(UserListener listener) {
		logger.debug("注册监听开始,用户监听数为: " + listeners.size());
		if (listener == null) {
			logger.error("注册监听失败,监听器为空");
			throw new NullPointerException();
		}
		listeners.add(listener);
		logger.debug("注册监听完成,用户监听数为: " + listeners.size());
	}

	/**
	 * 移除监听
	 * 
	 * @param listener
	 */
	public void removeListener(UserListener listener) {
		logger.debug("移除监听开始,用户监听数为:" + listeners.size());
		int i = listeners.indexOf(listener);
		if (i > 0) {
			listeners.remove(i);
		}
		logger.debug("移除监听完成,用户监听数为:" + listeners.size());
	}

	/**
	 * 发布监听无参
	 */
	public void notifyListener() {
		logger.debug("同步发布监听消息");
		for (int i = 0; i < listeners.size(); i++) {
			UserListener listener = listeners.get(i);
			listener.actionPerformed(this);
		}
		logger.debug("同步发布监听消息完成");
	}

	/**
	 * 发布监听消息(带参)
	 * 
	 * @param data
	 */
	public void notifyListener(Object data) {
		logger.debug("同步发布监听消息");
		for (int i = 0; i < listeners.size(); i++) {
			UserListener listener = listeners.get(i);
			listener.actionPerformed(this, data);
		}
		logger.debug("同步发布监听消息完成");
	}

	/**
	 * 异步回调监听器
	 */
	public void asynNotifyListener() {
		logger.info("异步发布监听消息");
		int count = listeners.size();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < count; i++) {
			final UserListener listener = listeners.get(i);
			executorService.submit(new Callable<Object>() {
				public Object call() throws Exception {
					listener.actionPerformed(UserEventSource.this);
					return null;
				}
			});
		}
		logger.info("异步发布监听消息完成");
	}

	public static void main(String[] args) {
		User user = new User("admin", "管理员", new Date(), 10000.0);
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring/spring-listener.xml"); 
		LoginEventSource loginEventSource = (LoginEventSource) ctx.getBean("loginEventSource");
		loginEventSource.setUser(user);
//		loginEventSource.notifyListener();// 同步调用
		loginEventSource.asynNotifyListener();// 异步调用

		try {
			Thread.sleep(1000);
			logger.info("主线程执行完毕");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ctx.close();
	}

}
