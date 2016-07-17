package com.milesbone.zookeeper;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slave2 implements Watcher {
	
	private static final Logger logger = LoggerFactory.getLogger(ZKSysConfig.class);
	 /** 
     * zk实例 
     * **/  
    public ZooKeeper zk;  
      
    /** 
     *  同步工具 
     *  
     * **/  
    private CountDownLatch count = new CountDownLatch(1);  
      
	
	public Slave2() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * hosts， zookeeper的访问地址
	 * 
	 **/
	public Slave2(String hosts) {
		try {
			zk = new ZooKeeper(hosts, 70000, new Watcher() {

				@Override
				public void process(WatchedEvent event) {
					// TODO Auto-generated method stub
					if (event.getState() == Event.KeeperState.SyncConnected) {
						count.countDown();

					}

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 
	 * 此方法是写入数据 如果不存在此节点 就会新建，已存在就是 更新
	 * 
	 **/
	public void write(String path, String value) throws Exception {

		Stat stat = zk.exists(path, false);
		if (stat == null) {
			zk.create(path, value.getBytes(StandardCharsets.UTF_8), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} else {

			zk.setData(path, value.getBytes(StandardCharsets.UTF_8), -1);
		}

	}

	public String read(String path, Watcher watch) throws Exception {

		byte[] data = zk.getData(path, watch, null);

		return new String(data, StandardCharsets.UTF_8);
	}

	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void automicSwitch() throws Exception {

		logger.error("Master故障，Slave自动切换.......,  时间  " + f.format(new Date()));

	}

	public void startMaster() {

		logger.info("C的Master 启动了........");
	}

	public void createPersist() throws Exception {

		zk.create("/a", "主节点".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		logger.info("创建主节点成功........");

	}

	public void createTemp() throws Exception {

		zk.create("/a/b", "c".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

		logger.info("c创建子节点成功...........");

	}

	public void check() throws Exception {
		List<String> list = zk.getChildren("/a", null);
		Collections.sort(list);
		if (list.isEmpty()) {
			logger.info("此父路径下面没有节点");
		} else {

			String start = list.get(0);

			String data = new String(zk.getData("/a/" + start, false, null));
logger.info("data: {}", data);			
			if (data.equals("c")) {// 等于本身就启动作为Master

				if (list.size() == 1) {
					startMaster();// 作为Master启动
				} else {
					automicSwitch();
				}
			} else {
				// 非当前节点
				for (int i = 0; i < list.size(); i++) {
					// 获取那个节点存的此客户端的模拟IP
					String temp = new String(zk.getData("/a/" + list.get(i), false, null));
logger.info("temp:{}", temp);
					if (temp.equals("c")) {
						// 因为前面作为首位判断，所以这个出现的位置不可能是首位
						// 需要监听小节点里面的最大的一个节点
						String watchPath = list.get(i - 1);
						logger.info("c监听的是:  " + watchPath);

						zk.exists("/a/" + watchPath, this);// 监听此节点的详细情况
						break;// 结束循环
					}

				}

			}

		}

	}

	public void close() throws Exception {
		zk.close();
	}

	@Override
	public void process(WatchedEvent event) {

		if (event.getType() == Event.EventType.NodeDeleted) {

			// 如果发现，监听的节点，挂掉了，那么就重新，进行监听
			try {
				logger.info("注意有节点挂掉，重新调整监听策略........");
				check();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	public static void main(String[] args) throws Exception {

		Slave2 s = new Slave2("192.168.4.128:2181,192.168.4.128:2182,192.168.4.128:2183");
//		 s.createPersist();//创建主节点
		s.createTemp();
		s.check();
		Thread.sleep(Long.MAX_VALUE);
		s.close();

	}

}
