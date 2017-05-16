package com.elwyn.bigData.modules.population.heatmap.websocket.runner;


import com.rainsoft.modules.population.heatmap.dao.oracle.HeatmapDao;
import com.rainsoft.utils.SpringBeanUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public class SuspiciousPeopleRunner implements Runnable {
	private Map<String,Object> resultData;
	private static HeatmapDao dao = (HeatmapDao) SpringBeanUtil.getBeanById("heatmapDao");

	public SuspiciousPeopleRunner(Map<String, Object> resultData) throws UnsupportedEncodingException {
		this.resultData=resultData;
	}

	@Override
	public void run() {
		try {
			resultData.put("suspiciousPeople",dao.getSuspiciousPeople(resultData));
		} catch (Exception e) {
			resultData.put("suspiciousPeople",null);
			e.printStackTrace();
		}
	}

}
