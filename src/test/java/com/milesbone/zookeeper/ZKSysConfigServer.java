package com.milesbone.zookeeper;



/**
 * 监听服务器
 * @author miles
 *
 */
public class ZKSysConfigServer {

	private static final String PATH="/zkdatatest"; 
	
	public static void main(String[] args) throws InterruptedException {
		ZKSysConfig conf =  new ZKSysConfig("192.168.4.128:2181,192.168.4.128:2182,192.168.4.128:2183");
		conf.readData(PATH);
		System.out.println("模拟服务监听器开始监听........");
		Thread.sleep(Long.MAX_VALUE);  
		conf.close();
	}
}
