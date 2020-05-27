package com.milesbone.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

public class CuratorLockDemo {

//	private static final Logger logger = LoggerFactory.getLogger(CuratorMasterSelectedDemo.class);

	/**
	 * 初始sleep时间(毫秒)
	 */
	private static final int BASE_SLEEP_TIME = 1000;
	/**
	 * 最大重试次数
	 */
	private static final int MAX_RETRIES_COUNT = 5;
	/**
	 * 最大sleep时间
	 */
	private static final int MAX_SLEEP_TIME = 60000;

	private static final String CONNECT_STRING = "192.168.4.128:2181,192.168.4.128:2182,192.168.4.128:2183";
	private static final int SESSION_TIMEOUT = 5000;
	private static final int CONNECTION_TIMEOUT = 5000;

//	private static final String MASTER_PATH = "/master_lock";

	
	/**
	 * 初始化客户端
	 * @return
	 */
	public static CuratorFramework initClient(){
		//1.设置重试策略,重试时间计算策略sleepMs = baseSleepTimeMs * Math.max(1, random.nextInt(1 << (retryCount + 1)));
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES_COUNT, MAX_SLEEP_TIME);
		
		//2.使用Fluent风格初始化客户端
		CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
														.connectString(CONNECT_STRING)
														.sessionTimeoutMs(SESSION_TIMEOUT)
														.connectionTimeoutMs(CONNECTION_TIMEOUT)
														.retryPolicy(retryPolicy)
														.namespace("sample")
														.build();
		curatorFramework.start();
		return curatorFramework;
	}
	
	
	@Test
	public void testCuratorLock(){
//		final CountDownLatch countDownLatch = new CountDownLatch(1);
//		CuratorFramework client = initClient();
//		
//		final InterProcessLock lock = new InterProcessMutex(client, MASTER_PATH);
		
	}

}
