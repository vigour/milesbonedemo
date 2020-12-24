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
}
