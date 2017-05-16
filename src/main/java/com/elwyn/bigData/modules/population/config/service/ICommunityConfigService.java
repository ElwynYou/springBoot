package com.elwyn.bigData.modules.population.config.service;

import java.util.List;

import com.rainsoft.core.service.ICrudService;
import com.rainsoft.model.ServiceInfo;
import com.rainsoft.modules.population.config.entity.CommunityConfig;

/**
 * 小区设置Service
 * @author Sugar
 * @version 2017-04-14
 */
public interface ICommunityConfigService extends ICrudService<CommunityConfig>  {
	/**
	 * 获取场所
	 * @param info
	 * @return
	 */
	List<ServiceInfo> findServiceInfoList(ServiceInfo info);
}