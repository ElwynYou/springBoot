package com.elwyn.modules.population.crowd.websocket;


import com.alibaba.fastjson.JSONObject;
import com.rainsoft.core.utils.SpringBeanUtil;
import com.rainsoft.modules.population.crowd.entity.EmphasisRefresh;
import com.rainsoft.modules.population.crowd.service.IEmphasisAreaService;
import com.rainsoft.modules.population.heatmap.websocket.HeatMapScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/emphasisIndex")
public class EmphasisWebSocketIndexHandle {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final Set<Session> connections = new CopyOnWriteArraySet<>();//所有客户端连接实例
	private Session session;
	private static HeatMapScheduler heatMapScheduler;
	private static IEmphasisAreaService emphasisAreaService = (IEmphasisAreaService) SpringBeanUtil.getBeanById("emphasisAreaService");

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		heatMapScheduler = HeatMapScheduler.getInstance();
		connections.add(this.session);
		List<EmphasisRefresh> emphasisRefreshes = emphasisAreaService.initEmphasisAreaIndex("init", null);
		try {
			this.session.getBasicRemote().sendText(JSONObject.toJSONString(emphasisRefreshes));
			logger.debug("初始化重点区域主页数据:" + JSONObject.toJSONString(emphasisRefreshes) + "发送到" + session.getId() + "当前连接数" + connections.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("重点区域主页新的连接:" + session.getId() + "当前连接数" + connections.size());
	}

	@OnMessage
	public void onMessage(String interval) {
		logger.debug("重点区域主页收到:" + this.session.getId() + "的消息:" + interval + ",当前连接数" + connections.size());
		//添加后自动开始任务如果任务存在会修改触发时间
		String jobName = "empjob" + this.session.getId();
		if ("none".equals(interval)) {
			heatMapScheduler.removeJob(jobName, jobName, jobName, jobName);
		} else {
			heatMapScheduler.addJob(jobName, jobName, jobName, jobName, EmphasisIndexJob.class, null, interval, null);
		}
	}

	@OnError
	public void onError(Throwable t) {
		logger.debug(t.getMessage());
		sendMessage(t.getMessage());
		connections.remove(this.session);
		String jobName = "empjob" + this.session.getId();
		heatMapScheduler.removeJob(jobName, jobName, jobName, jobName);
	}

	@OnClose
	public void onClose() {
		connections.remove(this.session);
		String jobName = "empjob" + this.session.getId();
		heatMapScheduler.removeJob(jobName, jobName, jobName, jobName);
		logger.debug("关闭重点区域主页session:" + this.session.getId() + "当前连接数" + connections.size());
	}

	private static void sendMessage(String msg) {
		for (Session client : connections) {
			try {
				synchronized (client) {
					client.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
				connections.remove(client);
				try {
					client.close();
				} catch (IOException e1) {
				}
				String message = String.format("%s %s", client.getId(), "has been disconnected.");
				sendMessage(message);
			}
		}
	}

	public static Set<Session> getConnections() {
		return connections;
	}
}