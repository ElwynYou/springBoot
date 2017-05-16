package com.elwyn.modules.population.config.service;

import com.rainsoft.core.service.ICrudService;
import com.rainsoft.modules.population.config.entity.DangerPerson;

/**
 * 高危人群库管理Service
 * 
 * @author Sugar
 * @version 2017-03-31
 */
public interface IDangerPersonService extends ICrudService<DangerPerson> {
	boolean exists(DangerPerson entity);
}