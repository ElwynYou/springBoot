package com.elwyn.bigData.modules.population.crowd.runner;


import com.rainsoft.modules.population.crowd.dao.EmphasisAreaRefreshDao;
import com.rainsoft.utils.SpringBeanUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public class EmpSuspiciousPeopleRunner implements Runnable {
	private Map<String,Object> resultData;
	private static EmphasisAreaRefreshDao dao = (EmphasisAreaRefreshDao) SpringBeanUtil.getBeanById("emphasisAreaRefreshDao");
	public EmpSuspiciousPeopleRunner(Map<String, Object> resultData) throws UnsupportedEncodingException {
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
