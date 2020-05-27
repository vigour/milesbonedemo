package com.milesbone.rpc.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

import com.milesbone.rpc.common.RpcRequest;
import com.milesbone.rpc.common.RpcResponse;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest>{

	
	private static final Logger LOGGER = LoggerFactory.getLogger(RpcHandler.class);
	
	private final Map<String, Object> handlerMap;
	
	
	
	/**
	 * @param handlerMap
	 */
	public RpcHandler(Map<String, Object> handlerMap) {
		super();
		this.handlerMap = handlerMap;
	}




	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
		RpcResponse response = new RpcResponse();
		response.setRequestId(request.getRequestId());
		
		try {
			Object result = handle(request);
			response.setResult(result);
		} catch (Throwable t) {
			response.setError(t);
		}
		
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}




	private Object handle(RpcRequest request) throws Throwable {
		String className = request.getClassName();
		Object serviceBean = handlerMap.get(className);
		
		Class<?> serviceClass = serviceBean.getClass();
		String methodName = request.getMethodName();
		Class<?>[] parameterTypes = request.getParameterTypes();
		Object[] parameters = request.getParameters();
		
//		Method method = serviceClass.getMethod(methodName, parameterTypes);
//
//        method.setAccessible(true);
//
//        return method.invoke(serviceBean, parameters);
		
		FastClass serviceFastClass = FastClass.create(serviceClass);
		FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
		return serviceFastMethod.invoke(serviceBean, parameters);
	}
	
	
	 public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        LOGGER.error("server caught exception", cause);
	        ctx.close();
	    }

}
