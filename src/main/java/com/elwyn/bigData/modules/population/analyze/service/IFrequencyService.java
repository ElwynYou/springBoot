package com.elwyn.bigData.modules.population.analyze.service;

import com.rainsoft.core.service.IService;
import com.rainsoft.modules.population.analyze.entity.Frequency;

import java.util.List;
import java.util.Map;

/**
 * @Name IFrequencyService
 * @Description
 * 
 * @Author Sugar
 * @Version 2017年4月7日 下午3:12:23
 * @Copyright 上海云辰信息科技有限公司
 */
public interface IFrequencyService extends IService {
	/**
	 * 时间分析
	 * 
	 * @param entity
	 *            分析条件
	 * @return
	 */
	public List<Map<String, Object>> timeAnalyze(Frequency entity);

	/**
	 * 地点分析
	 * 
	 * @param entity
	 *            分析条件
	 * @return
	 */
	public List<Map<String, Object>> placeAnalyze(Frequency entity);
}
