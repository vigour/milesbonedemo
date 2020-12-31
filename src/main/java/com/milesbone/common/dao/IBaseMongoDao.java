package com.milesbone.common.dao;

import java.util.List;

public interface IBaseMongoDao<T> extends IBaseDao<T>{

	List<T> findAll(String order);
	List<T> findByProp(String propName,Object value);
	List<T> findByProp(String propName,Object value,String order);
	List<T> findByProps(String[] propName,Object[] values);
	List<T> findByProps(String[] propName,Object[] values,String order);
	T uniqueByProp(String propName,Object value);
	T uniqueByProps(String[] propName,Object[] values);
	int countByCondition(String[] params,Object[] values);
}
