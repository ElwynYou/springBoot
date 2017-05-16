package com.elwyn.bigData.modules.population.query.service.oracle;

import com.rainsoft.core.paging.Page;
import com.rainsoft.model.User;
import com.rainsoft.modules.bigdata.dao.oracle.GetMachineInfoDao;
import com.rainsoft.modules.population.crowd.dao.EmphasisAreaDao;
import com.rainsoft.modules.population.query.dao.oracle.QueryAnalysisDao;
import com.rainsoft.modules.population.query.dao.oracle.TraceQueryDao;
import com.rainsoft.modules.population.query.entity.AnalysisMachine;
import com.rainsoft.modules.population.query.entity.CrowdQuery;
import com.rainsoft.modules.population.query.entity.MachineCollisionQuery;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;
import com.rainsoft.modules.population.query.service.IQueryAnalysisService;
import com.rainsoft.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Name com.rainsoft.modules.population.analyze.service.oracle.
 *       queryAnalysisServiceImpl
 * @Description
 * @Author Elwyn
 * @Version 2017/3/29
 * @Copyright 上海云辰信息科技有限公司
 **/
@Service
public class QueryAnalysisServiceImpl implements IQueryAnalysisService {

	@Autowired
	private QueryAnalysisDao queryAnalysisDao;

	@Autowired
	EmphasisAreaDao iCrowdManageDao;

	@Autowired
	TraceQueryDao traceQueryDao;

	@Autowired
	GetMachineInfoDao getMachineInfoDao;

	@Override
	public Page<ScanEndingImproves> findCowrdList(CrowdQuery query, Integer pageNo, Integer pageSize){
		Page<ScanEndingImproves> page = new Page<ScanEndingImproves>(pageSize, pageNo);
		query.setAreaCode(Utils.parseArea(query.getAreaCode()));
		query.setPhoneArea(Utils.parseArea(query.getPhoneArea()));
		query.setPage(page);
		page.setList(queryAnalysisDao.findCowrdList(query));
		return page;
	}

	@Override
	public Page<AnalysisMachine> findMachineList(AnalysisMachine analysisMachine, Integer pageNo, Integer pageSize) {
		Page<AnalysisMachine> page = new Page<AnalysisMachine>(pageSize, pageNo);
		analysisMachine.setAreaCode(Utils.parseArea(analysisMachine.getAreaCode()));
		analysisMachine.setPage(page);
		page.setList(queryAnalysisDao.getMachineList(analysisMachine));
		return page;
	}

	@Override
	public List<AnalysisMachine> machineMapQuery(AnalysisMachine analysisMachine, User user) {
		String region = user.getRegion();
		if (region != null && !"".equals(region)) {
			analysisMachine.setAreaCode(Utils.parseArea(region));
		}
		List<AnalysisMachine> machineMapList = queryAnalysisDao.getMachineMapList(analysisMachine);
		return machineMapList;
	}

	@Override
	public Page<ScanEndingImproves> collisionQuery(MachineCollisionQuery scanEndingImproves, Integer pageNo, Integer pageSize) {
		Page<ScanEndingImproves> page = new Page(pageSize, pageNo);
		scanEndingImproves.setPage(page);
		page.setList(queryAnalysisDao.collisionQuery(scanEndingImproves));
		return page;

	}

	@Override
	public List<AnalysisMachine> getMachine(String region) {
		return queryAnalysisDao.getMachine(Utils.parseArea(region));
	}
}
