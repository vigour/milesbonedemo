package com.milesbone.mongodb.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.milesbone.base.AbstractServiceTestCase;
import com.milesbone.category.entity.Category;
import com.milesbone.category.entity.CategoryTags;
import com.milesbone.category.mongodb.CategoryDao;
import com.milesbone.category.service.CategoryTagsService;

public class CategoryMongoServiceTest extends AbstractServiceTestCase{

	@Autowired
	private CategoryDao categoryDao;
	
	@Resource(name="categoryTagsService")
	private CategoryTagsService categoryTagsService;
	

	@Test
	public void query() {
		String mac = "18:89:A0:26:02:E2";
		List<CategoryTags> tags = categoryTagsService.findByMac(mac);
		System.out.println(tags);
//		Assert.assertEquals("admin", user.getUsername());
	}
	@Test
	public void findByMacTest(){
		String mac = "80:38:96:66:57:21";
		List<Category> categories = categoryDao.findByMac(mac);
		System.out.println( categories);
	}
}
