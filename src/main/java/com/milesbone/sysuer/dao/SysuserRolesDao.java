package com.milesbone.sysuer.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.milesbone.sysuer.entity.SysuserRoles;

public interface SysuserRolesDao extends MongoRepository<SysuserRoles, String>{

}
