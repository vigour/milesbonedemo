package com.milesbone.listener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

public class KeyExpiresMessageListener implements MessageListener{

	private static final Logger logger = LoggerFactory.getLogger(KeyExpiresMessageListener.class);
	
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
//	private static final int MAX_SLEEP_TIME = 60000;
	
	private static final String CONNECT_STRING = "192.168.4.128:2181,192.168.4.128:2182,192.168.4.128:2183";
	private static final int SESSION_TIMEOUT = 5000;
	private static final int CONNECTION_TIMEOUT = 5000;
	
	static CuratorFramework cfClient = CuratorFrameworkFactory.builder()
			.connectString(CONNECT_STRING)
			.sessionTimeoutMs(SESSION_TIMEOUT)
			.connectionTimeoutMs(CONNECTION_TIMEOUT)
			.retryPolicy(new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES_COUNT))
			.namespace("sample")
			.build();
	
	private static ExecutorService excutor = Executors.newCachedThreadPool();
	
	
	public KeyExpiresMessageListener(){
		//启动Zookeeper客户端
		cfClient.start();
	}

	
	private RedisTemplate<String, String> redisTemplate;
	

	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}


	@Override
	public void onMessage(Message message, byte[] pattern) {
		byte[] body = message.getBody();
		byte[] channel = message.getChannel();
		String itemValue = (String) redisTemplate.getValueSerializer().deserialize(body);
		String itemChannel = redisTemplate.getStringSerializer().deserialize(channel);
		
		logger.info("[KeyExpiresMessageListener] on message: channel[{}], message[{}]",itemChannel,itemValue);
		
		//通过线程池执行分拆数据可以的订单号,尝试创建排他锁等工作
		excutor.execute(new ZKThread(cfClient,itemValue));
		
	}
	
	
}
