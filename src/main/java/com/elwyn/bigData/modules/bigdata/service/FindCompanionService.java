package com.elwyn.bigData.modules.bigdata.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rainsoft.model.AreaStation;
import com.rainsoft.model.User;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.model.datatables.DataTablesResponse;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.bigdata.dao.impala.GetCompanionDao;
import com.rainsoft.modules.bigdata.dao.oracle.GetMachineInfoDao;
import com.rainsoft.modules.bigdata.entity.HClusterResult;
import com.rainsoft.modules.bigdata.entity.HScanCollectionApEntity;
import com.rainsoft.modules.bigdata.entity.HScanEndingTraceEntity;
import com.rainsoft.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Name com.rainsoft.modules.bigdata.service
 * @Description
 * @Author Elwyn
 * @Version 2016/12/20 11:37
 * @Copyright 上海云辰信息科技有限公司
 */
@Service
public class FindCompanionService {

	@Autowired
	private GetMachineInfoDao getMachineInfoDao;

	@Autowired
	private GetCompanionDao companionDao;

	/**
	 * 根据当前用户的areaCode查询场所Servicecode
	 *
	 * @param areaCode
	 * @return
	 */
	public List<String> getUserServiceCode(List<Long> areaCode) {

		return getMachineInfoDao.getUserServiceCode(areaCode);

	}

	/**
	 * 根据serviceCode查询设备信息
	 *
	 * @param
	 * @return
	 */
	public List<MachineInfo> getMachineInfoByServiceCode(Map<String,Object> map) {
		Object region=map.get("region");
		if(region!=null){
			region= Utils.parseArea(String.valueOf(region));
			map.put("region",region);
		}
		return getMachineInfoDao.getMachineInfoByMap(map);
	}

	/**
	 * 暂时没用
	 *
	 * @param serviceCode
	 * @return
	 */
	public List<HScanCollectionApEntity> getScanCollectionAp(List<String> serviceCode) {
		return companionDao.getScanCollectionAp(serviceCode);
	}

	public String getServiceCodeByName(String serviceName) {
		String ServiceCode = getMachineInfoDao.getServiceCodeByName(serviceName);
		if (org.apache.commons.lang.StringUtils.isEmpty(ServiceCode)) {
			return null;
		}
		return ServiceCode;
	}

