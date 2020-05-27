package com.milesbone.irpc.client;

import com.milesbone.irpc.framework.RpcFrameWork;
import com.milesbone.irpc.service.IHelloWorld;

public class HelloClient {

	
	public static void main(String[] args) throws InterruptedException {
		IHelloWorld helloWorld = RpcFrameWork.proxy(IHelloWorld.class, "127.0.0.1", 8088);
//		 for (int i = 0; i < Integer.MAX_VALUE; i ++) {  
		 	for (int i = 0; i < 10; i ++) {  
	            String hello = helloWorld.hello("World" + i);  
	            System.out.println(hello);  
	            Thread.sleep(1000);  
	        } 
	}
}
