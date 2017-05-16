package com.elwyn.bigData.modules.population.crowd.dao;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.modules.population.crowd.entity.EmphasisRefresh;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Name com.rainsoft.modules.population.crowd.dao.EmphasisAreaRefreshDao
 * @Description
 * @Author Elwyn
 * @Version 2017/4/21
 * @Copyright 上海云辰信息科技有限公司
 **/
@MyBatisDao
public interface EmphasisAreaRefreshDao {
	List<EmphasisRefresh> initEmphasisAreaIndex(Map<String, Object> map);

	Map<String,Object> getMaxTime(@Param("interval") String interval);

	Set<String> getMachineIdsByserviceCode(String serviceCode);

	//明细刷新
	List<EmphasisRefresh> getDangerAreaCount(Map<String, Object> map);

	List<EmphasisRefresh> getDangerPersonCount(Map<String, Object> map);

	List<EmphasisRefresh> getSuspiciousPeople(Map<String, Object> map);

	List<EmphasisRefresh> emphasisArea(Map<String, Object> map);

	String getTotalPerson(Map<String, Object> map);

	//明细初始化
	List<EmphasisRefresh> initDangerAreaCount(Map<String, Object> map);

	List<EmphasisRefresh> initDangerPersonCount(Map<String, Object> map);

	List<EmphasisRefresh> initSuspiciousPeople(Map<String, Object> map);

	List<Map<String, Object>> initTotalPerson(Map<String, Object> map);

}
