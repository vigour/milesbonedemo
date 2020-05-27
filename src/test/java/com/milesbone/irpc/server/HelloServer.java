package com.milesbone.irpc.server;

import java.io.IOException;

import com.milesbone.irpc.framework.RpcFrameWork;
import com.milesbone.irpc.service.IHelloWorld;
import com.milesbone.irpc.service.impl.HelloWorldServiceImpl;

public class HelloServer {

	
	public static void main(String[] args) throws IOException {
		IHelloWorld helloWorld = new HelloWorldServiceImpl();
		RpcFrameWork.register(helloWorld, 8088);
	}
}
