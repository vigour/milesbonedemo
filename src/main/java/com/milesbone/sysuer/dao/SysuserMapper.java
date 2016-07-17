package com.milesbone.sysuer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.milesbone.sysuer.entity.Sysuser;


@Repository(SysuserMapper.DAO_NAME)
public interface SysuserMapper {
	
	public static final String DAO_NAME = "sysuserMapper";
	
    int deleteByPrimaryKey(String username);

    int insert(Sysuser record);

    int insertSelective(Sysuser record);

    Sysuser selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(Sysuser record);

    int updateByPrimaryKey(Sysuser record);
    
    List<Sysuser> getAllUser();
}