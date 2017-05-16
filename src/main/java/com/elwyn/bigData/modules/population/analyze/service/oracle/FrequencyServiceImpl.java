package com.elwyn.bigData.modules.population.analyze.service.oracle;

import com.rainsoft.modules.population.analyze.dao.oracle.IFrequencyDao;
import com.rainsoft.modules.population.analyze.entity.Frequency;
import com.rainsoft.modules.population.analyze.service.IFrequencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Name 
 *       FrequencyServiceImpl
 * @Description
 * 
 * @Author Sugar
 * @Version 2017年4月7日 下午3:20:34
 * @Copyright 上海云辰信息科技有限公司
 */
@Service
public class FrequencyServiceImpl implements IFrequencyService {
	@Autowired
	private IFrequencyDao dao;

	@Override
	public List<Map<String, Object>> timeAnalyze(Frequency entity) {
		return dao.timeAnalyze(entity);
	}

	@Override
	public List<Map<String, Object>> placeAnalyze(Frequency entity) {
		return dao.placeAnalyze(entity);
	}
}
