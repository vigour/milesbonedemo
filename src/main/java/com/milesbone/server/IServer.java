package com.milesbone.server;

import java.net.InetSocketAddress;

public interface IServer {
	public interface TransmissionProtocol{

    }

    // 服务器使用的协议
    public enum TRANSMISSION_PROTOCOL implements TransmissionProtocol {
        TCP,UDP
    }

    TransmissionProtocol getTransmissionProtocol();
	
	/**
	 * 启动服务器
	 * @throws Exception
	 */
	public void startServer() throws Exception;
	
	
	/**
	 * 启动服务器
	 * @param port
	 * @throws Exception
	 */
	public void startServer(int port) throws Exception;
	
	
	/**
	 * 启动服务器
	 * @param port
	 * @throws Exception
	 */
	public void startServer(InetSocketAddress address) throws Exception;

	/**
	 * 停止服务器
	 * @param port
	 * @throws Exception
	 */
	public void stopServer() throws Exception;
	
	
	
	InetSocketAddress getSocketAddress();
}
