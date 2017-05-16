package com.elwyn.bigData.modules.population.config.service;

import com.rainsoft.core.service.ICrudService;
import com.rainsoft.modules.population.config.entity.DangerPerson;

/**
 * 高危人群库管理Service
 * 
 * @author Sugar
 * @version 2017-03-31
 */
public interface IDangerPersonService extends ICrudService<DangerPerson> {
	/**
	 * 判断是存在
	 * @param person
	 * @return
	 */
	boolean exists(DangerPerson person);

}