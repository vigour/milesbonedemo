package com.milesbone.account.service;

import java.util.List;

import com.milesbone.account.entity.User;

public interface IUserService {
	
	public static final String SERVICE_NAME = "userService";

	/**
     * 添加用户
     * @param user
     */
    void addUser(User user);
    
    /**
     * 根据用户id获取用户
     * @param userId
     * @return
     */
    User getUserById(String userId);
    
    
    /**
     * 获取所有用户信息
     * @return
     */
    List<User> getAllUser();
}
