package com.milesbone.category.service;

import com.milesbone.category.entity.Category;

public interface ICategoryService {

	public static final String SERVICE_NAME = "categoryService";
	
	public void addCategory(Category category);
}
