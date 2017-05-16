package com.elwyn.modules.population.heatmap.service;

import com.rainsoft.core.paging.Page;
import com.rainsoft.modules.population.heatmap.entity.HeatMapDetail;
import com.rainsoft.modules.population.heatmap.entity.HeatMapHistory;
import com.rainsoft.modules.population.heatmap.entity.HeatMapSuspicious;
import com.rainsoft.modules.population.heatmap.entity.TheHeat;
import com.rainsoft.modules.population.query.entity.CrowdQuery;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IHeatMapService {

	Map<String,Object> initHeatMapDatas(String id);
	Map<String,Object> getHeatMapDatas(String id, String interval);
	List<TheHeat> initHeatMapIndex(String init, String interval);


	/**
	 * 查询热力分布历史
	 * @param heatMapHistory
	 * @return
	 */
	List<HeatMapHistory> getHeatMapHistory(HeatMapHistory heatMapHistory);
	/**
	 * 可疑人群查询
	 * @param heatMapSuspicious
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<HeatMapSuspicious> getHeatMapSuspicious(HeatMapSuspicious heatMapSuspicious, Integer pageNo, Integer pageSize);
	/**
	 * 可疑人群状态更新
	 * @param requestMap
	 * @return
	 */
	int updateSuspicious(HashMap<String, String> requestMap);

	/**
	 * 人群明细按天查询
	 * @param heatMapDetail
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<HeatMapDetail> getHeatMapDetailByDay(HeatMapDetail heatMapDetail, Integer pageNo, Integer pageSize);
	/**
	 * 人群明细按月查询
	 * @param heatMapDetail
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<HeatMapDetail> getHeatMapDetailByMonth(HeatMapDetail heatMapDetail, Integer pageNo, Integer pageSize);
	/**
	 * 人群明细按年查询
	 * @param heatMapDetail
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<HeatMapDetail> getHeatMapDetailByYear(HeatMapDetail heatMapDetail, Integer pageNo, Integer pageSize);
	/**
	 * 人群查询
	 * @param query
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<ScanEndingImproves> getHeatMapCrowdQuery(CrowdQuery query, Integer pageNo, Integer pageSize);
}
