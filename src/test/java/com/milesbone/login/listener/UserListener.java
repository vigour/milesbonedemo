package com.milesbone.login.listener;

public interface UserListener {

	void actionPerformed(UserEventSource e);

	void actionPerformed(UserEventSource userEventSource, Object data);  
}
