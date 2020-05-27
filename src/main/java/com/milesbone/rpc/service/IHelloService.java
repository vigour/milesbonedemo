package com.milesbone.rpc.service;

import java.util.List;

import com.milesbone.account.entity.User;

public interface IHelloService {

	String hello(String name);
	
	User getUser(String name);
	
	List<User> getUsers(int size);
	
	User updateUser(User user);
	
}
