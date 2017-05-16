package com.elwyn.bigData.modules.population.analyze.dao.oracle;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.modules.population.analyze.entity.Frequency;

import java.util.List;
import java.util.Map;

/**
 * @Name IFrequencyDao
 * @Description
 * 
 * @Author Sugar
 * @Version 2017年4月7日 下午3:34:12
 * @Copyright 上海云辰信息科技有限公司
 */
@MyBatisDao
public interface IFrequencyDao {
	public List<Map<String, Object>> timeAnalyze(Frequency entity);

	public List<Map<String, Object>> placeAnalyze(Frequency entity);
}
