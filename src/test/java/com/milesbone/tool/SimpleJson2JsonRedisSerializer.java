package com.milesbone.tool;

import java.nio.charset.Charset;

import org.json.simple.JSONValue;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;


public class SimpleJson2JsonRedisSerializer<T> implements RedisSerializer<T> {
 
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
 
 
    public SimpleJson2JsonRedisSerializer() {
    	 super();
	}

 
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSONValue.toJSONString(t).getBytes(DEFAULT_CHARSET);
    }
 
    @SuppressWarnings("unchecked")
	@Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
 
		return (T) JSONValue.parse(str);
    }
 
}
