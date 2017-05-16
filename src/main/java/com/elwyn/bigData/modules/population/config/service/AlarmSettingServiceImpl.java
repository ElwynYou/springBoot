package com.elwyn.bigData.modules.population.config.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rainsoft.core.service.BaseCrudService;
import com.rainsoft.model.ServiceInfo;
import com.rainsoft.modules.population.config.dao.AlarmSettingDao;
import com.rainsoft.modules.population.config.entity.AlarmSetting;
import com.rainsoft.utils.Utils;

/**
 * 报警设置管理Service
 * @author Sugar
 * @version 2017-04-05
 */
@Service
public class AlarmSettingServiceImpl extends BaseCrudService<AlarmSettingDao, AlarmSetting> implements IAlarmSettingService {

	@Override
	public List<AlarmSetting> findList(AlarmSetting entity) {
		entity.setAreaCode(Utils.parseArea(entity.getAreaCode()));
		return super.findList(entity);
	}

	
	@Override
	public List<ServiceInfo> findServiceInfoList(ServiceInfo info) {
		info.setAreaCode(Utils.parseArea(info.getAreaCode()));
		return dao.findServiceInfoList(info);
	}
}