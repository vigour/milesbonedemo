package com.milesbone.login.async;

public abstract class CallBackBody {

	public void onSuccess(Object context){
		System.out.println("onSuccess");
	}
	
	
	public void onFailure(Object context){
		System.out.println("onFailure");
    }
 
    abstract void execute(Object context) throws Exception;
	
}
