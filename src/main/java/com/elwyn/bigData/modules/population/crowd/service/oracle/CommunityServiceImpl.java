package com.elwyn.bigData.modules.population.crowd.service.oracle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rainsoft.core.paging.Page;
import com.rainsoft.modules.population.crowd.dao.CommunityDao;
import com.rainsoft.modules.population.crowd.entity.CrowdAnalyze;
import com.rainsoft.modules.population.crowd.entity.CrowdDetail;
import com.rainsoft.modules.population.crowd.entity.CrowdHistory;
import com.rainsoft.modules.population.crowd.entity.CrowdManage;
import com.rainsoft.modules.population.crowd.service.ICommunityService;
import com.rainsoft.utils.Utils;

/**
 * @Name com.rainsoft.modules.population.crowd.service.CrowdManageServiceImpl
 * @Description
 * @Author xa
 * @Version 2017年3月29日 下午5:06:59
 * @Copyright 上海云辰信息科技有限公司
 */

@Service
public class CommunityServiceImpl implements ICommunityService {
	@Autowired
	protected CommunityDao dao;

	@Override
	public Page<CrowdManage> getCommunityManage(CrowdManage crowdManage, Integer pageNo, Integer pageSize) {
		Page<CrowdManage> page = new Page<CrowdManage>(pageSize, pageNo);
		crowdManage.setAreaCode(Utils.parseArea(crowdManage.getAreaCode()));
		crowdManage.setPage(page);
		page.setList(dao.getCommunityList(crowdManage));
		return page;
	}

	@Override
	public Page<CrowdDetail> getCommunityDetail(CrowdDetail crowdDetail, Integer pageNo, Integer pageSize) {
		Page<CrowdDetail> page = new Page<CrowdDetail>(pageSize, pageNo);
		crowdDetail.setPage(page);
		page.setList(dao.getCommunityDetailList(crowdDetail));
		return page;
	}

	@Override
	public CrowdAnalyze getAnalyze(HashMap<String, String> requestMap) {
		CrowdAnalyze crowdAnalyze = dao.getCommunityAnalyze(requestMap);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		CrowdDetail crowdDetail =new CrowdDetail();
		crowdDetail.setQueryTime(sdf.format(new Date()));
		crowdDetail.setServiceCode(requestMap.get("serviceCode"));
		if(crowdAnalyze == null ){
			CrowdAnalyze cal = new CrowdAnalyze();
			cal.setAbnormalCount(dao.getAbnormalDetailCount(crowdDetail)+"");
			cal.setDangerAreaCount("0");
			cal.setDangerPersonCount("0");
			cal.setTempPeopleNum("0");
			cal.setUnmeantNum("0");
			return cal;
		}
		crowdAnalyze.setDangerMap(dao.getDangerMap(requestMap));
		crowdAnalyze.setPersonTypeMap(dao.getPersonType(requestMap));
		crowdAnalyze.setAbnormalCount(dao.getAbnormalDetailCount(crowdDetail)+"");
		return crowdAnalyze;
	}

	@Override
	public int updateSignDetail(HashMap<String, String> requestMap) {
		return dao.updateSignDetail(requestMap);
	}

	@Override
	public List<CrowdHistory> getCommunityHistory(CrowdHistory crowdHistory) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String beginTimeStr = crowdHistory.getBeginTime();
		String overTimeStr = crowdHistory.getOverTime();
		Date beginTime = null;
		try {
			overTimeStr = sdf.format(sdf.parse(overTimeStr));
			beginTime = sdf.parse(beginTimeStr);
			beginTimeStr =sdf.format(beginTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Map<String, CrowdHistory> hm = new LinkedHashMap<String, CrowdHistory>();
		while (!beginTimeStr.equals(overTimeStr)) {
			CrowdHistory ch = new CrowdHistory();
			ch.setCaptureTime(beginTimeStr);
			hm.put(beginTimeStr, ch);
			beginTime = new Date(beginTime.getTime() + 24*60*60*1000);
			beginTimeStr =sdf.format(beginTime);
		}
		CrowdHistory ch = new CrowdHistory();
		ch.setCaptureTime(beginTimeStr);
		hm.put(beginTimeStr, ch);
		List<CrowdHistory> crowdHistoryList = dao.getCommunityHistoryList(crowdHistory);
		for(CrowdHistory crowdHistoryTemp :crowdHistoryList){
			String key = null;
			try {
				key = sdf.format(sdf.parse(crowdHistoryTemp.getCaptureTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			hm.put(key, crowdHistoryTemp);
		}
		return new ArrayList<CrowdHistory>(hm.values());
	}

	
	@Override
	public Page<CrowdDetail> getCommunityAbnormalDetail(CrowdDetail crowdDetail, Integer pageNo, Integer pageSize) {
		Page<CrowdDetail> page = new Page<CrowdDetail>(pageSize, pageNo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		crowdDetail.setQueryTime(sdf.format(new Date()));
		crowdDetail.setPage(page);
		page.setList(dao.getAbnormalDetailList(crowdDetail));
		return page;
	}

	
	@Override
	public int deleteAbnormal(HashMap<String, String> requestMap) {
		return dao.deleteAbnormal(requestMap);
	}
}
