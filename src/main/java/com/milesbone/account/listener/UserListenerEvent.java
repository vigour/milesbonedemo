package com.milesbone.account.listener;

import org.springframework.context.ApplicationEvent;

import com.milesbone.account.entity.User;

public class UserListenerEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2615513235682198836L;

	private User user;
	
	public UserListenerEvent(Object source) {
		super(source);
	}

	
	
	public UserListenerEvent(Object source, User user) {
		super(source);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
