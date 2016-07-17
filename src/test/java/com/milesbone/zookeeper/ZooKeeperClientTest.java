package com.milesbone.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Zookeeper 客户端操作
 * @author miles
 *
 */
public class ZooKeeperClientTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ZooKeeperClientTest.class);

	public ZooKeeperClientTest() {
		super();
	}

	// 会话超时时间，设置为与系统默认时间一致
	private static final int SESSION_TIMEOUT = 30 * 1000;

	// 创建 ZooKeeper 实例
	private ZooKeeper zk;

	// 创建 Watcher 实例
	private Watcher wh = new Watcher() {
		/**
		 * Watched事件
		 */
		public void process(WatchedEvent event) {
			logger.debug("WatchedEvent >>> " + event.toString());
			CountDownLatch down = new CountDownLatch(1);// 同步阻塞状态
			if (event.getState() == Event.KeeperState.SyncConnected) {
				down.countDown();// 连接上之后，释放计数器
			}
		}
	};

	// 初始化 ZooKeeper 实例
	private  void createZKInstance() throws IOException {
		// 连接到ZK服务，多个可以用逗号分割写
		zk = new ZooKeeper("192.168.4.128:2181,192.168.4.128:2182,192.168.4.128:2183",
				ZooKeeperClientTest.SESSION_TIMEOUT, this.wh);
		if(!zk.getState().equals(States.CONNECTED)){
            while(true){
                if(zk.getState().equals(States.CONNECTED)){
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(ZooKeeperClientTest.SESSION_TIMEOUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
		logger.debug("连接成功：" + zk.toString());
	}

	private void ZKOperations() throws IOException, InterruptedException, KeeperException {
		logger.debug("\n1. 创建 ZooKeeper 节点 (znode ： zoo2, 数据： myData2 ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
		zk.create("/zoo2", "myData2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		logger.debug("\n2. 查看是否创建成功： ");
		logger.debug(new String(zk.getData("/zoo2", this.wh, null)));// 添加Watch

		// 前面一行我们添加了对/zoo2节点的监视，所以这里对/zoo2进行修改的时候，会触发Watch事件。
		logger.debug("\n3. 修改节点数据 ");
		zk.setData("/zoo2", "shanhy20160310".getBytes(), -1);

		// 这里再次进行修改，则不会触发Watch事件，这就是我们验证ZK的一个特性“一次性触发”，也就是说设置一次监视，只会对下次操作起一次作用。
		logger.debug("\n3-1. 再次修改节点数据 ");
		zk.setData("/zoo2", "shanhy20160310-ABCD".getBytes(), -1);

		logger.debug("\n4. 查看是否修改成功： ");
		logger.debug(new String(zk.getData("/zoo2", false, null)));

		logger.debug("\n5. 删除节点 ");
		zk.delete("/zoo2", -1);

		logger.debug("\n6. 查看节点是否被删除： ");
		logger.debug(" 节点状态： [" + zk.exists("/zoo2", false) + "]");
	}

	private void ZKClose() throws InterruptedException {
		zk.close();
	}

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		ZooKeeperClientTest dm = new ZooKeeperClientTest();
		dm.createZKInstance();
		dm.ZKOperations();
		dm.ZKClose();
	}

	@Test
	public void testConnect() throws InterruptedException, IOException {
		// 连接到ZK服务，多个可以用逗号分割写
		zk = new ZooKeeper("192.168.4.128:2181,192.168.4.128:2182,192.168.4.128:2183",
				ZooKeeperClientTest.SESSION_TIMEOUT, this.wh);
		if(!zk.getState().equals(States.CONNECTED)){
            while(true){
                if(zk.getState().equals(States.CONNECTED)){
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(ZooKeeperClientTest.SESSION_TIMEOUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
		logger.debug("连接成功：" + zk.toString());
		zk.close();
	}

}
