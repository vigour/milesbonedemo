package com.milesbone.sysuer.dao;

import org.springframework.stereotype.Repository;

import com.milesbone.sysuer.entity.SysuserRoles;

@Repository(SysuserRolesMapper.DAO_NAME)
public interface SysuserRolesMapper {
	
	static final String DAO_NAME = "sysuserRolesMapper";
	
    int deleteByPrimaryKey(Integer userRoleId);

    int insert(SysuserRoles record);

    int insertSelective(SysuserRoles record);

    SysuserRoles selectByPrimaryKey(Integer userRoleId);

    int updateByPrimaryKeySelective(SysuserRoles record);

    int updateByPrimaryKey(SysuserRoles record);
}