package com.milesbone.sysuer.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.milesbone.sysuer.entity.Sysuser;
import com.milesbone.sysuer.entity.SysuserRoles;

public interface SysUserDao extends MongoRepository<Sysuser, String>{
	
	public Sysuser findByRole(SysuserRoles sysuserRoles);	
	
	@Query(value="{'sysuserRoles.$id': ?0 }")
	public Sysuser findByRoleId(ObjectId id);

}
