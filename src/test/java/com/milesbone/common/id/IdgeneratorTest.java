package com.milesbone.common.id;


import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import org.junit.Test;

import com.milesbone.common.id.impl.DefaultIdGenerator;

public class IdgeneratorTest {
	

	
	@Test
	public void testFormat(){
		int a = 1;
		System.out.println(String.format("%08d", a));
		
	}
	@Test
	public void test1() throws InterruptedException{
		IIdGenerator idGenerator = new DefaultIdGenerator();
		
		for (int i = 0; i < 50000; i++) {
			idGenerator.next();
			if(i%1000==0){
				Thread.sleep(100);
				System.out.println(idGenerator.next());
			}
		}
	}

}
