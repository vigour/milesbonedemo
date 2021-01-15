package com.milesbone.category.mongodb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.milesbone.category.entity.CategoryTags;

@Repository
public interface CategoryTagsRepository extends MongoRepository<CategoryTags, String>,QueryByExampleExecutor<CategoryTags>{
	@Query(value="{'mac':?0}")
	public List<CategoryTags> findByMac(String mac);
}
