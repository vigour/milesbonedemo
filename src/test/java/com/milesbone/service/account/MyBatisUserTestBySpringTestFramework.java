package com.milesbone.service.account;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.milesbone.account.entity.User;
import com.milesbone.account.service.IUserService;
import com.milesbone.base.AbstractServiceTestCase;

public class MyBatisUserTestBySpringTestFramework extends AbstractServiceTestCase{
	
	//注入userService
    @Resource(name=IUserService.SERVICE_NAME)
    private IUserService userService;
    
    @Test
    @Rollback(false)
    public void testAddUser(){
        User user = new User();
        user.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setUserName("Bone");
        user.setUserBirthday(new Date());
        user.setUserSalary(10000D);
        userService.addUser(user);
    }
    
    @Test
    public void testGetUserById(){
        String userId = "b9b2b3392cee4962b9f080067f50740e";
        User user = userService.getUserById(userId);
        System.out.println(user.getUserName());
    }
    
    
    
    @Test
    public void testGetAllUser(){
        List<User> user = userService.getAllUser();
        for(User u : user){
        	System.out.println(u.getUserName());
        }
    }
}
