package com.milesbone.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class MapTest {

	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("A_12313", "123123");
		map.put("B_456", "456");
		map.put("C_789", "789");
		map.put("D_123313", "D_123313");
		System.out.println(map.containsKey("A_"));
	}
	
	@Test
	public void testString2Set(){
		String servers = "192.168.4.128:7001,192.168.4.128:7002,192.168.4.128:7003,192.168.4.128:7004,192.168.4.128:7005";
		Set<String> s = Collections.singleton(servers);
		Iterator<String> ss = s.iterator();
		while (ss.hasNext()) {
			String type = (String) ss.next();
			System.out.println(type);
			
		}
	}
	
	
	@Test
	public void testMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("A_12313", "123123");
		map.put("B_456", "456");
		map.put("C_789", "789");
		map.put("D_123313", "D_123313");
		System.out.println(map.containsKey("A_"));
	}
	
	@Test
	public void testMapPutAll(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("A_12313", "123123");
		map.put("B_456", "456");
		map.put("C_789", "789");
		map.put("D_123313", "D_123313");
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("E", "e");
		map.putAll(map);
		System.out.println(map.toString());
	}
	
	
	@Test
	public void testShowMap(){
		Map<String, String> map = new HashMap<String, String>();
		for(String k : map.keySet()){
			if(k.indexOf("1")!=-1)
			System.out.println(k);
		}
	}
}
