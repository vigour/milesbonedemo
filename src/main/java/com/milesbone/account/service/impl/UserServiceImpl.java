package com.milesbone.account.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import com.milesbone.account.dao.UserMapper;
import com.milesbone.account.entity.User;
import com.milesbone.account.listener.UserListenerEvent;
import com.milesbone.account.service.IUserService;


@Service(IUserService.SERVICE_NAME)
public class UserServiceImpl implements IUserService,ApplicationEventPublisherAware {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class); 
	
	private ApplicationEventPublisher publisher;
	
	@Resource(name=UserMapper.DAO_NAME)
	private UserMapper userMapper;

	public void addUser(User user) {
		userMapper.insert(user);
		logger.debug("fire BookingCreatedEvent");
		//publishing the event here
        publisher.publishEvent(new UserListenerEvent(this, user));
	}

	public User getUserById(String userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	public List<User> getAllUser() {
		return userMapper.getAllUser();
	}


	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}

	 
}
