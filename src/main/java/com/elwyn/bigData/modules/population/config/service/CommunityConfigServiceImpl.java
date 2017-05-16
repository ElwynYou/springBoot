package com.elwyn.bigData.modules.population.config.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rainsoft.core.service.BaseCrudService;
import com.rainsoft.model.ServiceInfo;
import com.rainsoft.modules.population.config.dao.CommunityConfigDao;
import com.rainsoft.modules.population.config.entity.CommunityConfig;
import com.rainsoft.utils.Utils;

/**
 * 小区设置Service
 * @author Sugar
 * @version 2017-04-14
 */
@Service
@Transactional(readOnly = true)
public class CommunityConfigServiceImpl extends BaseCrudService<CommunityConfigDao, CommunityConfig> implements ICommunityConfigService {

	
	@Override
	public List<ServiceInfo> findServiceInfoList(ServiceInfo info) {
		info.setAreaCode(Utils.parseArea(info.getAreaCode()));
		return dao.findServiceInfoList(info);
	}
		
}