	/**
	 * impala查找同伴信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataTablesResponse getCompanionInfo(User user,DataTablesRequest dataTablesRequest) {
		dataTablesRequest.setModeClass(HScanEndingTraceEntity.class);
		DataTablesResponse dtres = new DataTablesResponse(dataTablesRequest);
		// 获取用户能查询的serviceCode
		List<String> serviceCode = getServiceCodeByArea(user,getMachineInfoDao);
		String type = (String) dataTablesRequest.getFormData().get("type");// 获取查询类型
		// 根据场所名称查询结果
		if ("1".equals(type)) {
			Map<String, Object> query = Maps.newHashMap();
			List<String> machineNames = new ArrayList<>();
			// 获取场所信息
			List<Map<String, String>> aryPlaceData = (List) dataTablesRequest.getFormData().get("ary_placeData");// 获得传入场所信息\
			String timeInterval = (String) dataTablesRequest.getFormData().get("timeInterval");// 获得时间间隔
			String mac = (String) dataTablesRequest.getFormData().get("mac");// 获得查询mac
			for (Map<String, String> paceInfo : aryPlaceData) {
				machineNames.add(paceInfo.get("equipment"));
			}
			// 根据场所名和serviceCode 得到设备信息
			Map map = new HashMap();
			map.put("serviceCode", serviceCode);
			map.put("machineNames", machineNames);
			List<MachineInfo> machineInfos = getMachineInfoDao.getMachineInfoByMap(map);
			// 合并machineid和开始结束时间作为一组查询条件
			List<EquipmentInfo> equipmentInfoslist = new ArrayList<>();
			for (Map<String, String> paceInfo : aryPlaceData) {
				for (MachineInfo machineInfo : machineInfos) {
					if ((paceInfo.get("equipment")).equals(machineInfo.getMachineName())) {
						EquipmentInfo equipmentInfo = new EquipmentInfo(machineInfo.getMachineId(), paceInfo.get("startTime"), paceInfo.get("endTime"));
						equipmentInfoslist.add(equipmentInfo);
						break;
					}
				}
			}

			query.put("mac", mac);
			query.put("timeInterval", timeInterval);
			query.put("equipmentInfoslist", equipmentInfoslist);
			dataTablesRequest.setFormData(query);

			long count = 0;
			try {
				count = companionDao.getCount(dataTablesRequest);
				if (count <= 0) {
					return dtres;
				}
			} catch (UncategorizedSQLException e) {
				DataTablesResponse dr = new DataTablesResponse();
				dr.setError("请求超时！");
				e.printStackTrace();
				return dr;
			}
			// 查询impala获取最终结果
			List<HScanEndingTraceEntity> companionInfo = companionDao.getCompanionInfo(dataTablesRequest);
			
			if (machineInfos != null) {
				// 合并oracle和impala数据信息
				companionInfo = mergeInfo(machineInfos, companionInfo);
			}
			dtres.setRecordsTotal(count);
			dtres.setRecordsFiltered(count);
			dtres.setData(companionInfo);
			return dtres;
		}
		// 根据画图范围查询
		if ("2".equals(type)) {
			Map<String, Object> query = Maps.newHashMap();
			String timeInterval = (String) dataTablesRequest.getFormData().get("timeInterval");// 获得时间间隔
			String mac = (String) dataTablesRequest.getFormData().get("mac");// 获得查询mac
			Map map = (Map) dataTablesRequest.getFormData().get("mapData");
			// 获得圆形
			List<Map> circleList = (List) map.get("ary_circle_label");

			// 获得方形
			List<Map> squareList = (List) map.get("ary_rectangle_label");
			// 根据圆形方形和serviceCode获取设备信息
			Map<String,Object> quaryMachine = Maps.newHashMap();
			quaryMachine.put("serviceCode", serviceCode);
			quaryMachine.put("circleList", circleList);
			quaryMachine.put("squareList", squareList);
			List<MachineInfo> machineInfos = getMachineInfoDao.getMachineInfoByMap(quaryMachine);
			// 拿到设备id作为条件
			Set<String> equipmentIds = new HashSet<>();
			for (MachineInfo machineInfo : machineInfos) {
				equipmentIds.add(machineInfo.getMachineId());
			}
			query.put("mac", mac);
			query.put("timeInterval", timeInterval);
			query.put("equipmentIds", equipmentIds);
			if (equipmentIds.isEmpty()){
				return dtres;
			}
			dataTablesRequest.setFormData(query);

			long count = companionDao.getCount(dataTablesRequest);
			if (count <= 0) {
				return dtres;
			}
			List<HScanEndingTraceEntity> list = companionDao.getCompanionInfo(dataTablesRequest);
			list = mergeInfo(machineInfos, list);
			dtres.setRecordsTotal(count);
			dtres.setData(list);
			dtres.setRecordsFiltered(count);
			return dtres;
		}

		// 获取impala查询结果取出设备Id去oracle数据库查询设备信息
		long count = 0;
		try {
			count = companionDao.getHClusterResultCount(dataTablesRequest);
			if (count <= 0) {
				return dtres;
			}
		} catch (UncategorizedSQLException e) {
			DataTablesResponse dr = new DataTablesResponse();
			dr.setError("请求超时！");
			e.printStackTrace();
			return dr;
		}
		List<HClusterResult> data = null;
		try {
			data = companionDao.getHClusterResultInfo(dataTablesRequest);
		} catch (UncategorizedSQLException e) {
			DataTablesResponse dr = new DataTablesResponse();
			dr.setError("请求超时！");
			e.printStackTrace();
			return dr;
		}
		dtres.setRecordsTotal(count);
		dtres.setRecordsFiltered(count);
		List<String> listMId = Lists.newArrayList();
		for (HClusterResult hcr : data) {
			listMId.add(hcr.getMachineId());
		}

		List<MachineInfo> machineInfoList = getMachineInfoDao.getMachineInfoByMachId(listMId);
		for (HClusterResult hcr : data) {
			for (MachineInfo m : machineInfoList) {
				if (hcr.getMachineId().equals(m.getMachineId())) {
					hcr.setLatitude(m.getLatitude());
					hcr.setLongitude(m.getLongitude());
					hcr.setMachineAddress(m.getServiceAddress());
					hcr.setMachineName(m.getMachineName());
					break;
				}
			}
		}
		//去除经纬度为空的数据
		Iterator<HClusterResult> iterator = data.iterator();
		while (iterator.hasNext()){
			HClusterResult hClusterResult=iterator.next();
			if (hClusterResult.getLatitude()==null || "".equals(hClusterResult.getLatitude())
					||hClusterResult.getLongitude()==null || "".equals(hClusterResult.getLongitude())){
				iterator.remove();
			}
		}
		dtres.setData(data);
		return dtres;
	}

	// 获取当前用户和城区
	private static List<String> getServiceCodeByArea(User user,GetMachineInfoDao getMachineInfoDao) {

		Iterator<AreaStation> it = user.getAreas().iterator();
		List<Long> areas = new ArrayList<>();
		while (it.hasNext()) {
			AreaStation area = it.next();
			areas.add(area.getCode());
		}
		return getMachineInfoDao.getUserServiceCode(areas);
	}

	/**
	 * 页面初始化和移动时调用查询当前范围的设备
	 *
	 * @param data
	 * @return
	 */
	public List<HScanEndingTraceEntity> getInitEq(List<HScanEndingTraceEntity> data) {
		if (data != null) {
			List<String> machineId = new ArrayList<>();
			List<MachineInfo> machineInfo = new ArrayList<>();
			// 拿到impala查询结果根据equipmentId查询设备信息
			for (HScanEndingTraceEntity hScanEndingTraceEntity : data) {
				machineId.add(hScanEndingTraceEntity.getEquipmentId());
			}
			if (!machineId.isEmpty()) {
				Map<String,Object> map = Maps.newHashMap();
				map.put("equipmentIds", machineId);
				machineInfo = getMachineInfoDao.getMachineInfoByMap(map);
			}

			if (machineInfo != null) {
				data = mergeInfo(machineInfo, data);
			}

		}
		return data;
	}

