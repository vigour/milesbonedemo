package com.milesbone.asyn;

public class Remote {

	public void executeMessage(String msg, CallBack callBack) {
		/** 模拟远程类正在处理其他事情，可能需要花费许多时间 **/
		for (int i = 0; i < 1000000000; i++) {

		}
		/** 处理完其他事情，现在来处理消息 **/
		System.out.println(msg);
		System.out.println("I hava executed the message by Local");
		/** 执行回调 **/
		callBack.execute(new String[] { "Nice to meet you~!" });
	}
}
