package com.milesbone.asyn;


/**
 * 回调函数
 * @author miles
 * @date 2017-04-09 下午5:20:08
 */
public interface CallBack {

	/**
	 *  执行回调方法 
	 * @param objects   将处理后的结果作为参数返回给回调方法
	 */
	public void execute(Object... objects );
}
