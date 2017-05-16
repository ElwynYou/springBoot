package com.elwyn.bigData.modules.population.config.service;

import java.util.List;

import com.rainsoft.core.service.ICrudService;
import com.rainsoft.model.ServiceInfo;
import com.rainsoft.modules.population.config.entity.AlarmSetting;

/**
 * @Name com.rainsoft.modules.population.config.service.IAlarmSettingService
 * @Description 
 * 
 * @Author Sugar
 * @Version 2017年4月5日 下午12:56:12
 * @Copyright 上海云辰信息科技有限公司
 */
public interface IAlarmSettingService extends ICrudService<AlarmSetting> {
	/**
	 * 获取场所
	 * @param info
	 * @return
	 */
	List<ServiceInfo> findServiceInfoList(ServiceInfo info);
}
