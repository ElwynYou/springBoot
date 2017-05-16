package com.elwyn.bigData.modules.population.crowd.dao;

import java.util.HashMap;
import java.util.List;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.modules.population.crowd.entity.EmphasisAreaDetail;
import com.rainsoft.modules.population.crowd.entity.EmphasisAreaHistory;
import com.rainsoft.modules.population.crowd.entity.EmphasisAreaSuspicious;
import com.rainsoft.modules.population.query.entity.CrowdQuery;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;
/**
 * 
 * @Name com.rainsoft.modules.population.crowd.service.RpcCrowdManageService
 * @Description 人群管理
 * @Author xa
 * @Version 2017年3月29日 下午4:28:23
 * @Copyright 上海云辰信息科技有限公司
 */
@MyBatisDao
public interface EmphasisAreaDao {
	
	
	/**
	 * 按天查询热力分布历史列表
	 * @param EmphasisAreaHistory
	 * @return
	 */
	List<EmphasisAreaHistory> getEmphasisAreaHistoryListByDay(EmphasisAreaHistory emphasisAreaHistory);
	/**
	 * 按月查询热力分布历史列表
	 * @param EmphasisAreaHistory
	 * @return
	 */
	List<EmphasisAreaHistory> getEmphasisAreaHistoryListByMonth(EmphasisAreaHistory emphasisAreaHistory);
	/**
	 * 按年查询热力分布历史列表
	 * @param EmphasisAreaHistory
	 * @return
	 */
	List<EmphasisAreaHistory> getEmphasisAreaHistoryListByYear(EmphasisAreaHistory emphasisAreaHistory);
	/**
	 * 获取可疑人群列表
	 * @param EmphasisAreaSuspicious
	 * @return
	 */
	List<EmphasisAreaSuspicious> getEmphasisAreaSuspicious(EmphasisAreaSuspicious emphasisAreaSuspicious);
	/**
	 * 更新可疑人群状态
	 * @param requestMap
	 * @return
	 */
	int updateSuspicious(HashMap<String, String> requestMap);
	/**
	 * 人群明细按天查询
	 * @param EmphasisAreaDetail
	 * @return
	 */
	List<EmphasisAreaDetail> getEmphasisAreaDetailByDayList(EmphasisAreaDetail emphasisAreaDetail);
	/**
	 * 人群明细按月查询
	 * @param EmphasisAreaDetail
	 * @return
	 */
	List<EmphasisAreaDetail> getEmphasisAreaDetailByMonthList(EmphasisAreaDetail emphasisAreaDetail);
	/**
	 * 人群明细按年查询
	 * @param EmphasisAreaDetail
	 * @return
	 */
	List<EmphasisAreaDetail> getEmphasisAreaDetailByYearList(EmphasisAreaDetail emphasisAreaDetail);
	/**
	 * 人群明细查询
	 * @param query
	 * @return
	 */
	List<ScanEndingImproves> findCowrdList(CrowdQuery query);
}
