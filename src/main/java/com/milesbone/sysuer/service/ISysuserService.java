package com.milesbone.sysuer.service;

import java.util.List;

import com.milesbone.sysuer.entity.Sysuser;

public interface ISysuserService {
	
	public static final String SERVICE_NAME = "sysUserService";

	/**
     * 添加用户
     * @param user
     */
    void addUser(Sysuser user);
    
    /**
     * 根据用户id获取用户
     * @param username
     * @return
     */
    Sysuser getUsername(String username);
    
    
    /**
     * 获取所有用户信息
     * @return
     */
    List<Sysuser> getAllUser();
}
