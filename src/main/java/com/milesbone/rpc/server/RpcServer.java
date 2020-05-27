package com.milesbone.rpc.server;

import java.util.HashMap;
import java.util.Map;


import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.milesbone.rpc.annotation.RpcService;
import com.milesbone.rpc.common.RpcDecoder;
import com.milesbone.rpc.common.RpcEncoder;
import com.milesbone.rpc.common.RpcRequest;
import com.milesbone.rpc.common.RpcResponse;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class RpcServer implements ApplicationContextAware,InitializingBean  {

	private static final Logger LOGGER = LoggerFactory.getLogger(RpcServer.class);

	private String serverAddress;

	private ServiceRegistry serviceRegistry;
	
	private Map<String, Object> handlerMap = new HashMap<String, Object>();


	/**
	 * @param serverAddress
	 */
	public RpcServer(String serverAddress) {
		super();
		this.serverAddress = serverAddress;
	}

	/**
	 * @param serverAddress
	 * @param serviceRegistry
	 */
	public RpcServer(String serverAddress, ServiceRegistry serviceRegistry) {
		super();
		this.serverAddress = serverAddress;
		this.serviceRegistry = serviceRegistry;
	}

	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// 获取所有带有 RpcService 注解的 Spring Bean
		Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
		
		if(MapUtils.isNotEmpty(serviceBeanMap)){
			for(Object serviceBean : serviceBeanMap.values()){
				String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
				handlerMap.put(interfaceName, serviceBean);
			}
		}
	}

//	@Override
//	public void contextInitialized(ServletContextEvent sce) {
//		EventLoopGroup bossGroup = new NioEventLoopGroup();
//		EventLoopGroup workerGroup = new NioEventLoopGroup();
//		
//		try {
//			ServerBootstrap bootstrap = new ServerBootstrap();
//			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
//					.childHandler(new ChannelInitializer<SocketChannel>() {
//
//						@Override
//						protected void initChannel(SocketChannel channel) throws Exception {
//							channel.pipeline().addLast(new RpcDecoder(RpcRequest.class))// 将 RPC 请求进行解码（为了处理请求）
//									.addLast(new RpcEncoder(RpcResponse.class))// 将 RPC 响应进行编码（为了返回响应）
//									.addLast(new RpcHandler(handlerMap));// 处理 RPC 请求
//						}
//					}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
//			String[] array = serverAddress.split(":");
//			if (array == null) {
//				LOGGER.error("serverAddress参数格式有误");
//				throw new IllegalArgumentException("serverAddress参数格式有误");
//			}
//			String host = array[0];
//			int port = Integer.parseInt(array[1]);
//			ChannelFuture future = bootstrap.bind(host, port).sync();
//			LOGGER.debug("server started on port {}", port);
//			if (serviceRegistry != null) {
//				serviceRegistry.register(serverAddress);
//			}
//			future.channel().closeFuture().sync();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} finally {
//			workerGroup.shutdownGracefully();
//			bossGroup.shutdownGracefully();
//		}
//	}
//
//	@Override
//	public void contextDestroyed(ServletContextEvent sce) {
//		// TODO Auto-generated method stub
//		
//	}
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel channel) throws Exception {
							channel.pipeline().addLast(new RpcDecoder(RpcRequest.class))// 将 RPC 请求进行解码（为了处理请求）
									.addLast(new RpcEncoder(RpcResponse.class))// 将 RPC 响应进行编码（为了返回响应）
									.addLast(new RpcHandler(handlerMap));// 处理 RPC 请求
						}
					}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
			String[] array = serverAddress.split(":");
			if (array == null) {
				LOGGER.error("serverAddress参数格式有误");
				throw new IllegalArgumentException("serverAddress参数格式有误");
			}
			String host = array[0];
			int port = Integer.parseInt(array[1]);
			ChannelFuture future = bootstrap.bind(host, port).sync();
			LOGGER.debug("server started on port {}", port);
			if (serviceRegistry != null) {
				serviceRegistry.register(serverAddress);
			}
			future.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
	}

	

}
