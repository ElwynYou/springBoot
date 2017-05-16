package com.elwyn.bigData.modules.population.crowd.service.oracle;


import com.rainsoft.modules.population.crowd.dao.EmphasisAreaRefreshDao;
import com.rainsoft.modules.population.crowd.entity.EmphasisRefresh;
import com.rainsoft.modules.population.heatmap.dao.oracle.HeatmapDao;
import com.rainsoft.modules.population.heatmap.entity.TheHeat;
import com.rainsoft.utils.DateUtil;
import com.rainsoft.utils.SpringBeanUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * @Name com.rainsoft.modules.population.crowd.service.oracle.EmphasisAreaServiceImplTest
 * @Description
 * @Author Elwyn
 * @Version 2017/4/21
 * @Copyright 上海云辰信息科技有限公司
 **/
public class EmphasisAreaServiceImplTest {
	@org.junit.Test
	public void initEmphasisAreaIndex() throws Exception {


	}

	@org.junit.Test

	public void aa() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml", "classpath:applicationContext-datasource.xml"});

		HeatmapDao dao = (HeatmapDao) applicationContext.getBean("heatmapDao");
		Set<String> machineIdsByConfigId = dao.getMachineIdsByConfigId("59");
		Map<String,Object> resultData = dao.getMaxTime("1");
		String beginTime = (String) resultData.get("beginTime");
		String endTime = (String) resultData.get("endTime");
		Map<String,Object> timeMap=new HashMap<>();
		timeMap.put("machineIds",machineIdsByConfigId);
		timeMap.put("beginTime",DateUtil.getDateByString(beginTime,"yyyy-MM-dd HH:mm",-1,Calendar.DATE));
		timeMap.put("endTime",DateUtil.getDateByString(endTime,"yyyy-MM-dd HH:mm",-1,Calendar.DATE));
		resultData.put("totalPersonPreDay",dao.getTotalPerson(timeMap));
		timeMap.put("beginTime",DateUtil.getDateByString(beginTime,"yyyy-MM-dd HH:mm",-7,Calendar.DATE));
		timeMap.put("endTime",DateUtil.getDateByString(endTime,"yyyy-MM-dd HH:mm",-7,Calendar.DATE));
		resultData.put("totalPersonPreWeek",dao.getTotalPerson(timeMap));
		timeMap.put("beginTime",DateUtil.getDateByString(beginTime,"yyyy-MM-dd HH:mm",-1,Calendar.YEAR));
		timeMap.put("endTime",DateUtil.getDateByString(endTime,"yyyy-MM-dd HH:mm",-1,Calendar.YEAR));
		resultData.put("totalPersonPreYear",dao.getTotalPerson(timeMap));
		//Map<String,Object> time = heatmapDao.getMaxTime("5");
	}



}