package com.elwyn.bigData.modules.population.query.service.oracle;

import com.rainsoft.model.User;
import com.rainsoft.modules.bigdata.dao.oracle.GetMachineInfoDao;
import com.rainsoft.modules.population.query.dao.oracle.TraceQueryDao;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;
import com.rainsoft.modules.population.query.service.ITraceQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.populationMovement.service
 * @Description
 * @Author Elwyn
 * @Version 2017/2/15 18:14
 * @Copyright 上海云辰信息科技有限公司
 */
@Service
public class TraceQueryServiceImpl implements ITraceQueryService {

	@Autowired
	TraceQueryDao traceQueryDao;

	@Autowired
	GetMachineInfoDao getMachineInfoDao;

	public List<ScanEndingImproves> getTraceQuery(@SuppressWarnings("rawtypes") Map map, User user) {
		/*List<String> serviceCodeByArea = GetServiceCodesByUser.getServiceCodeByArea(user, getMachineInfoDao);
		if (serviceCodeByArea != null && !serviceCodeByArea.isEmpty()) {
			map.put("serviceCodes", serviceCodeByArea);
		}*/

		return traceQueryDao.getIMSITraceQueryList(map);

	}

}
