package com.elwyn.bigData.modules.population.config.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rainsoft.core.service.BaseCrudService;
import com.rainsoft.model.ServiceInfo;
import com.rainsoft.modules.population.config.dao.EmphasisAreaConfigDao;
import com.rainsoft.modules.population.config.entity.EmphasisAreaConfig;
import com.rainsoft.utils.Utils;

/**
 * 重点区域设置Service
 * @author Sugar
 * @version 2017-04-14
 */
@Service
public class EmphasisAreaConfigServiceImpl extends BaseCrudService<EmphasisAreaConfigDao, EmphasisAreaConfig>  implements IEmphasisAreaConfigService{

	
	@Override
	public List<ServiceInfo> findServiceInfoList(ServiceInfo info) {
		info.setAreaCode(Utils.parseArea(info.getAreaCode()));
		return dao.findServiceInfoList(info);
	}
		
}