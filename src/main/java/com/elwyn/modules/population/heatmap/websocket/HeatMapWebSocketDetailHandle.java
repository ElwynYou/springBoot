package com.elwyn.modules.population.heatmap.websocket;


import com.alibaba.fastjson.JSONObject;
import com.rainsoft.core.utils.SpringBeanUtil;
import com.rainsoft.modules.population.heatmap.service.IHeatMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/heatMapDetail/{configId}")
public class HeatMapWebSocketDetailHandle {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final Set<HeatMapWebSocketDetailHandle> connections = new CopyOnWriteArraySet<>();//所有客户端连接实例
	private Session session;
	private static HeatMapScheduler heatMapScheduler;
	private static IHeatMapService heatMapService= (IHeatMapService) SpringBeanUtil.getBeanById("heatMapService");

	@OnOpen
	public void onOpen(@PathParam("configId") String configId, Session session) {
		this.session = session;
		heatMapScheduler = HeatMapScheduler.getInstance();
		connections.add(this);
		logger.debug("明细新的连接:" + session.getId() + "当前连接数" + connections.size());
		WebSocketSessionFactory.putConfigIdSessionMap(configId, session);
		Map<String, Object> map = heatMapService.initHeatMapDatas(configId);
		try {
			this.session.getBasicRemote().sendText(JSONObject.toJSONString(map));
			logger.debug("初始化明细数据:"+ JSONObject.toJSONString(map)+"发送到"+ session.getId() + "当前连接数" + connections.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnMessage
	public void onMessage(String interval) {
		logger.debug("明细收到:" + this.session.getId() + "的消息:" + interval + ",当前连接数" + connections.size());
		WebSocketSessionFactory.addSessionCornMap(this.session,interval);
		String configId = WebSocketSessionFactory.getSessionConfigIdMap().get(this.session);
		//添加后自动开始任务如果任务存在会修改触发时间
		String jobName="heatmapDetailjob"+this.session.getId();
		heatMapScheduler.addJob(jobName, jobName, jobName, jobName, HeatMapDetailJob.class, configId, interval,null);
	}

	@OnError
	public void onError(Throwable t) {
		sendMessage(t.getMessage());
	}

	@OnClose
	public void onClose() {
		WebSocketSessionFactory.removeHeatMapSession(this.session);
		String jobName="heatmapDetailjob"+this.session.getId();
		heatMapScheduler.removeJob(jobName,jobName,jobName,jobName);
		connections.remove(this);
		logger.debug("关闭明细session:" + this.session.getId() + "当前连接数" + connections.size());
		WebSocketSessionFactory.removeHeatMapSession(this.session);
	}

	private static void sendMessage(String msg) {
		for (HeatMapWebSocketDetailHandle client : connections) {
			try {
				synchronized (client) {
					client.session.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
				connections.remove(client);
				try {
					client.session.close();
				} catch (IOException e1) {
				}
				String message = String.format("%s %s", client.session.getId(), "has been disconnected.");
				sendMessage(message);
			}
		}
	}


}