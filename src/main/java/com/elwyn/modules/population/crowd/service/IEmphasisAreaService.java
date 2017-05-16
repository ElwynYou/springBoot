package com.elwyn.modules.population.crowd.service;


import com.rainsoft.core.paging.Page;
import com.rainsoft.core.service.IService;
import com.rainsoft.modules.population.crowd.entity.EmphasisAreaDetail;
import com.rainsoft.modules.population.crowd.entity.EmphasisAreaHistory;
import com.rainsoft.modules.population.crowd.entity.EmphasisAreaSuspicious;
import com.rainsoft.modules.population.crowd.entity.EmphasisRefresh;
import com.rainsoft.modules.population.query.entity.CrowdQuery;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Name com.rainsoft.modules.population.crowd.service.RpcCrowdManageService
 * @Description rpc人群管理服务
 * @Author xa
 * @Version 2017年3月29日 下午4:28:23
 * @Copyright 上海云辰信息科技有限公司
 */
public interface IEmphasisAreaService extends IService{
	/**
	 * 查询热力分布历史
	 * @param emphasisAreaHistory
	 * @return
	 */
	List<EmphasisAreaHistory> getEmphasisAreaHistory(EmphasisAreaHistory emphasisAreaHistory);
	/**
	 * 可疑人群查询
	 * @param emphasisAreaSuspicious
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<EmphasisAreaSuspicious> getEmphasisAreaSuspicious(EmphasisAreaSuspicious emphasisAreaSuspicious, Integer pageNo, Integer pageSize);
	/**
	 * 可疑人群状态更新
	 * @param requestMap
	 * @return
	 */
	int updateSuspicious(HashMap<String, String> requestMap);
	/**
	 * 人群明细按天查询
	 * @param emphasisAreaDetail
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<EmphasisAreaDetail> getEmphasisAreaDetailByDay(EmphasisAreaDetail emphasisAreaDetail, Integer pageNo, Integer pageSize);
	/**
	 * 人群明细按月查询
	 * @param emphasisAreaDetail
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<EmphasisAreaDetail> getEmphasisAreaDetailByMonth(EmphasisAreaDetail emphasisAreaDetail, Integer pageNo, Integer pageSize);
	/**
	 * 人群明细按年查询
	 * @param emphasisAreaDetail
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<EmphasisAreaDetail> getEmphasisAreaDetailByYear(EmphasisAreaDetail emphasisAreaDetail, Integer pageNo, Integer pageSize);
	/**
	 * 人群查询
	 * @param query
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<ScanEndingImproves> getEmphasisAreaCrowdQuery(CrowdQuery query, Integer pageNo, Integer pageSize);

	/**
	 * 重点区域首页
	 */
	List<EmphasisRefresh>initEmphasisAreaIndex(String init, String interval);

	/**
	 * 重点区域明细
	 */
	Map<String,Object> initEmphasisAreaDetail(String init, String interval, String serviceCode);
}
