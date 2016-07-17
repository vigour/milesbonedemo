package com.milesbone.queue;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LightQueueSynced {

	
	private static final Logger logger = LoggerFactory.getLogger(LightQueueSynced.class);
	
	private static LinkedList<String> QUEUE_LIST = new LinkedList<String>();

    public static List<String> getQueueList(){
        return QUEUE_LIST;
    }

    public static synchronized void queue(String key){
        if(!StringUtils.isBlank(key)&&!QUEUE_LIST.contains(key)){
            QUEUE_LIST.add(key);
        }
        logger.debug("key : {} push in to queue", key );
    }

    public static synchronized void remove(String key){
    	logger.debug("key : {} poll out from queue", key );
        QUEUE_LIST.remove(key);
    }

    public static synchronized String pollFirst(){
        if(QUEUE_LIST.size()==0){
            return null;
        }
        String seatNo = QUEUE_LIST.get(0);
        if(!StringUtils.isBlank(seatNo)){
            QUEUE_LIST.remove(0);
        }
        return seatNo;
    }
}
