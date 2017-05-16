package com.elwyn.bigData.modules.population.heatmap.dao.oracle;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.core.paging.Page;
import com.rainsoft.modules.population.heatmap.entity.*;
import com.rainsoft.modules.population.query.entity.CrowdQuery;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@MyBatisDao
public interface HeatmapDao {
	//热力图首页
	//初始化
	List<TheHeat> initHeatMapIndex(Map<String, Object> map);

	//具体页面明细
	//初始化查询
	List<TheHeat> initHeatmap(Map<String, Object> map);
	List<Map<String,Object>> initTotalPerson(Map<String, Object> map);
	List<TheHeat> initDangerAreaCount(Map<String, Object> map);
	List<TheHeat> initDangerPersonCount(Map<String, Object> map);
	List<TheHeat> initSuspiciousPeople(Map<String, Object> map);
	TheHeat initHeatClock(Map<String, Object> map);

	//定时刷新
	List<TheHeat> findHeatmap(Map<String, Object> map);
	String getTotalPerson(Map<String, Object> map);
	List<TheHeat> getDangerAreaCount(Map<String, Object> map);
	List<TheHeat> getDangerPersonCount(Map<String, Object> map);
	List<TheHeat> getSuspiciousPeople(Map<String, Object> map);
	TheHeat getHeatClock(Map<String, Object> map);
	Set<String> getMachineIdsByConfigId(@Param("configId") String configId);
	Map<String,Object> getMaxTime(@Param("interval") String interval);




	List<HeatingDistribution> getHeatmapConfig(Page page);


	/**
	 * 人群明细查询
	 * @param query
	 * @return
	 */
	List<ScanEndingImproves> findCowrdList(CrowdQuery query);



	
	
	/**
	 * 按天查询热力分布历史列表
	 * @param heatMapHistory
	 * @return
	 */
	List<HeatMapHistory> getHeatMapHistoryListByDay(HeatMapHistory heatMapHistory);
	/**
	 * 按月查询热力分布历史列表
	 * @param heatMapHistory
	 * @return
	 */
	List<HeatMapHistory> getHeatMapHistoryListByMonth(HeatMapHistory heatMapHistory);
	/**
	 * 按年查询热力分布历史列表
	 * @param heatMapHistory
	 * @return
	 */
	List<HeatMapHistory> getHeatMapHistoryListByYear(HeatMapHistory heatMapHistory);
	/**
	 * 获取可疑人群列表
	 * @param heatMapSuspicious
	 * @return
	 */
	List<HeatMapSuspicious> getHeatMapSuspicious(HeatMapSuspicious heatMapSuspicious);
	/**
	 * 更新可疑人群状态
	 * @param requestMap
	 * @return
	 */
	int updateSuspicious(HashMap<String, String> requestMap);
	/**
	 * 人群明细按天查询
	 * @param heatMapDetail
	 * @return
	 */
	List<HeatMapDetail> getHeatMapDetailByDayList(HeatMapDetail heatMapDetail);
	/**
	 * 人群明细按月查询
	 * @param heatMapDetail
	 * @return
	 */
	List<HeatMapDetail> getHeatMapDetailByMonthList(HeatMapDetail heatMapDetail);
	/**
	 * 人群明细按年查询
	 * @param heatMapDetail
	 * @return
	 */
	List<HeatMapDetail> getHeatMapDetailByYearList(HeatMapDetail heatMapDetail);

}
