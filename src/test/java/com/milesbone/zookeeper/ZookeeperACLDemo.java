package com.milesbone.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Zookeeper 访问控制列表操作
 * @author miles
 *
 */
public class ZookeeperACLDemo implements Watcher{

	private static final Logger logger = LoggerFactory.getLogger(ZookeeperACLDemo.class);

	private static CountDownLatch connectSignal = new CountDownLatch(1);
	private static final String CONNECT_STRING = "192.168.4.128:2181,192.168.4.128:2182,192.168.4.128:2183";
	private static final int DEFAULT_SESSION_TIMEOUT = 5000;

	
	public static void main(String[] args) {
		//1.使用带有权限认证的客户端创建节点"/acl-test"
		ZooKeeper client = initClient();
		//附加权限认证
		client.addAuthInfo("digest", "eagle:eagle".getBytes());
		
		//创建节点"/acl-test"
		String path = null;
		try {
			path = client.create("/acl-test2", "/acl-test2".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
		} catch (KeeperException | InterruptedException e) {
			logger.error("创建节点失败:{}", e.getMessage());
			e.printStackTrace();
		}
		logger.info("success create znode:" + path + " with auth info!");
		
		//关闭连接
		cloeseClient(client);
		connectSignal = new CountDownLatch(1); 
		
		
		//2.使用没有权限认证的客户端,创建子节点"/acl-test/test1"
		client = initClient();
		
		try{
			client.create(path + "/test2", "/acl-test2/test2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch(Throwable e){
			logger.error("failed create " + (path + "/test2 without auth info, caused by:") +  e.getMessage());
		}
		
		
		//3.添加权限认证,创建子节点"/acl-test/test1"
		client.addAuthInfo("digest", "eagle:eagle".getBytes());
		try{
			path = client.create(path + "/test2", "/acl-test2/test2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			logger.info("success create with auth info for path:" + path);
		} catch(Throwable e){
			logger.error(e.getMessage());
		}
		cloeseClient(client);
		
		connectSignal = new CountDownLatch(1); 
		client = initClient();
		
		//4.使用无权限认证的客户端删除"/simple/acl-test/test1"
		try{
			client.delete("/acl-test2/test2", -1);
			logger.info("success delete path:/acl-test2/test2 without auth info!");
		}catch(Throwable e){
			logger.error("failed delete path:/acl-test2/test2 without auth info!");
		}
		
		
		//5.使用有权限认证的客户端删除"/simple/acl-test/test1"
		client.addAuthInfo("digest", "eagle:eagle".getBytes());
		try{
			client.delete("/acl-test2/test2", -1);
			logger.info("success delete path:/acl-test2/test2 with auth info!");
		}catch(Throwable e){
			e.printStackTrace();
		}
		cloeseClient(client);
		
		connectSignal = new CountDownLatch(1); 
		client = initClient();
		
		//6.使用无权限认证的客户端删除"/simple/acl-test"
		try{
			client.delete("/acl-test2", -1);
			logger.info("success delete path:/acl-test2 without auth info!");
		}catch(Throwable e){
			e.printStackTrace();
		}
	}
	
	public static ZooKeeper initClient() {
		ZooKeeper client = null;
		try {
			client = new ZooKeeper(CONNECT_STRING, DEFAULT_SESSION_TIMEOUT,new ZookeeperACLDemo());
			connectSignal.await();
			logger.info("finish connected!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return client;
	}
	
	
	public static void cloeseClient(ZooKeeper client){
		if(null != client){
			//关闭连接
			try {
				client.close();
			} catch (InterruptedException e) {
				logger.error("节点关闭失败:" + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public void process(WatchedEvent event) {
		if(KeeperState.SyncConnected == event.getState()){
			connectSignal.countDown();
		}
	}

}
