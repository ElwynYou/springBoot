package com.elwyn.modules.population.heatmap.websocket;


import com.alibaba.fastjson.JSONObject;
import com.rainsoft.core.utils.SpringBeanUtil;
import com.rainsoft.modules.population.heatmap.entity.TheHeat;
import com.rainsoft.modules.population.heatmap.service.IHeatMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/heatMapIndex")
public class HeatMapWebSocketIndexHandle {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final Set<Session> CONNECTIONS = new CopyOnWriteArraySet<>();//所有客户端连接session
	private Session session;
	private static HeatMapScheduler heatMapScheduler;
	private static IHeatMapService heatMapService= (IHeatMapService) SpringBeanUtil.getBeanById("heatMapService");

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		heatMapScheduler = HeatMapScheduler.getInstance();
		CONNECTIONS.add(this.session);
		List<TheHeat> theHeatList = heatMapService.initHeatMapIndex("init",null);
		try {
			this.session.getBasicRemote().sendText(JSONObject.toJSONString(theHeatList));
			logger.debug("初始化主页数据:"+ JSONObject.toJSONString(theHeatList)+"发送到"+ session.getId() + "当前连接数" + CONNECTIONS.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("主页新的连接:" + session.getId() + "当前连接数" + CONNECTIONS.size());
	}

	@OnMessage
	public void onMessage(String interval) {
		logger.debug("主页收到:" + this.session.getId() + "的消息:" + interval + ",当前连接数" + CONNECTIONS.size());
		//添加后自动开始任务如果任务存在会修改触发时间
		String jobName="heatmapJob"+this.session.getId();
		if("none".equals(interval)){
			heatMapScheduler.removeJob(jobName,jobName,jobName,jobName);
		}else {
			heatMapScheduler.addJob(jobName,jobName,jobName,jobName,HeatMapIndexJob.class,null,interval,null);
		}
	}

	@OnError
	public void onError(Throwable t) {
		logger.debug(t.getMessage());
		sendMessage(t.getMessage());
		CONNECTIONS.remove(this.session);
		String jobName="heatmapJob"+this.session.getId();
		heatMapScheduler.removeJob(jobName,jobName,jobName,jobName);
	}

	@OnClose
	public void onClose() {
		CONNECTIONS.remove(this.session);
			String jobName="heatmapJob"+this.session.getId();
			heatMapScheduler.removeJob(jobName,jobName,jobName,jobName);
		logger.debug("关闭主页session:" + this.session.getId() + "当前连接数" + CONNECTIONS.size());
	}

	private static void sendMessage(String msg) {
		for (Session client : CONNECTIONS) {
			try {
				synchronized (client) {
					client.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
				CONNECTIONS.remove(client);
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
		return CONNECTIONS;
	}
}