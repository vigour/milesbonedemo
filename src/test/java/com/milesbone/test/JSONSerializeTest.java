package com.milesbone.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.json.simple.JSONObject;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import junit.framework.TestCase;

public class JSONSerializeTest extends TestCase{

	
	

	@Test
	public void testListJson() {
		List<JSONObject> temp = new ArrayList<JSONObject>();
		JSONObject obj;
		for(int i= 0; i<20;i++) {
			obj = new JSONObject();
			obj.put("key", "value"+i);
			obj.put("keya", "valuea"+i);
			temp.add(obj);
		}
		for(int j=0;j<temp.size();j++) {
			obj = temp.get(j);
			System.out.println("------------");
			System.out.println(obj.get("key"));
			System.out.println(obj.get("keya"));
			System.out.println("------------");
		}
		
	}
	@Test
	public void testListJson1() {
		List<String> list = new ArrayList<String>();
		JSONObject obj;
		for(int i= 0; i<3;i++) {
			list.add("test"+i);
		}
		obj = new JSONObject();
		obj.put("test", list);
		System.out.println("------------");
		System.out.println(JSON.toJSONString(obj.get("test")));
		System.out.println("------------");
	}
	
	
	
	@Test
	public void testJsonArray() {
		JSONArray ja = new JSONArray();
		JSONArray result = new JSONArray();
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("1_1", 1L);
		map.put("1_4", 1L);
		JSONObject jo = null;
		for(int i=0; i<10; i++) {
			jo = new JSONObject();
			jo.put("task_id", "1_"+i);
			jo.put("taskVal", "test"+i);
			jo.put("task", "testtask"+i);
			ja.add(jo);
		}
		String taskId = null;
		for(int j=0;j<ja.size();j++) {
			jo = (JSONObject) ja.get(j);
			taskId = jo.getString("task_id");
			if(map.containsKey(taskId)) {
				System.out.println("contain key:" + taskId + ", j="+j);
				continue;
//				ja.remove(j);
//				System.out.println("------------");
//				System.out.println(ja);
//				System.out.println("------------");
			}
			result.add(jo);
		}
		System.out.println(result.toJSONString());
	}
}
