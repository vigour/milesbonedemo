package com.milesbone.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.AtomicLongMap;

public class GuavaTest {
	
	
	@Test
	public void testDiff() {
		Map<String,Integer> srcMap = new HashMap<String,Integer>();
		Map<String,Integer> destMap = new HashMap<String,Integer>();
		
		srcMap.put("1", 1);
		srcMap.put("2", 1);
		srcMap.put("3", 1);
		destMap.put("1", 2);
		destMap.put("2", 2);
		destMap.put("4", 1);
		destMap.put("5", 1);
		
		//找出2个Map的不同之处与相同之处，以Map形式返回
//		ImmutableMap<String,Integer> oneMap=ImmutableMap.of("key1",1,"key2",2);
//		ImmutableMap<String,Integer> twoMap=ImmutableMap.of("key11",11,"key2",2);
		MapDifference<String,Integer> diffHadle=Maps.difference(srcMap,destMap);
		Map<String,Integer> commonMap=diffHadle.entriesInCommon();//{"key2",2},若无共同Entry，则返回长度为0的Map
		Map<String,Integer> diffOnLeft=diffHadle.entriesOnlyOnLeft();//返回左边的Map中不同或者特有的元素
		Map<String,Integer> diffOnRight=diffHadle.entriesOnlyOnRight();//返回右边的Map中不同或者特有的元素
		for(Map.Entry<String, Integer> entry:commonMap.entrySet()){
			System.out.println("共同Map中key:"+entry.getKey()+"  value:"+entry.getValue());
		}
		for(Map.Entry<String, Integer> entry:diffOnLeft.entrySet()){
			System.out.println("左边不同Map中key:"+entry.getKey()+"  value:"+entry.getValue());
		}
		for(Map.Entry<String, Integer> entry:diffOnRight.entrySet()){
			System.out.println("右边不同Map中key:"+entry.getKey()+"  value:"+entry.getValue());
		}
	}
	
	
	
	// 来自于Google的Guava项目
		AtomicLongMap<String> map = AtomicLongMap.create(); // 线程安全，支持并发
	 
		Map<String, Integer> map2 = new HashMap<String, Integer>(); // 线程不安全
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock(); // 为map2增加并发锁
	 
		ConcurrentHashMap<String, Integer> map3 = new ConcurrentHashMap<String, Integer>(); // 线程安全，但也要注意使用方式
	 
		private int taskCount = 100;
		CountDownLatch latch = new CountDownLatch(taskCount); // 新建倒计时计数器，设置state为taskCount变量值
	 
		public static void main(String[] args) {
			GuavaTest t = new GuavaTest();
			t.test();
		}
	 
		private void test() {
			// 启动线程
			for (int i = 1; i <= taskCount; i++) {
				Thread t = new Thread(new MyTask("key", 100));
				t.start();
			}
	 
			try {
				// 等待直到state值为0，再继续往下执行
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	 
			System.out.println("##### AtomicLongMap #####");
			for (String key : map.asMap().keySet()) {
				System.out.println(key + ": " + map.get(key));
			}
	 
			System.out.println("##### HashMap #####");
			for (String key : map2.keySet()) {
				System.out.println(key + ": " + map2.get(key));
			}
	 
			System.out.println("##### ConcurrentHashMap #####");
			for (String key : map3.keySet()) {
				System.out.println(key + ": " + map3.get(key));
			}
		}
	 
		class MyTask implements Runnable {
			private String key;
			private int count = 0;
	 
			public MyTask(String key, int count) {
				this.key = key;
				this.count = count;
			}
	 
			@Override
			public void run() {
				try {
					for (int i = 0; i < count; i++) {
						map.incrementAndGet(key); // key值自增1后，返回该key的值
	 
						// 对map2添加写锁，可以解决线程并发问题
						lock.writeLock().lock();
						try {
							if (map2.containsKey(key)) {
								map2.put(key, map2.get(key) + 1);
							} else {
								map2.put(key, 1);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						} finally {
							lock.writeLock().unlock();
						}
	 
						// 虽然ConcurrentHashMap是线程安全的，但是以下语句块不是整体同步，导致ConcurrentHashMap的使用存在并发问题
						if (map3.containsKey(key)) {
							map3.put(key, map3.get(key) + 1);
						} else {
							map3.put(key, 1);
						}
	 
						// TimeUnit.MILLISECONDS.sleep(50); //线程休眠50毫秒
					}
	 
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					latch.countDown(); // state值减1
				}
			}
		}
	 


}
