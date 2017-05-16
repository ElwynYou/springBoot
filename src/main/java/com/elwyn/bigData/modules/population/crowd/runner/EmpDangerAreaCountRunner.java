package com.elwyn.bigData.modules.population.crowd.runner;


import com.rainsoft.modules.population.crowd.dao.EmphasisAreaRefreshDao;
import com.rainsoft.utils.SpringBeanUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public class EmpDangerAreaCountRunner implements Runnable {
	private Map<String,Object> resultData;
	private static EmphasisAreaRefreshDao dao = (EmphasisAreaRefreshDao) SpringBeanUtil.getBeanById("emphasisAreaRefreshDao");

	public EmpDangerAreaCountRunner(Map<String, Object> resultData) throws UnsupportedEncodingException {
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
