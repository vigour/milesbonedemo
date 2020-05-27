package com.milesbone.rpc;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.milesbone.base.AbstactBeanTestCase;
import com.milesbone.rpc.client.RpcProxy;
import com.milesbone.rpc.service.IHelloService;


public class RpcClientTest extends AbstactBeanTestCase{

	@Autowired
	private RpcProxy rpcProxy;
	
	@Test
	public void testHello(){
		IHelloService helloService = rpcProxy.create(IHelloService.class);
		for(int i = 0; i < 1000000; i++){
			logger.info("--------------------");
			logger.info(helloService.hello("hahahahah"));
			System.out.println(helloService.getUser("aa"));
			logger.info("--------------------");
		}
	}
	
}
