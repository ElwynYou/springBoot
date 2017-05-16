package com.elwyn.modules.population.config.service;

import com.rainsoft.core.service.ICrudService;
import com.rainsoft.model.ServiceInfo;
import com.rainsoft.modules.population.config.entity.EmphasisAreaConfig;

import java.util.List;

/**
 * 重点区域设置Service
 * @author Sugar
 * @version 2017-04-14
 */
public interface IEmphasisAreaConfigService extends ICrudService<EmphasisAreaConfig> {
	/**
	 * 获取场所
	 * @param info
	 * @return
	 */
	List<ServiceInfo> findServiceInfoList(ServiceInfo info);
}