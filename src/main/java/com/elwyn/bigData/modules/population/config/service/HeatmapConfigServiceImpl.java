package com.elwyn.bigData.modules.population.config.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.common.collect.Maps;
import com.rainsoft.core.paging.Page;
import com.rainsoft.model.User;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.bigdata.dao.oracle.GetMachineInfoDao;
import com.rainsoft.modules.common.entity.JsonResult;
import com.rainsoft.modules.population.config.dao.HeatmapConfigDao;
import com.rainsoft.modules.population.config.entity.HeatmapConfig;
import com.rainsoft.utils.Utils;

/**
 * 
 * @Name com.rainsoft.modules.population.config.service.HeatmapConfigServiceImpl
 * @Description
 * 
 * @Author Sugar
 * @Version 2017年3月29日 下午1:16:30
 * @Copyright 上海云辰信息科技有限公司
 */
@Service
public class HeatmapConfigServiceImpl implements IHeatmapConfigService {

	@Autowired
	HeatmapConfigDao heatmapConfigDao;

	@Autowired
	GetMachineInfoDao getMachineInfoDao;

	public Page<HeatmapConfig> findPage(HeatmapConfig entity, Integer pageNo, Integer pageSize) {
		Page<HeatmapConfig> page = new Page<HeatmapConfig>(pageSize, pageNo);
		entity.setPage(page);
		page.setList(heatmapConfigDao.findList(entity));
		return page;
	}

	public JsonResult addHeatmapConfig(HeatmapConfig heatmapConfig) {
		try {
			heatmapConfig.setDeviceNum(heatmapConfig.getMachineIds().size());
			heatmapConfigDao.addHeatMapConfig(heatmapConfig);
			List<String> machineIds = heatmapConfig.getMachineIds();
			Map<String, Object> map = new HashMap<>();
			map.put("id", heatmapConfig.getId());
			for (String machineId : machineIds) {
				map.put("devId", machineId);
				heatmapConfigDao.addHeatmapConfigDevice(map);
			}
			return new JsonResult(JsonResult.SUCCESS, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.ERROR, "添加失败");
		}

	}

	// 检查选中范围是否有设备
	public List<MachineInfo> checkMachine(User user, @RequestBody Map<String, Object> map) {
		String region = user.getRegion();
		region = Utils.parseArea(region);
		// 获得圆形
		List<Map> circleList = (List) map.get("ary_circle_label");
		// 获得方形
		List<Map> squareList = (List) map.get("ary_rectangle_label");
		// 根据圆形方形和serviceCode获取设备信息
		Map<String, Object> quaryMachine = Maps.newHashMap();
		quaryMachine.put("region", region);
		quaryMachine.put("circleList", circleList);
		quaryMachine.put("squareList", squareList);
		return getMachineInfoDao.getMachineInfoByMap(quaryMachine);
	}

	public void delHeatingConfig(String configId) {
		// String url="populationmovement/heatmap/"+configId;
		heatmapConfigDao.delHeatingConfig(configId);
		heatmapConfigDao.delHeatmapConfigDevice(configId);
		/*
		 * Map map= new HashMap();
		 * map.put("heatMap","populationmovement/heatmap/"+configId);
		 * map.put("crowdFlowrate"
		 * ,"populationmovement/crowdFlowrate/"+configId);
		 * map.put("crowdList","populationmovement/crowdList/"+configId);
		 * map.put("heatConfig","populationmovement/"+configId);
		 * heatingDistributionDao.delPermission(map);
		 * heatingDistributionDao.delHeatmapConfigSubmenu(url);
		 * heatingDistributionDao.delHeatingConfigMenu(url);
		 */
	}

	public int updateStatus(String id, String status) {
		Map<String, String> map = new HashMap<>();
		map.put("configId", id);
		map.put("status", status);
		return heatmapConfigDao.updateStatus(map);
	}

	public JsonResult updateHeatmapConfig(HeatmapConfig heatmapConfig) {

		heatmapConfig.setDeviceNum(heatmapConfig.getMachineIds().size());
		int i = heatmapConfigDao.updateHeatmapConfig(heatmapConfig);
		heatmapConfigDao.delHeatmapConfigDevice(String.valueOf(heatmapConfig.getId()));
		List<String> machineIds = heatmapConfig.getMachineIds();
		Map<String, Object> map = new HashMap<>();
		map.put("id", heatmapConfig.getId());
		for (String machineId : machineIds) {
			map.put("devId", machineId);
			heatmapConfigDao.addHeatmapConfigDevice(map);
		}
		if (i > 0) {
			return new JsonResult(JsonResult.SUCCESS, "修改成功");
		} else {
			return new JsonResult(JsonResult.SUCCESS, "修改失败");
		}
	}

	public HeatmapConfig getHeatmapConfigById(String id) {
		HeatmapConfig heatingDistributionById = heatmapConfigDao.getHeatMapConfigById(id);
		List<String> machineIds = heatmapConfigDao.getMachineIds(id);
		heatingDistributionById.setMachineIds(machineIds);
		return heatingDistributionById;
	}
}
