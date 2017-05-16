package com.elwyn.bigData.modules.population.heatmap.websocket.runner;


import com.rainsoft.modules.population.heatmap.dao.oracle.HeatmapDao;
import com.rainsoft.utils.DateUtil;
import com.rainsoft.utils.SpringBeanUtil;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



public class TotalPersonRunner implements Runnable {
	private static HeatmapDao dao = (HeatmapDao) SpringBeanUtil.getBeanById("heatmapDao");
	private Map<String,Object> resultData;
	public TotalPersonRunner(Map<String, Object> resultData) throws UnsupportedEncodingException {
		this.resultData=resultData;
	}

	@Override
	public void run() {
		try {
			resultData.put("totalPerson",dao.getTotalPerson(resultData));
			String beginTime = (String) resultData.get("beginTime");
			String endTime = (String) resultData.get("endTime");
			Map<String,Object> timeMap=new HashMap<>();
			timeMap.put("machineIds",resultData.get("machineIds"));
			timeMap.put("beginTime",DateUtil.getDateByString(beginTime,"yyyy-MM-dd HH:mm",-1,Calendar.DATE));
			timeMap.put("endTime",DateUtil.getDateByString(endTime,"yyyy-MM-dd HH:mm",-1,Calendar.DATE));
			resultData.put("totalPersonPreDay",dao.getTotalPerson(timeMap));
			timeMap.put("beginTime",DateUtil.getDateByString(beginTime,"yyyy-MM-dd HH:mm",-7,Calendar.DATE));
			timeMap.put("endTime",DateUtil.getDateByString(endTime,"yyyy-MM-dd HH:mm",-7,Calendar.DATE));
			resultData.put("totalPersonPreWeek",dao.getTotalPerson(timeMap));
			timeMap.put("beginTime",DateUtil.getDateByString(beginTime,"yyyy-MM-dd HH:mm",-1,Calendar.YEAR));
			timeMap.put("endTime",DateUtil.getDateByString(endTime,"yyyy-MM-dd HH:mm",-1,Calendar.YEAR));
			resultData.put("totalPersonPreYear",dao.getTotalPerson(timeMap));
		} catch (Exception e) {
			resultData.put("totalPerson",null);
			e.printStackTrace();
		}
	}

}
