package com.milesbone.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:config/spring/spring-redis-cluster.xml")
public class TestRedisClusterTemplate {
	
	/**
	 * 花絮缓存名称
	 */
	public static final String CACHE_NAME_VIDEO_FEATURE_PREFIX = "cachevideofeature";

	/**
	 * 缓存影片属性名称
	 */
	public static final String CACHE_NAME_PROPERTY_PREFIX = "cacheproperty" ;
	
	
	/**
	 * 缓存列表属性名称
	 */
	public static final String CACHE_NAME_VIDEO_LIST_PREFIX = "cachevideolist" ;
	
	/**
	 * 缓存影片库里所有的影片集合
	 */
	public static final String CACHE_NAME_SITE_VIDEO_LIST_PREFIX = "cachesitevideolist" ;
	
	/**
	 * 缓存影片库里所有的影片集合
	 */
	public static final String CACHE_NAME_SITE_VIP_VIDEO_LIST_PREFIX = "cachesitevipvideolist" ;
	
	/**
	 * 
	 */
	public static final String CACHE_NAME_SITE_VIP_VIDEO_LIST_PARTTERN = "cachesitevipvideolist:*_412";
	
	
	/**
	 * 缓存影片详情名称
	 */
	public static final String CACHE_NAME_VIDEO_INFO_PREFIX = "cachevideoinfo";
	
	/**
	 * 缓存影片详情名称
	 */
	public static final String CACHE_NAME_VIDEO_ALBUM_PREFIX = "cachevideoalbum";
	
	/**
	 * 影片评论缓存名称
	 */
	public static final String CACHE_NAME_VIDEO_COMMENT_PREFIX = "cachevideocomment";
	
	/**
	 * 集数列表缓存名称
	 */
	public static final String CACHE_NAME_VOLUME_LIST_PREFIX = "cachevolume";
	
	/**
	 * 分集剧情缓存名称
	 */
	public static final String CACHE_NAME_VOLUME_STORIES_PREFIX = "cachevolumestorys";
	
	
	/**
	 * redis缓存名称分隔符
	 */
	public static final String REDIS_CHAR_KEY  = ":";
	
	/**
	 * rediskey分隔符
	 */
	private static final String KEY_SPLIT_CHAR = "_";
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Test
	public void test() {
		System.out.println(redisTemplate);
	}
	@Test
	public void testSetValue() {
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		operations.set("name",  "test", 300L, TimeUnit.SECONDS);
		System.out.println("done");
	}
	@Test
	public void testValue() {
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		System.out.println(redisTemplate.hasKey("name"));
        //根据key取出Value
        Object name = operations.get("name");
        System.out.println("name:"+name);
//		String s = (String) redisTemplate.opsForValue().get(CACHE_NAME_PROPERTY_PREFIX +REDIS_CHAR_KEY+ "763365501F5F21");
//		System.out.println(s);
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
	}
	
	@Test
	public void testList() {
		List<String> list = new ArrayList<String>();
		JSONObject json = new JSONObject();
		json.put("name","aaa");
		json.put("age", 10);
		json.put("msg","bbb");
		list.add(json.toJSONString());
		JSONObject json1 = new JSONObject();
		json1.put("name","bbb");
		json1.put("age", 11);
		json1.put("msg","ccc");
		list.add(json1.toJSONString());
		redisTemplate.opsForValue().set("testList", list);
	}
	
	@Test
	public void testGetList() {
		List<String> list = (List<String>) redisTemplate.opsForValue().get("testList1");
		System.out.println(list.size());
		System.out.println(list);
	}
	

	@Test
	public void testMap() {
		Map<String,Map<String,Object>> map = new HashMap<String, Map<String,Object>>();
		Map<String,Object> map1 = new HashMap<>();
		map1.put("name","aaa");
		map1.put("age", 10);
		map1.put("msg","bbb");
		map.put("key1", map1);
		Map<String,Object> map2 = new HashMap<>();
		map2.put("name","bbb");
		map2.put("age", 11);
		map2.put("msg","ccc");
		map.put("key2", map2);
		redisTemplate.opsForValue().set("testMap", map);
	}
	
	@Test
	public void testGetMap() {
		Map<String,Map<String,Object>> map = (Map<String, Map<String, Object>>) redisTemplate.opsForValue().get("testMap1");
		System.out.println(map.size());
		System.out.println(map.get("key1"));
	}
}
