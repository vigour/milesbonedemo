package com.milesbone.test;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.junit.Test;


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
}
