package com.milesbone.category.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milesbone.category.entity.CategoryTags;
import com.milesbone.category.mongodb.CategoryTagsRepository;
import com.milesbone.category.service.CategoryTagsService;

@Service(value="categoryTagsService")
public class CategoryTagsMongoServiceImpl implements CategoryTagsService {

	@Autowired
	private CategoryTagsRepository categoryTagsDao;
	
	public List<CategoryTags> findByMac(String mac) {
//		CategoryTags tags = new CategoryTags();
//		tags.setMac(mac);
//		
//		//创建匹配器，即如何使用查询条件
//	    ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
//	            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
//	            .withIgnoreCase(true) //改变默认大小写忽略方式：忽略大小写
//	            .withMatcher("mac", ExampleMatcher.GenericPropertyMatchers.contains()) //标题采用“包含匹配”的方式查询
//	            .withIgnorePaths("pageNum", "pageSize");  //忽略属性，不参与查询
//	    Example<CategoryTags> example = Example.of(tags, matcher);
		List<CategoryTags> result = this.categoryTagsDao.findByMac(mac);
		return result;
	}
	
	
}
