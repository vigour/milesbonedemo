package com.milesbone.category.dao;

import org.springframework.stereotype.Repository;

import com.milesbone.category.entity.Category;

@Repository(CategoryMapper.DAO_NAME)
public interface CategoryMapper {
	public static final String DAO_NAME = "categoryMapper";
	
    int deleteByPrimaryKey(Integer fskId);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer fskId);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}