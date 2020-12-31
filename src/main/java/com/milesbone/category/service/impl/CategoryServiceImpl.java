package com.milesbone.category.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.milesbone.category.dao.CategoryMapper;
import com.milesbone.category.entity.Category;
import com.milesbone.category.service.ICategoryService;

@Service(ICategoryService.SERVICE_NAME)
public class CategoryServiceImpl implements ICategoryService{
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Resource(name = CategoryMapper.DAO_NAME)
	private CategoryMapper categoryMapper;

	public void addCategory(Category category) {
		logger.info("add start...");
		categoryMapper.insert(category);		
		logger.info("end");
	}

}
