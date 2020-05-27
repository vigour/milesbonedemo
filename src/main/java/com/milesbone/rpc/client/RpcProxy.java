package com.milesbone.rpc.client;

import java.lang.reflect.Method;
import java.util.UUID;

import com.milesbone.rpc.common.RpcRequest;
import com.milesbone.rpc.common.RpcResponse;

import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;

public class RpcProxy {

	
	private String serverAddress;
	
	private ServiceDiscovery serviceDiscovery;

	/**
	 * @param serverAddress
	 */
	public RpcProxy(String serverAddress) {
		super();
		this.serverAddress = serverAddress;
	}

	/**
	 * @param serviceDiscovery
	 */
	public RpcProxy(ServiceDiscovery serviceDiscovery) {
		super();
		this.serviceDiscovery = serviceDiscovery;
	}
	
	public <T> T create(Class<?> interfaceClass){
		return (T) Proxy.newProxyInstance(
				interfaceClass.getClassLoader(), 
				new Class<?>[]{interfaceClass}, 
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						RpcRequest request = new RpcRequest();
						request.setRequestId(UUID.randomUUID().toString());
						request.setClassName(method.getDeclaringClass().getName());
						request.setMethodName(method.getName());
						request.setParameterTypes(method.getParameterTypes());
						request.setParameters(args);
						
						if(serviceDiscovery != null){
							serverAddress = serviceDiscovery.discover();
						}
						
						String[] array = serverAddress.split(":");
						String host = array[0];
						int port = Integer.parseInt(array[1]);
						
						RpcClient client = new RpcClient(host, port);
						
						RpcResponse response = client.send(request);
						
						if(response.getError() != null){
							throw response.getError();
						}else{
							return response.getResult();
						}
					}
				});
	}
}
