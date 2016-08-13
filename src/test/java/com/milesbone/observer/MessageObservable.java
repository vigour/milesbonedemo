package com.milesbone.observer;

import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 受观察者
 * @author miles
 * @date 2016-08-10 下午3:04:16
 */
public class MessageObservable extends Observable{

	private static final Logger logger = LoggerFactory.getLogger(MessageObservable.class);
	
	public MessageObservable() {
		super();
	}
	
	
	public void count(int period){
		while(period >=0 ){
			setChanged();
			
			notifyObservers(period);
			
			try {
				Thread.sleep(100);
				period--;
			} catch (InterruptedException e) {
				logger.error("操作异常");
			}
		}
	}
	
	
	public static void main(String[] args) {
		MessageWatcher mw = new MessageWatcher();
		MessageObservable mo = new MessageObservable();
		
		mo.addObserver(mw);
		logger.info(mo.countObservers() + "");
		mo.count(10);
		
	}

}
