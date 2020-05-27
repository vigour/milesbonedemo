package com.milesbone.asyn;

public class Local implements CallBack, Runnable {

	private Remote remote;

	private String message;

	/**
	 * @param remote
	 * @param message
	 */
	public Local(Remote remote, String message) {
		super();
		this.remote = remote;
		this.message = message;
	}

	@Override
	public void run() {
		remote.executeMessage(message, this);
	}

	@Override
	public void execute(Object... objects) {
		/** 打印返回的消息 **/
		System.out.println(objects[0]);
		/** 打印发送消息的线程名称 **/
		System.out.println(Thread.currentThread().getName());
		/** 中断发送消息的线程 **/
		Thread.interrupted();
	}
	
	
	
	/**
	 * 发送消息
	 */
	public void sendMessage(){
		System.out.println(Thread.currentThread().getName());
		
		Thread thread = new Thread(this);
		thread.start();
		
		System.out.println("Message has been sent by Local~!");
	}

	public static void main(String[] args) {
		Local local = new Local(new Remote(), "hello");
		
		local.sendMessage();
	}

}
