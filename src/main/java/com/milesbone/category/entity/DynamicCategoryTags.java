package com.milesbone.category.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="mg_vod_detail")
public class DynamicCategoryTags extends BaseMongoEntiry{

	@Field(value = "mac")
	private String mac;
	
	@Field(value = "cat_records")
	private List<CategoryRecords> catrecords;

	public String toString() {
		return "DynamicCategoryTags [mac=" + mac + ", catrecords=" + catrecords + "]";
	} 
}
