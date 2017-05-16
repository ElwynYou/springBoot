package com.elwyn.bigData.modules.population.config.dao;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.core.dao.IBaseDao;
import com.rainsoft.modules.population.config.entity.AlarmStrategy;

/**
 * 报警策略管理DAO接口
 * @author Sugar
 * @version 2017-04-05
 */
@MyBatisDao
public interface AlarmStrategyDao extends IBaseDao<AlarmStrategy> {
	
}