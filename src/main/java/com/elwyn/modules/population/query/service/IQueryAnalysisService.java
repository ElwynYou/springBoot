package com.elwyn.modules.population.query.service;

import com.rainsoft.core.paging.Page;
import com.rainsoft.core.service.ICrudService;
import com.rainsoft.model.User;
import com.rainsoft.modules.population.query.entity.AnalysisMachine;
import com.rainsoft.modules.population.query.entity.CrowdQuery;
import com.rainsoft.modules.population.query.entity.MachineCollisionQuery;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;

import java.util.List;

/**
 * @Name IQueryAnalysisService
 * @Description
 * @Author Elwyn
 * @Version 2017/3/29
 * @Copyright 上海云辰信息科技有限公司
 **/
public interface IQueryAnalysisService extends ICrudService<AnalysisMachine> {

	Page<ScanEndingImproves> findCowrdList(CrowdQuery query, Integer pageNo, Integer pageSize);
	Page<AnalysisMachine> findMachineList(AnalysisMachine query, Integer pageNo, Integer pageSize);

	List<AnalysisMachine> machineMapQuery(AnalysisMachine analysisMachine, User user);

	Page<ScanEndingImproves> collisionQuery(MachineCollisionQuery machineCollisionQuery, Integer pageNo, Integer pageSize);
	List<AnalysisMachine> getMachine(String region);
}
