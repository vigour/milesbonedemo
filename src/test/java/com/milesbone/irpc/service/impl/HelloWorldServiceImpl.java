package com.milesbone.irpc.service.impl;

import com.milesbone.irpc.service.IHelloWorld;

public class HelloWorldServiceImpl implements IHelloWorld {

	@Override
	public String hello(String name) {
		return "hello:" + name;
	}

}
