package com.milesbone.zookeeper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CuratorMasterSelectedDemo {
	private static final Logger logger = LoggerFactory.getLogger(CuratorMasterSelectedDemo.class);
	
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
	
	private static final String MASTER_PATH = "/master_lock";
	/**
	 * 初始化客户端
	 * @return
	 */
	private static CuratorFramework initClient(){
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
	public void testMasterSelected(){
		final CuratorFramework client = initClient();
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		
		for(int i = 0; i < 8; i++){
			executorService.execute(new Runnable() {
				
				@Override
				public void run() {
					while(true) {
						
						LeaderSelector leaderSelector = new LeaderSelector(client, MASTER_PATH, new LeaderSelectorListenerAdapter() {
							
							/**
							 * Curator会在该线程被成功选取为master时回调该方法.同时,当该方法执行完毕之后,Curator会自动释放master权利,然后重新开始新一轮的选举
							 */
							@Override
							public void takeLeadership(CuratorFramework client) throws Exception {
								logger.info("Thread:" + Thread.currentThread().getId() + " be selected as Master!");
								try{
									Thread.sleep(3000);
								}catch(Throwable e){
									
								}
								logger.info("Thread:" + Thread.currentThread().getName() + " release master right!");
							}
						});
						
						//By default, when LeaderSelectorListener.takeLeadership(CuratorFramework) returns, this instance is not requeued. 
						//Calling this method puts the leader selector into a mode where it will always requeue itself.
						leaderSelector.autoRequeue();
						
						//Attempt leadership. This attempt is done in the background - i.e. this method returns immediately.
						leaderSelector.start();
						
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						//Shutdown this selector and remove yourself from the leadership group
						leaderSelector.close();
					}
				};
			});
		}
		
		executorService.shutdown();
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
