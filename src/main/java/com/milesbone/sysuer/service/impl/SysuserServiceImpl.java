package com.milesbone.sysuer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import com.milesbone.sysuer.dao.SysuserMapper;
import com.milesbone.sysuer.entity.Sysuser;
import com.milesbone.sysuer.listener.SysuserListenerEvent;
import com.milesbone.sysuer.service.ISysuserService;

@Service(ISysuserService.SERVICE_NAME)
public class SysuserServiceImpl implements ISysuserService,ApplicationEventPublisherAware {
	
	private static final Logger logger = LoggerFactory.getLogger(SysuserServiceImpl.class);

	private ApplicationEventPublisher publisher;
	
	@Resource(name=SysuserMapper.DAO_NAME)
	private SysuserMapper sysuserMapper;
	
	@Override
	public void addUser(Sysuser user) {
		sysuserMapper.insert(user);
		logger.debug("fire BookingCreatedEvent");
		//publishing the event here
        publisher.publishEvent(new SysuserListenerEvent(this, user));
	}

	
	@Override
	public Sysuser getUsername(String username) {
		return sysuserMapper.selectByPrimaryKey(username);
	}

	@Override
	public List<Sysuser> getAllUser() {
		return sysuserMapper.getAllUser();
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}


}
