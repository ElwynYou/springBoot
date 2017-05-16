package com.elwyn.bigData.modules.population.heatmap.websocket.runner;


import com.rainsoft.modules.population.heatmap.dao.oracle.HeatmapDao;
import com.rainsoft.utils.SpringBeanUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;


public class DangerAreaCountRunner implements Runnable {
	private Set<String> machineIds;
	private Map<String,Object> resultData;
	private static HeatmapDao dao = (HeatmapDao) SpringBeanUtil.getBeanById("heatmapDao");
	private String statTime;

	public DangerAreaCountRunner(Map<String, Object> resultData) throws UnsupportedEncodingException {
		this.statTime = statTime;
		this.machineIds=machineIds;
		this.resultData=resultData;
	}

	@Override
	public void run() {
		try {
			resultData.put("dangerAreaCount",dao.getDangerAreaCount(resultData));
		} catch (Exception e) {
			resultData.put("dangerAreaCount",null);
			e.printStackTrace();
		}
	}

}
