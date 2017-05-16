package com.elwyn.modules.population.config.service;

import com.rainsoft.core.service.ICrudService;
import com.rainsoft.model.ServiceInfo;
import com.rainsoft.modules.population.config.entity.CommunityConfig;

import java.util.List;

/**
 * 小区设置Service
 * @author Sugar
 * @version 2017-04-14
 */

public interface ICommunityConfigService extends ICrudService<CommunityConfig> {
	List<ServiceInfo> findServiceInfoList(ServiceInfo info);
}