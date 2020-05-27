package com.milesbone.common.id;

public interface IIdGenerator {

	
	/**
	 * 生成下一个不重复流水号
	 * @return
	 */
	String next();
}
