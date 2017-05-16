package com.elwyn.modules.population.config.service;

import com.rainsoft.core.paging.Page;
import com.rainsoft.model.JsonResult;
import com.rainsoft.model.User;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.population.config.entity.HeatmapConfig;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Name com.rainsoft.modules.population.config.service.IHeatmapConfigService
 * @Description
 * 
 * @Author Sugar
 * @Version 2017年3月29日 下午1:15:52
 * @Copyright 上海云辰信息科技有限公司
 */
public interface IHeatmapConfigService {

	Page<HeatmapConfig> findPage(HeatmapConfig entity, Integer pageNo, Integer pageSize);

	// 添加热力分布配置
	JsonResult addHeatmapConfig(HeatmapConfig heatmapConfig);

	// 检查设备
	List<MachineInfo> checkMachine(User user, @RequestBody Map<String, Object> map);

	void delHeatingConfig(String configId);

	int updateStatus(String id, String status);

	JsonResult updateHeatmapConfig(HeatmapConfig heatmapConfig);

	HeatmapConfig getHeatmapConfigById(String id);
}
