package com.milesbone.common.dao;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T> {
	 /**
     * 插入
     * @param entity
     */
	void save(T entity);
	
	/**
	 * 修改
	 * @param entity
	 */
	void update(T entity);
	
	/**
     * 根据主键删除
     * @param entity
     * @return
     */
	void delete(Serializable... ids);
	
	/**
     * 查找一个
     * @param id
     * @return
     */
	T find(Serializable id);
	
	/**
	 * 查找全部
	 * @return
	 */
	List<T> findAll();
}
