package com.milesbone.test;

public class MyThread implements Runnable {
	
	private String name;
	
	

	public void setName(String name) {
		this.name = name;
	}



	@Override
	public void run() {
		System.out.println("Thread " + this.name + " is running.....");
	}

}
