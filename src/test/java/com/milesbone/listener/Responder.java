package com.milesbone.listener;

//Someone interested in "Hello" events
public class Responder implements HelloListener {

	public void someoneSaidHello() {
		System.out.println("Hello there...");
	}

}
