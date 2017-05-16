package com.elwyn.modules.population.websocket.netty;

import com.rainsoft.utils.SysConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Name com.rainsoft.modules.population.websocket.netty.WebSocketServer
 * @Description
 * @Author Elwyn
 * @Version 2017/5/10
 * @Copyright 上海云辰信息科技有限公司
 **/

//@Component
public final class WebSocketServer extends Thread {
	private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

	/*private static WebSocketServer WEB_SOCKET_SERVER = null;

	private WebSocketServer(int port) {
		this.port = port;
	}

	public static WebSocketServer getInstance(int port) {
		if (WEB_SOCKET_SERVER == null) {
			return new WebSocketServer(port);
		}
		return WEB_SOCKET_SERVER;
	}*/

	static {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new WebSocketServerInitializer())
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			logger.debug("WebsocketChatServer 启动了");
			// 绑定端口，开始接收进来的连接
			ChannelFuture f = b.bind(Integer.parseInt(SysConfig.getConfig("websocket.port"))).sync();
			// 等待服务器 socket 关闭 。在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			logger.debug("WebsocketChatServer 关闭了");
		}
	}


}