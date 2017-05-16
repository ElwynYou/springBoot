package com.elwyn.modules.population.crowd.service;


import com.rainsoft.core.paging.Page;
import com.rainsoft.core.service.IService;
import com.rainsoft.modules.population.crowd.entity.CrowdAnalyze;
import com.rainsoft.modules.population.crowd.entity.CrowdDetail;
import com.rainsoft.modules.population.crowd.entity.CrowdHistory;
import com.rainsoft.modules.population.crowd.entity.CrowdManage;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @Name com.rainsoft.modules.population.crowd.service.RpcCrowdManageService
 * @Description rpc人群管理服务
 * @Author xa
 * @Version 2017年3月29日 下午4:28:23
 * @Copyright 上海云辰信息科技有限公司
 */
public interface ICommunityService extends IService{
	/**
	 * 查询小区人群明细
	 * @param crowdDetail
	 * @param pageNo
	 * @param pageSize
	 * @return Page<CrowdDetail>
	 */
	Page<CrowdDetail> getCommunityDetail(CrowdDetail crowdDetail, Integer pageNo, Integer pageSize);
	/**
	 * 查询小区人群管理
	 * @param entity
	 * @param pageNo
	 * @param pageSize
	 * @return Page<CrowdManage>
	 */
	Page<CrowdManage> getCommunityManage(CrowdManage crowdManage, Integer pageNo, Integer pageSize);
	/**
	 *  查询小区人群明细
	 * @param requestMap
	 * @return CrowdAnalyze
	 */
	CrowdAnalyze getAnalyze(HashMap<String, String> requestMap);
	/**
	 * 查询异常常住人群明细
	 * @param crowdDetail
	 * @param pageNo
	 * @param pageSize
	 * @return Page<CrowdDetail>
	 */
	Page<CrowdDetail> getCommunityAbnormalDetail(CrowdDetail crowdDetail, Integer pageNo, Integer pageSize);
	/**
	 * 删除异常常住人群
	 * @param imsiCode
	 * @param peopleType
	 * @return
	 */
	int deleteAbnormal(HashMap<String, String> requestMap);
	/**
	 * 小区人群明细标记更改
	 * @param imsiCode
	 * @param peopleType
	 * @return
	 */
	int updateSignDetail(HashMap<String, String> requestMap);
	
	/**
	 * 查询小区人群历史列表
	 * @param crowdHistory
	 * @return
	 */
	List<CrowdHistory> getCommunityHistory(CrowdHistory crowdHistory);
}
