package com.milesbone.account.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.milesbone.account.entity.User;

@Repository(UserMapper.DAO_NAME)
public interface UserMapper {
    public static final String DAO_NAME = "userMapper";

	int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> getAllUser();
}