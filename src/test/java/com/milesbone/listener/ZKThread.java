package com.milesbone.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZKThread implements Runnable {

	public static final Logger logger = LoggerFactory.getLogger(ZKThread.class);
	
	static String path = "/order/overtime/UPBO";
	
	private CuratorFramework cfClient;
	
	private String itemValue;
	
	
	public ZKThread() {
		super();
	}
	
	


	public ZKThread(CuratorFramework cfClient, String itemValue) {
		super();
		this.cfClient = cfClient;
		this.itemValue = itemValue;
	}

	



	@Override
	public void run() {
		String[] itemValueArray = this.itemValue.split("_");
		logger.info("itemValueArray[2] :{}",itemValueArray[2]);
		
		//把订单号加入到排他锁路径中
		final String testPath = path + itemValueArray[2];
		logger.info("testPath :{}", testPath);
		try {
			cfClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(
					new BackgroundCallback() {
						@Override
						public void processResult(CuratorFramework cf, CuratorEvent event) throws Exception {
							logger.info("event :{}",event);
							if(0 == event.getResultCode()){
								//使用线程后续处理
								logger.info("创建成功获得后续业务处理权,successs.");
								Thread.sleep(5000);
								//业务处理完毕后主动删除零食节点
								cfClient.delete().forPath(testPath);
							}else{
								logger.info("Oh no,I'm late...");
							}
						}
			}).forPath(testPath);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
