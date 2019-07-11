package com.milesbone.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.junit.Test;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

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
	public void testMapIN() {
		Map<String,Long> map1 = new HashMap<String, Long>();
		map1.put("a", 1L);
		map1.put("b", 1L);
		map1.put("c", 1L);
		map1.put("d", 1L);
		
		Map<String,Long> map2 = new HashMap<String, Long>();
		map2.put("a1", 1L);
		map2.put("b1", 1L);
		
		Map<String,Long> map3 = new HashMap<String, Long>();
		System.out.println(map3.isEmpty());
		
		MapDifference<String,Long> handleMap =  Maps.difference(map1, map2);
		Map<String, Long> result = handleMap.entriesInCommon();
		
		String aa = result.keySet().toString();
		System.out.println(result.entrySet());
		System.out.println("key:" +aa);
		
		System.out.println(result.isEmpty());
		
		Set<Integer> sets = Sets.newHashSet(1, 2, 3, 4, 5, 6);
		Set<Integer> sets2 = Sets.newHashSet(3, 4, 5, 6, 7, 8, 9);
		// 交集
		System.out.println("交集为：");
		SetView<Integer> intersection = Sets.intersection(sets, sets2);
		for (Integer temp : intersection) {
			System.out.println(temp);
		}
		// 差集
		System.out.println("差集为：");
		SetView<Integer> diff = Sets.difference(sets, sets2);
		for (Integer temp : diff) {
			System.out.println(temp);
		}
		// 并集
		System.out.println("并集为：");
		SetView<Integer> union = Sets.union(sets, sets2);
		for (Integer temp : union) {
			System.out.println(temp);
		}
	}
	
	@Test
	public void testNullMap() {
		Map<String, Long> map = null;
		System.out.println(map.containsKey("a"));
	}
	
	@Test
	public void testCaseInsenceMap() {
		Map<String, Object> map = new CaseInsensitiveMap();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("A1", 1);
		map1.put("A2", 2);
		map.putAll(map1);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("A3", 1);
		map2.put("a1", 2);
		map.putAll(map2);
		
		System.out.println(map.containsKey("a2"));
		
				
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
