package com.elwyn.modules.population.heatmap.websocket;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.core.utils.SpringBeanUtil;
import com.rainsoft.modules.population.heatmap.service.IHeatMapService;
import org.quartz.*;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @Name com.rainsoft.modules.population.heatmap.websocket.HeatMapDetailJob
 * @Description
 * @Author Elwyn
 * @Version 2017/4/13
 * @Copyright 上海云辰信息科技有限公司
 **/
@DisallowConcurrentExecution
public class HeatMapDetailJob implements Job {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(HeatMapDetailJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
			String configId = jobDataMap.getString("configId");
			IHeatMapService heatMapService = (IHeatMapService) SpringBeanUtil.getBeanById("heatMapService");
			Map<String, Object> heatMapDatas = heatMapService.getHeatMapDatas(configId,jobDataMap.getString("interval"));
			Map<String, Set<Session>> configIdSessionMap = WebSocketSessionFactory.getConfigIdSessionMap();
			Set<Session> sessions = configIdSessionMap.get(configId);
			if(sessions!=null && !sessions.isEmpty()){
				for (Session session : sessions) {
					session.getBasicRemote().sendText(JSONObject.toJSONString(heatMapDatas));
					logger.debug("发送数据到session"+session.getId()+"数据:"+ JSONObject.toJSONString(heatMapDatas));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
