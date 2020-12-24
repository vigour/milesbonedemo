package com.milesbone.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zookeeper开源客户端框架Curator测试
 * @author miles
 *
 */
public class CuratorZookeeperTest {

	private static final Logger logger = LoggerFactory.getLogger(CuratorZookeeperTest.class);
	
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
	
	/**
	 * 创建节点
	 * @param client
	 * @param destPath
	 */
	private static void createData(CuratorFramework curatorFramework, String destPath){
		try {
			String path = curatorFramework.create()
								.creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(destPath);
			logger.info("finish create path:" + path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除节点
	 * @param curatorFramework
	 * @param destPath
	 */
	private static void deleteData(CuratorFramework curatorFramework, String destPath){
		try {
			curatorFramework.delete().guaranteed().forPath(destPath);
			logger.info("success delete path:" + destPath);
			createData(curatorFramework, destPath);
			//删除一个节点,强制并递归删除所有子节点
			curatorFramework.delete().deletingChildrenIfNeeded().forPath("/curator-test");

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 监听节点数据变化
	 * @throws Exception 
	 */
	private static void watchDataChanged(CuratorFramework client) throws Exception{
		//1.创建目标监听节点
		String path = client.create().creatingParentsIfNeeded().forPath("/node1", "eagle".getBytes());
		logger.info("success create path:" + path);
		
		//2.添加监听器
		final NodeCache nodeCache = new NodeCache(client, path);
		nodeCache.getListenable().addListener(new NodeCacheListener() {
			
			@Override
			public void nodeChanged() throws Exception {
				logger.info("node data changed, new data:" + new String(nodeCache.getCurrentData().getData()));
			}
		});
		nodeCache.start(true);
		
		//3.更新数据
		client.setData().forPath(path, "eagle_update".getBytes());
		Thread.sleep(1000);
		
		//4.再次更新
		client.setData().forPath(path, "eagle_update_update".getBytes());
		Thread.sleep(1000);
		
		//4.删除节点
		client.delete().deletingChildrenIfNeeded().forPath(path);
	}
	
	
	private static void watchChildrenChanged(CuratorFramework client) throws Exception{
		//1.创建目标监听节点
		String path = client.create().creatingParentsIfNeeded().forPath("/node1", "eagle".getBytes());
		logger.info("success create path:" + path);
		
		//2.添加监听器
		PathChildrenCache cache = new PathChildrenCache(client, path, true);
		cache.start(StartMode.BUILD_INITIAL_CACHE);
		cache.getListenable().addListener(new PathChildrenCacheListener() {
			
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				switch(event.getType()){
					case  CHILD_ADDED:
						logger.info("CHILD_ADDED:" + event.getData().getPath());
						break;
					case CHILD_REMOVED:
						logger.info("CHILD_REMOVED:" + event.getData().getPath());
						break;
					case CHILD_UPDATED:
						logger.info("CHILD_UPDATED:" + event.getData().getPath());
						break;
					default:
						logger.info("event type:" + event.getType());
						break;
				}
			}
		});
		
		//3.创建子节点
		String result = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path + "/c1");
		logger.info("success create path:" + result);
		
		Thread.sleep(2000);
		
		//4.创建二级子节点
		String result2 = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(result + "/c2");
		logger.info("success create path:" + result2);
		
		Thread.sleep(2000);
		
		//5.删除子节点
		client.delete().deletingChildrenIfNeeded().forPath(result);
		logger.info("success delete path:" + result);
		
		Thread.sleep(2000);
		
		//6.删除节点
		client.delete().forPath(path);
		logger.info("success delete path:" + path);
		
		Thread.sleep(2000);
		
		cache.close();
	}
	
	@Test
	public void testSimpleCurator(){
		CuratorFramework client = initClient();
		String destPath = "/curator-test/test1";
		createData(client, destPath);
		deleteData(client, destPath);
	}
	
	@Test
	public void testNodeCurator(){
		CuratorFramework client = initClient();
		try {
			watchDataChanged(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWatchChildrenChanged(){
		CuratorFramework client = initClient();
		try {
			watchChildrenChanged(client);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
