package com.elwyn.modules.population.websocket.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Name com.rainsoft.modules.population.websocket.netty.TextWebSocketFrameHandler
 * @Description
 * @Author Elwyn
 * @Version 2017/5/10
 * @Copyright 上海云辰信息科技有限公司
 **/


public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			if (channel != incoming) {
				channel.writeAndFlush(new TextWebSocketFrame("[" + incoming.remoteAddress() + "]" + msg.text()));
			} else {
				channel.writeAndFlush(new TextWebSocketFrame("[服务器端返回]：" + msg.text()));

				//output current message to context.
				logger.debug("服务端返回"+ String.valueOf(incoming.remoteAddress()) + "->" + msg.text());
			}
		}
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));
		}
		channels.add(ctx.channel());
		logger.debug("Client:" + incoming.remoteAddress() + "加入");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));
		}
		logger.debug("Client:" + incoming.remoteAddress() + "离开");
		channels.remove(ctx.channel());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		logger.debug("Client:" + incoming.remoteAddress() + "在线");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		logger.debug("Client:" + incoming.remoteAddress() + "掉线");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		Channel incoming = ctx.channel();
		logger.debug("Client:" + incoming.remoteAddress() + "异常");
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}

}