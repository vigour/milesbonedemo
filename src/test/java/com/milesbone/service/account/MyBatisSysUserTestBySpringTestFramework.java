package com.milesbone.service.account;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.milesbone.base.AbstractServiceTestCase;
import com.milesbone.sysuer.entity.Sysuser;
import com.milesbone.sysuer.service.ISysuserService;

public class MyBatisSysUserTestBySpringTestFramework extends AbstractServiceTestCase{
	
	//注入userService
    @Resource(name=ISysuserService.SERVICE_NAME)
    private ISysuserService sysuserService;
    
    @Test
    @Rollback(false)
    public void testAddUser(){
    	Sysuser user = new Sysuser();
    	user.setUsername("Bone");
    	user.setPassword("123456");
    	user.setEnabled(Byte.valueOf("1"));
    	sysuserService.addUser(user);
    }
    
    @Test
    public void testGetUserById(){
        String username = "miles";
        Sysuser user = sysuserService.getUsername(username);
        System.out.println(user.getUsername());
    }
    
    
    
    @Test
    public void testGetAllUser(){
        List<Sysuser> users = sysuserService.getAllUser();
        for(Sysuser u : users){
        	System.out.println(u.getUsername());
        }
    }
}
