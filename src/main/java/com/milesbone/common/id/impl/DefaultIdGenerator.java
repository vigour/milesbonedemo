package com.milesbone.common.id.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.milesbone.common.id.IIdGenerator;
import com.milesbone.common.id.IIdGeneratorConfig;

/**
 * 默认的ID生成器, 采用前缀+时间+原子数的形式实现 建议相同的配置采用同一个实例
 * 
 * @author miles
 * @date 2017-04-09 下午9:48:34
 */
public class DefaultIdGenerator implements IIdGenerator, Runnable {

	private String time;

	private AtomicInteger value;

	private static final DateFormat YMDHMS_FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");

	private IIdGeneratorConfig config;

	private Thread thread;

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * 
	 */
	public DefaultIdGenerator() {
		super();
		config = new DefaultIdGeneratorConfig();
		time = YMDHMS_FORMATTER.format(new Date());

		value = new AtomicInteger(config.getInitial());

		thread = new Thread(this);
		thread.setDaemon(true);

		thread.start();
	}

	/**
	 * @param config
	 */
	public DefaultIdGenerator(IIdGeneratorConfig config) {
		super();
		this.config = config;

		time = YMDHMS_FORMATTER.format(new Date());
		value = new AtomicInteger(config.getInitial());

		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 * config.getRollingInterval());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			String now = YMDHMS_FORMATTER.format(new Date());
			if (!now.equals(time)) {
				lock.writeLock().lock();
				time = now;
				value.set(config.getInitial());
				lock.writeLock().unlock();
			}

		}

	}

	@Override
	public String next() {
		lock.readLock().lock();
		StringBuffer sb = new StringBuffer(config.getPrefix());
		sb.append(config.getSpiltString());
		sb.append(time).append(config.getSpiltString());
		sb.append(String.format("%08d", value.getAndIncrement()));
		lock.readLock().unlock();
		return sb.toString();
	}

}
