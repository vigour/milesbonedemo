package com.milesbone.queue;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLightQueueSynced {

	private static final Logger logger = LoggerFactory.getLogger(TestLightQueueSynced.class);

	@Test
	public void testQueueSync() throws InterruptedException {
		CountDownLatch main = new CountDownLatch(1);// 控制子线程等待主线程

		CountDownLatch part = new CountDownLatch(20);// 控制主线程等待子线程全部执行完后再继续
		
		ThreadProducer producer = null;
		for (int i = 0; i < 10; i++) {
			producer = new ThreadProducer(main, part);
			producer.start();
		}
		
		ThreadConsumer consumer = null;
		for (int i = 0; i < 10; i++) {
			consumer = new ThreadConsumer(main, part);
			consumer.start();
		}
		
		main.countDown();
		part.await();
		System.out.println(LightQueueSynced.getQueueList().size());
		System.out.println(LightQueueSynced.getQueueList());
		
		
		

	}

	class ThreadProducer extends Thread{

		CountDownLatch main = null;

		CountDownLatch part = null;

		public ThreadProducer(CountDownLatch main, CountDownLatch part) {
			super();
			this.main = main;
			this.part = part;
		}

		@Override
		public void run() {
			try {
				this.main.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			LightQueueSynced.queue("test" + Thread.currentThread().getId());
			logger.debug("ThreadProducer push thread: test{}:{} into queue..", Thread.currentThread().getId(),Thread.currentThread().getName());
			part.countDown();
		}

	}

	class ThreadConsumer extends Thread{
		CountDownLatch main = null;

		CountDownLatch part = null;

		public ThreadConsumer(CountDownLatch main, CountDownLatch part) {
			super();
			this.main = main;
			this.part = part;
		}

		@Override
		public void run() {
			try {
				this.main.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			LightQueueSynced.pollFirst();
			logger.debug("ThreadConsumer poll thread:test{}:{} out from queue..",Thread.currentThread().getId(),Thread.currentThread().getName());
			part.countDown();
		}

	}
}
