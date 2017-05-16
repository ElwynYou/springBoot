package com.elwyn.bigData.modules.population.query.dao.oracle;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.modules.population.query.entity.AnalysisMachine;
import com.rainsoft.modules.population.query.entity.CrowdQuery;
import com.rainsoft.modules.population.query.entity.MachineCollisionQuery;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;

import java.util.List;

/**
 * @Name com.rainsoft.modules.population.analyze.dao.oracle.QueryAnalysis
 * @Description
 * @Author Elwyn
 * @Version 2017/3/29
 * @Copyright 上海云辰信息科技有限公司
 **/
@MyBatisDao
public interface QueryAnalysisDao {
 	List<ScanEndingImproves> findCowrdList(CrowdQuery query);


	List<AnalysisMachine> getMachineList(AnalysisMachine analysisMachine);
	List<AnalysisMachine> getMachineMapList(AnalysisMachine analysisMachine);

	List<ScanEndingImproves> collisionQuery(MachineCollisionQuery machineCollisionQuery);

	List<AnalysisMachine> getMachine(String areaCode);
}
