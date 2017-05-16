package com.elwyn.bigData.modules.population.config.service;

import com.rainsoft.core.service.ICrudService;
import com.rainsoft.modules.population.config.entity.DangerPersonGroup;

/**
 * @Name 
 *       com.rainsoft.modules.population.config.service.IDangerPersonGroupService
 * @Description
 * 
 * @Author Sugar
 * @Version 2017年4月14日 下午5:26:51
 * @Copyright 上海云辰信息科技有限公司
 */
public interface IDangerPersonGroupService extends ICrudService<DangerPersonGroup> {
	int checkName(String name, String id);

	DangerPersonGroup getByName(String name);
}
