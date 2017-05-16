package com.elwyn.modules.population.heatmap.websocket;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.core.utils.SpringBeanUtil;
import com.rainsoft.modules.population.heatmap.entity.TheHeat;
import com.rainsoft.modules.population.heatmap.service.IHeatMapService;
import org.quartz.*;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @Name com.rainsoft.modules.population.heatmap.websocket.HeatMapDetailJob
 * @Description
 * @Author Elwyn
 * @Version 2017/4/13
 * @Copyright 上海云辰信息科技有限公司
 **/
@DisallowConcurrentExecution
public class HeatMapIndexJob implements Job {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(HeatMapIndexJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
			IHeatMapService heatMapService = (IHeatMapService) SpringBeanUtil.getBeanById("heatMapService");
			List<TheHeat> theHeatList = heatMapService.initHeatMapIndex(null,jobDataMap.getString("interval"));
			Set<Session> connections = HeatMapWebSocketIndexHandle.getConnections();
			if (connections != null && !connections.isEmpty()) {
				for (Session connection : connections) {
					connection.getBasicRemote().sendText(JSONObject.toJSONString(theHeatList));
					logger.debug("发送数据到session"+connection.getId()+"数据:"+ JSONObject.toJSONString(theHeatList));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