	/**
	 * 合并设备地址到impala数据中
	 *
	 * @param machineInfo
	 * @param traceEntityList
	 * @return
	 */
	private List<HScanEndingTraceEntity> mergeInfo(List<MachineInfo> machineInfo, List<HScanEndingTraceEntity> traceEntityList) {
		for (HScanEndingTraceEntity hScanEndingTraceEntity : traceEntityList) {
			for (MachineInfo info : machineInfo) {
				if (hScanEndingTraceEntity.getEquipmentId().equals(info.getMachineId())) {
					hScanEndingTraceEntity.setMachineAddress(info.getServiceAddress());
					hScanEndingTraceEntity.setLongitude(info.getLongitude());
					hScanEndingTraceEntity.setLatitude(info.getLatitude());
					hScanEndingTraceEntity.setMachineName(info.getMachineName());
					break;
				}
			}
		}
		return traceEntityList;
	}

	private class EquipmentInfo {
		@SuppressWarnings("unused")
		private String equipmentId;
		@SuppressWarnings("unused")
		private String startTime;
		@SuppressWarnings("unused")
		private String endTime;

		public EquipmentInfo(String equipmentId, String startTime, String endTime) {
			this.equipmentId = equipmentId;

			this.startTime = startTime;
			this.endTime = endTime;
		}
	}

	/**
	 * 同伴归类
	 * 
	 * @param dataTablesRequest
	 * @return
	 */
	public DataTablesResponse getCompanionGroup(DataTablesRequest<HScanEndingTraceEntity> dataTablesRequest) {
		dataTablesRequest.setModeClass(HScanEndingTraceEntity.class);
		DataTablesResponse dtres = new DataTablesResponse(dataTablesRequest);
		long count = 0;
		List<HClusterResult> data;
		try {
			count = companionDao.getGroupResultInfoCount(dataTablesRequest);
			if (count <= 0) {
				return dtres;
			}
			data = companionDao.getGroupResultInfo(dataTablesRequest);
			dtres.setRecordsTotal(count);
			dtres.setRecordsFiltered(count);
			dtres.setData(data);
		} catch (UncategorizedSQLException e) {
			DataTablesResponse dr = new DataTablesResponse();
			dr.setError("请求超时！");
			return dr;
		}
		return dtres;
	}

}
