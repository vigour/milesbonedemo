package com.milesbone.zookeeper;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Zookeeper实现分布式配置同步 
 * @author miles
 *
 */
public class ZKSysConfig implements Watcher{

	private static final Logger logger = LoggerFactory.getLogger(ZKSysConfig.class);
	
	private ZooKeeper zk;
	
	private CountDownLatch countDown = new CountDownLatch(1);
	
	// 会话超时时间，设置为与系统默认时间一致
	private static final int SESSION_TIMEOUT = 30 * 1000;
 
    private static final String PATH="/zkdatatest";  
    
	public ZKSysConfig(String hosts){
		try{
			zk = new ZooKeeper(hosts, SESSION_TIMEOUT, new Watcher(){
	
				public void process(WatchedEvent event) {
					if(event.getState().SyncConnected==Event.KeeperState.SyncConnected){
						 //防止在未连接Zookeeper服务器前，执行相关的CURD操作  
	                    countDown.countDown();//连接初始化，完成，清空计数器
					}
				}
			});
		}catch(Exception e){  
			logger.error("Zookeeper连接初始化出错!");
	        e.printStackTrace();  
		}
	}

	
	/**
	 * 写入或者更新数据
	 * @param path
	 * @param data
	 */
	public void addOrUpdateData(String path, String data){
		try {
			Stat stat = zk.exists(path, false);
			if(stat == null){
				//没有就创建,并写入
				zk.create(path, data.getBytes(StandardCharsets.UTF_8), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				logger.debug("新建并写入数据成功.. ");
			}else{
				//存在,更新
				zk.setData(path, data.getBytes(StandardCharsets.UTF_8), -1);
				logger.debug("更新数据成功..");
			}
		
		} catch (KeeperException | InterruptedException e) {
			logger.error("写入数据失败: 获取Zookeeper状态操作异常");
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取数据
	 * @param path
	 * @return
	 */
	public String readData(String path){
		String result = null;
		try {
			result = new String(zk.getData(path, this, null));
		} catch (KeeperException | InterruptedException e) {
			logger.error("Zookeeper获取数据出错");
			e.printStackTrace();
		}
		return result;
	}

	
	/**
	 * 关闭zookeeper连接 释放资源
	 * 
	 * 
	 */
    public void close(){  
          
        try{  
              
            zk.close();  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
          
    }  
    
    
	@Override
	public void process(WatchedEvent event) {
		try{
			if(event.getType() == Event.EventType.NodeDataChanged){
				logger.info("数据变化 : {}", readData(PATH));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ZKSysConfig conf = new ZKSysConfig("192.168.4.128:2181,192.168.4.128:2182,192.168.4.128:2183");
		conf.addOrUpdateData(PATH, "危机提高警觉");
		conf.addOrUpdateData(PATH, "敌人帮助成长");
		conf.addOrUpdateData(PATH, "逆境刺激思维");
		
		conf.close();
	}
	

}
