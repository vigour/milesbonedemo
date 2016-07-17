package com.milesbone.test;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.milesbone.parallel.MultiThreadedRunner;


@RunWith(MultiThreadedRunner.class)
public class MultiThread {

	@Test
	public void testThread() {
		for(int i = 0; i<100; i++){
			MyThread myThread = new MyThread();
			myThread.setName("world" + i);
			Thread thread = new Thread(myThread);
			thread.start();
		}
	}

}
