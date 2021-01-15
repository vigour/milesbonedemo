package com.milesbone.category.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.milesbone.category.entity.CategoryTags;
@Service
public interface CategoryTagsService {

	 public List<CategoryTags> findByMac(String mac);
}
