package com.milesbone.sysuer.listener;

import org.springframework.context.ApplicationEvent;

import com.milesbone.sysuer.entity.Sysuser;

public class SysuserListenerEvent extends ApplicationEvent {


	private static final long serialVersionUID = -7959267659615023096L;
	
	private Sysuser sysuser;

	public SysuserListenerEvent(Object source) {
		super(source);
	}
	

	public SysuserListenerEvent(Object source, Sysuser sysuser) {
		super(source);
		this.sysuser = sysuser;
	}


	public Sysuser getSysuser() {
		return sysuser;
	}

	public void setSysuser(Sysuser sysuser) {
		this.sysuser = sysuser;
	}
	
	
}
