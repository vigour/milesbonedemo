package com.milesbone.observer;

import java.util.Observable;
import java.util.Observer;

public class MessageWatcher implements Observer{

	@Override
	public void update(Observable o, Object arg) {

		System.out.println("Update() called, count is "      
                + ((Integer) arg).intValue());
	}

}
