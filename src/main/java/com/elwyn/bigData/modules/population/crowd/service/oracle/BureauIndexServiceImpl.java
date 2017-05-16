package com.elwyn.bigData.modules.population.crowd.service.oracle;

import com.rainsoft.modules.population.crowd.dao.BureauIndexDao;
import com.rainsoft.modules.population.crowd.entity.TotalCount;
import com.rainsoft.modules.population.crowd.service.IBureauIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.population.crowd.service.CrowdManageServiceImpl
 * @Description
 * @Author xa
 * @Version 2017年3月29日 下午5:06:59
 * @Copyright 上海云辰信息科技有限公司
 */

@Service
public class BureauIndexServiceImpl implements IBureauIndexService {
	@Autowired
	protected BureauIndexDao dao;



	public Map<String, Object> getTotalPeopleCount(String areaCode) {
		return dao.getTotalPeopleCount(areaCode.substring(0, 4));
	}

	@Override
	public List<TotalCount> getDetailCount(String areaCode) {
		return dao.getDetailCount(areaCode.substring(0, 4));
	}
}
