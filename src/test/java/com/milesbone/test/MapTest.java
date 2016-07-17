package com.milesbone.test;

import java.util.HashMap;
import java.util.Map;

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
	public void testMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("A_12313", "123123");
		map.put("B_456", "456");
		map.put("C_789", "789");
		map.put("D_123313", "D_123313");
		System.out.println(map.containsKey("A_"));
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
