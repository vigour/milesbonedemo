package com.milesbone.zookeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Zookeeper Session操作
 * @author miles
 *
 */
public class ZookeeperSessionDemo implements Watcher {

	private static final CountDownLatch connectSignal = new CountDownLatch(1);
	private static final String CONNECT_STRING = "192.168.4.128:2181,192.168.4.128:2182,192.168.4.128:2183";
	private static final int DEFAULT_SESSION_TIMEOUT = 5000;
	
	private static final Logger logger = LoggerFactory.getLogger(ZookeeperSessionDemo.class);
	
	
	
	public static void simpleConnection(){
		ZooKeeper client = null;
		try {
			client = new ZooKeeper(CONNECT_STRING, DEFAULT_SESSION_TIMEOUT, new ZookeeperSessionDemo());
			logger.info("连接状态:{}" + client.getState() );
			connectSignal.await();
			logger.info("finish connected!");
		} catch (IOException e) {
			logger.error("创建连接异常:"+e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			logger.error("线程等待异常");
			e.printStackTrace();
		}finally {
			if(null != client){
				try {
					client.close();
				} catch (InterruptedException e) {
					logger.debug("连接关闭失败");
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 测试通过sessionId以及sessionPasswd复用会话连接
	 */
	public static void createConnectBySeessionId(){
		ZooKeeper client = null;
		try {
			client = new ZooKeeper(CONNECT_STRING, DEFAULT_SESSION_TIMEOUT, new ZookeeperSessionDemo());
			connectSignal.await();
				
			//获取sessionId以及sessionPasswd
			long sessionId = client.getSessionId();
			byte[] sessionPasswd = client.getSessionPasswd();
			logger.info("first connected session id:" + sessionId);
				
			//使用错误的sesssionId
//			ZooKeeper clientWithWrongSessionId = new ZooKeeper(CONNECT_STRING, DEFAULT_SESSION_TIMEOUT, new ZookeeperSessionDemo(), new Random().nextLong(), sessionPasswd);
				
			//使用正确的sessionId以及sessionPasswd
			client = new ZooKeeper(CONNECT_STRING, DEFAULT_SESSION_TIMEOUT, new ZookeeperSessionDemo(), sessionId, sessionPasswd);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			if(client != null){
				try {
					client.close();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 同步获取子节点列表
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	public static void getChildrenSync() throws IOException, InterruptedException, KeeperException{
		final ZooKeeper client = new ZooKeeper(CONNECT_STRING, DEFAULT_SESSION_TIMEOUT, new ZookeeperSessionDemo());
		connectSignal.await();
		
		//获取子节点,注意这里的"/"即"/simple"，连接时由CONNECT_STRING指定"127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183/simple";
		client.getChildren("/", new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				if(EventType.NodeChildrenChanged == event.getType()){
					try {
							logger.info("receive node changed event. reload children:" +  client.getChildren(event.getPath(), true));
						} catch (KeeperException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			}
					
		});
		
		//创建子节点/simple/s1
		String path = client.create("/s1", "s1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		logger.info("finish create znode:" + path);
		
		path = client.create("/s2", "s2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		logger.info("finish create znode:" + path);
		
	}
	
	
	/**
	 * 异步获取子节点列表
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	public static void getChildrenAsync() throws IOException, InterruptedException, KeeperException{
		final ZooKeeper client = new ZooKeeper(CONNECT_STRING, DEFAULT_SESSION_TIMEOUT, new ZookeeperSessionDemo());
		connectSignal.await();
		
		//创建子节点/simple/s1
		String path = client.create("/s1", "s1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		logger.info("finish create znode:" + path);
		
		//获取子节点,注意这里的"/"即"/simple"，连接时由CONNECT_STRING指定"127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183/simple";
		client.getChildren("/", true, new AsyncCallback.Children2Callback(){

					@Override
					public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
						logger.info("async get children:[response code:" + rc + ", path:" + path + ", ctx:" + ctx + ", children:" + children.toString() + ", stat:" + stat + "]");
					}
					
				}, "hello");
		
		path = client.create("/s2", "s2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		logger.info("finish create znode:" + path);
		
		Thread.sleep(10000);
	}
	
	
	
	
	public static void main(String[] args) throws IOException, Exception, KeeperException {
//		simpleConnection();//简单连接
//		createConnectBySeessionId();
//		getChildrenSync();
		getChildrenAsync();
	}
	
	
	
	
	@Override
	public void process(WatchedEvent event) {
		logger.info("Receive watched event:" + event);
		
		//连接建立完成
		if(KeeperState.SyncConnected == event.getState()){
			connectSignal.countDown();
		}
	}

}
