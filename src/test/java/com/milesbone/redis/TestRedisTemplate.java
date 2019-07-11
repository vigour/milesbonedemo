package com.milesbone.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.milesbone.entity.UserInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring/spring-redis.xml")
public class TestRedisTemplate {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Test
	public void test() {
		System.out.println(redisTemplate);
	}
	
	@Test
	public void testKeyValue() {
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		String key = "name";
		JSONArray array = new JSONArray();
		
		JSONObject json = new JSONObject();
		json.put("aaa", "aaa.value");
		json.put("bbb", "bbb.value");
		json.put("ccc", "ccc.value");
		json.put("ddd", "ddd.value");
		json.put("eee", 100);
		array.add(json);
		
		json = new JSONObject();
		json.put("aaa", "aaa2.value");
		json.put("bbb", "bbb2.value");
		json.put("ddd", "ddd2.value");
		json.put("eee", 200);
		array.add(json);
		
		json = new JSONObject();
		json.put("aaa", "aaa3.value");
		json.put("bbb", "bbb3.value");
		json.put("ccc", "ccc3.value");
		json.put("ddd", "ddd3.value");
		json.put("eee", 300);
		array.add(json);
		 //存入key-value
        operations.set("name","miles");
//        long start = System.currentTimeMillis();
        //根据key取出Value
        Object name = operations.get("name");
        System.out.println("name:"+name);
        //追加
//        operations.append("name","is man");
        //获得并修改
//        operations.getAndSet("name","zhangsan-1");
        operations.getAndSet("name",  array);
        Object name1 = operations.get("name");
        System.out.println("修改后："+name1);
        operations.getAndSet("name",  new JSONObject());
        name1 = operations.get("name");
        System.out.println("修改json后："+name1);
        
        
        UserInfo ui = new UserInfo("u001", "123456");
        String jo =  JSON.toJSONString(ui);
        System.out.println(jo);
        operations.getAndSet("name",  jo);
        name1 = operations.get("name");
        System.out.println("修改json后："+name1);
	}
}
