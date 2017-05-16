package com.elwyn.bigData.modules.population.crowd.dao;

import java.util.HashMap;
import java.util.List;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.modules.population.crowd.entity.CrowdAnalyze;
import com.rainsoft.modules.population.crowd.entity.CrowdAnalyzeTemp;
import com.rainsoft.modules.population.crowd.entity.CrowdDetail;
import com.rainsoft.modules.population.crowd.entity.CrowdHistory;
import com.rainsoft.modules.population.crowd.entity.CrowdManage;

/**
 * 
 * @Name com.rainsoft.modules.population.crowd.service.RpcCrowdManageService
 * @Description 人群管理
 * @Author xa
 * @Version 2017年3月29日 下午4:28:23
 * @Copyright 上海云辰信息科技有限公司
 */
@MyBatisDao
public interface CommunityDao {
	/**
	 * 查询小区人群明细列表
	 * 
	 * @param CrowdDetail
	 * @return
	 */
	List<CrowdDetail> getCommunityDetailList(CrowdDetail CrowdDetail);

	/**
	 * 查询小区人群列表
	 * 
	 * @param entity
	 * @return
	 */
	List<CrowdManage> getCommunityList(CrowdManage crowdManage);

	/**
	 * 获取小区人群分析数据
	 * 
	 * @param requestMap
	 * @return
	 */
	CrowdAnalyze getCommunityAnalyze(HashMap<String, String> requestMap);

	/**
	 * 获取小区高危人群列表
	 * 
	 * @param requestMap
	 * @return
	 */
	List<CrowdAnalyzeTemp> getDangerMap(HashMap<String, String> requestMap);

	/**
	 * 获取小区人群类型列表
	 * 
	 * @param requestMap
	 */
	List<CrowdAnalyzeTemp> getPersonType(HashMap<String, String> requestMap);
	/**
	 * 查询小区异常常住人群列表
	 * 
	 * @param CrowdDetail
	 * @return
	 */
	List<CrowdDetail> getAbnormalDetailList(CrowdDetail CrowdDetail);
	/**
	 * 查询小区异常常住人群总数
	 * 
	 * @param CrowdDetail
	 * @return
	 */
	int getAbnormalDetailCount(CrowdDetail CrowdDetail);
	/**
	 * 移除异常常住人群
	 * @param requestMap
	 * @return
	 */
	int deleteAbnormal(HashMap<String, String> requestMap);
	/**
	 * 小区人群明细标记更改
	 * 
	 * @param imsiCode
	 * @param signType
	 * @return
	 */
	int updateSignDetail(HashMap<String, String> requestMap);

	/**
	 * 查询小区人群历史列表
	 * 
	 * @param crowdHistory
	 * @return
	 */
	List<CrowdHistory> getCommunityHistoryList(CrowdHistory crowdHistory);
}
