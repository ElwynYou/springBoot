package com.elwyn.modules.population.crowd.websocket;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.core.utils.SpringBeanUtil;
import com.rainsoft.modules.population.crowd.service.IEmphasisAreaService;
import org.quartz.*;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @Name com.rainsoft.modules.population.heatmap.websocket.HeatMapDetailJob
 * @Description
 * @Author Elwyn
 * @Version 2017/4/21
 * @Copyright 上海云辰信息科技有限公司
 **/
@DisallowConcurrentExecution
public class EmphasisDetailJob implements Job {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EmphasisDetailJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
			IEmphasisAreaService emphasisAreaService = (IEmphasisAreaService) SpringBeanUtil.getBeanById("emphasisAreaService");
			Map<String,Object> emphasisRefreshes = emphasisAreaService.initEmphasisAreaDetail(null,jobDataMap.getString("interval"),jobDataMap.getString("serviceCode"));
			Set<Session> connections = EmphasisWebSocketDetailHandle.getConnections();
			if (connections != null && !connections.isEmpty()) {
				for (Session connection : connections) {
					connection.getBasicRemote().sendText(JSONObject.toJSONString(emphasisRefreshes));
					logger.debug("重点区域:发送数据到session"+connection.getId()+"数据:"+ JSONObject.toJSONString(emphasisRefreshes));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
