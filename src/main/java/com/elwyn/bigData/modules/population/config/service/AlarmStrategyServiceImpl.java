package com.elwyn.bigData.modules.population.config.service;

import org.springframework.stereotype.Service;

import com.rainsoft.core.service.BaseCrudService;
import com.rainsoft.modules.population.config.dao.AlarmStrategyDao;
import com.rainsoft.modules.population.config.entity.AlarmStrategy;

/**
 * 报警策略管理Service
 * @author Sugar
 * @version 2017-04-05
 */
@Service
public class AlarmStrategyServiceImpl extends BaseCrudService<AlarmStrategyDao, AlarmStrategy> implements IAlarmStrategyService {

	@Override
	public boolean save(AlarmStrategy entity) {
		return super.save(entity);
	}
		
}