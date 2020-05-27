package com.milesbone.rpc.common;

import java.util.List;

import com.milesbone.rpc.util.SerializationUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;


/**
 * 接口反序列化处理
 * @author miles
 * @date 2017-06-08 下午5:01:42
 */
public class RpcDecoder extends ByteToMessageDecoder {
	
	
	private Class<?> genericClass;
	

	/**
	 * 
	 */
	public RpcDecoder() {
		super();
	}

	
	


	/**
	 * @param genericClass
	 */
	public RpcDecoder(Class<?> genericClass) {
		super();
		this.genericClass = genericClass;
	}





	@Override
	protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(in.readableBytes() < 4){
			return;
		}
		
		in.markReaderIndex();
		
		int dataLength = in.readInt();
		
		if(dataLength < 0){
			ctx.close();
		}
		if(in.readableBytes() < dataLength){
			in.resetReaderIndex();
		}
		
		byte[] data = new byte[dataLength];
		in.readBytes(data);
		
		Object obj = SerializationUtil.deserialize(data, genericClass);
		out.add(obj);
	}

	
}
