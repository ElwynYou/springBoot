package com.elwyn.bigData.modules.population.crowd.runner;


import com.rainsoft.modules.population.crowd.dao.EmphasisAreaRefreshDao;
import com.rainsoft.utils.SpringBeanUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public class EmpAreaRunner implements Runnable {
	private Map<String,Object> resultData;
	private static EmphasisAreaRefreshDao dao = (EmphasisAreaRefreshDao) SpringBeanUtil.getBeanById("emphasisAreaRefreshDao");

	public EmpAreaRunner(Map<String, Object> resultData) throws UnsupportedEncodingException {
		this.resultData=resultData;
	}

	@Override
	public void run() {
		try {
			resultData.put("empArea",dao.emphasisArea(resultData));
		} catch (Exception e) {
			resultData.put("empArea",null);
			e.printStackTrace();
		}
	}

}
