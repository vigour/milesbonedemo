package com.milesbone.category.mongodb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.milesbone.category.entity.Category;

public interface CategoryDao extends MongoRepository<Category, String> , QueryByExampleExecutor<Category>{

	@Query(value="{'mac':?0}")
	public List<Category> findByMac(String mac);
}
