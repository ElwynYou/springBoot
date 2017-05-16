package com.elwyn.bigData.modules.population.config.dao;

import java.util.List;
import java.util.Map;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.modules.population.config.entity.HeatmapConfig;

@MyBatisDao
public interface HeatmapConfigDao {
	
	 List<HeatmapConfig> findList(HeatmapConfig config);

	void addHeatMapConfig(HeatmapConfig heatmapConfig);

	void addHeatmapConfigDevice(Map map);

	void delHeatingConfig(String configId);
	void delHeatmapConfigDevice(String configId);

	int updateStatus(Map<String, String> map);

	int updateHeatmapConfig(HeatmapConfig heatmapConfig);

	HeatmapConfig getHeatMapConfigById(String id);

	List<String> getMachineIds(String id);
	/*
	//先删除子菜单
	void delHeatmapConfigSubmenu(String url);
	//再删除主菜单
	void delHeatingConfigMenu(String url);
	void delPermission(Map map);*/


}
