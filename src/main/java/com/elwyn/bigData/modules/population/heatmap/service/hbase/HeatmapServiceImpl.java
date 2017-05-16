package com.elwyn.bigData.modules.population.heatmap.service.hbase;

import com.rainsoft.core.paging.Page;
import com.rainsoft.modules.population.heatmap.dao.hbase.HeatmapHbaseDao;
import com.rainsoft.modules.population.heatmap.dao.oracle.HeatmapDao;
import com.rainsoft.modules.population.heatmap.entity.HeatMapDetail;
import com.rainsoft.modules.population.heatmap.entity.HeatMapHistory;
import com.rainsoft.modules.population.heatmap.entity.HeatMapSuspicious;
import com.rainsoft.modules.population.heatmap.entity.TheHeat;
import com.rainsoft.modules.population.heatmap.service.IHeatmapService;
import com.rainsoft.modules.population.query.entity.CrowdQuery;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.population.heatmap.service.hbase.HeatmapServiceImpl
 * @Description
 * @Author Sugar
 * @Version 2017年3月29日 下午1:08:35
 * @Copyright 上海云辰信息科技有限公司
 */
@Service("heatmapServiceHbaseImpl")
public class HeatmapServiceImpl implements IHeatmapService {

	@Autowired
	HeatmapHbaseDao heatmapHbaseDao;

	@Autowired
	HeatmapDao heatmapDao;

	/*public List<TheHeat> findHeatmap(String id, String userId, String statTime) {

		//获得hbase数据
		List<TheHeat> heatmap = heatmapHbaseDao.findHeatmap(id, userId, statTime);
		//获得oracles相关设备数据
		List<MachineInfo> machineS = heatmapDao.getMachineInfoByMac(heatmap);
		for (TheHeat tmap : heatmap) {
			for (MachineInfo info : machineS) {
				if (tmap.getSnCode().equals(info.getMachineId())) {
					tmap.setLatitude(info.getLatitude());
					tmap.setLongitude(info.getLongitude());
				}
			}
		}

		return heatmap;

	}*/

	@Override
	public Map<String, Object> initHeatMapDatas(String id) {
		return null;
	}

	@Override
	public Map<String, Object> getHeatMapDatas(String id,String interval) {
		return null;
	}




	@Override
	public List<TheHeat> initHeatMapIndex(String init,String interval) {
		return null;
	}



	@Override
	public List<HeatMapHistory> getHeatMapHistory(HeatMapHistory heatMapHistory) {
		return null;
	}

	@Override
	public Page<HeatMapSuspicious> getHeatMapSuspicious(HeatMapSuspicious heatMapSuspicious, Integer pageNo,
			Integer pageSize) {
		return null;
	}
	@Override
	public int updateSuspicious(HashMap<String, String> requestMap) {
		return 0;
	}

	@Override
	public Page<HeatMapDetail> getHeatMapDetailByDay(HeatMapDetail heatMapDetail, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Page<HeatMapDetail> getHeatMapDetailByMonth(HeatMapDetail heatMapDetail, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<HeatMapDetail> getHeatMapDetailByYear(HeatMapDetail heatMapDetail, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ScanEndingImproves> getHeatMapCrowdQuery(CrowdQuery query, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
