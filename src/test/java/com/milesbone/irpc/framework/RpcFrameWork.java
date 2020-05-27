package com.milesbone.irpc.framework;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;

public class RpcFrameWork {

	
	
	public static void register(final Object service, int port) throws IOException{
		if(service == null ){
			throw new IllegalArgumentException("参数错误, 服务接口不存在");
		}
		
		if(port <=0 && port >65535){
			throw new IllegalArgumentException("端口参数错误,值应大于0且小于65536");
		}
		
		System.out.println("register service " + service.getClass().getName() + " on port:" + port);
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			for (;;) {
				final Socket socket = server.accept();

				new Thread(new Runnable() {

					@Override
					public void run() {

						try {
							ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
							try {
								String methodName = input.readUTF();
								Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
								Object[] arguments = (Object[]) input.readObject();
								ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
								try {
									Method method = service.getClass().getMethod(methodName, parameterTypes);
									Object result = method.invoke(service, arguments);
									output.writeObject(result);
								} catch (Throwable t) {
									output.writeObject(t);
								} finally {
									output.close();
								}
							} finally {
								input.close();
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								socket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
				}).start();
			} 
		} finally {
//			server.close();
		}
	}
	
	
	/**
	 * 
	 * @param interfaceClass
	 * @param host
	 * @param port
	 * @return
	 */
	public static <T> T proxy(final Class<T> interfaceClass, final String host, final int port){
		
		if(interfaceClass == null){
			throw new IllegalArgumentException("参数interfaceClass 为空");
		}
		
		if(!interfaceClass.isInterface()){
			throw new IllegalArgumentException("参数interfaceClass必须为接口类型");
		}
		
		if(StringUtils.isBlank(host)){
			throw new IllegalArgumentException("参数host不能为空");
		}
		
		if(port <=0 && port >65535){
			throw new IllegalArgumentException("端口参数错误,值应大于0且小于65536");
		}
		
		System.out.println("get remote service: " + interfaceClass.getName() +" from server " + host +":" + port);
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

				Socket socket = new Socket(host, port);
				
				try {
					ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
					try {
						output.writeUTF(method.getName());
						output.writeObject(method.getParameterTypes());
						output.writeObject(args);
						ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
						try {
							Object result = input.readObject();
							if (result instanceof Throwable) {
								throw (Throwable) result;
							}
							return result;
						} finally {
							input.close();
						}
					} finally {
						output.close();
					} 
				} finally {
					socket.close();
				}
				
				
			}
		});
	}
	
}
