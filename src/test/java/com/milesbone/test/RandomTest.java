package com.milesbone.test;

import java.util.Random;

import org.junit.Test;

public class RandomTest {

	
	@Test
	public void testRandomInt(){
		Random random = new Random();
		for(int i = 0; i < 10; i++)
		System.out.println(random.nextInt(10));
	}
	
	@Test
	public void testRandomBoolean(){
		Random random = new Random();
		for(int i = 0; i < 10; i++)
		System.out.println(random.nextBoolean());
	}
	
	@Test
	public void testNum(){
		System.out.println(Runtime.getRuntime().availableProcessors());
		System.out.println(Runtime.getRuntime().availableProcessors()>>1);
		System.out.println(Runtime.getRuntime().availableProcessors()<<1);
	}
	
	@Test
	public void testShortNum(){
		System.out.println(0xbabe);
	}
	
}
