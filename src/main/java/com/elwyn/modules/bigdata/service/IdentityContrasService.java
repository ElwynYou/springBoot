package com.elwyn.modules.bigdata.service;

import com.rainsoft.model.AreaStation;
import com.rainsoft.model.User;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.model.datatables.DataTablesResponse;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.bigdata.dao.impala.IIdentityContrastDao;
import com.rainsoft.modules.bigdata.dao.oracle.GetMachineInfoDao;
import com.rainsoft.modules.bigdata.entity.IdentityContrastEntity;
import com.rainsoft.spring.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.bigdata.service.impl
 * @Description 身份比对
 * @Author lisicai
 * @Version 2016/12/21 11:30
 * @Copyright 上海云辰信息科技有限公司
 * 
 * 
 * @deprecated 该类已迁移至iSecBigdata工程中，请使用RpcIdentityContrasService远程接口替换 张云鹏 2017-01-19
 */
@Service
public class IdentityContrasService {

	@Autowired
	private IIdentityContrastDao identityContrastDao;

	@Autowired
	private GetMachineInfoDao getMachineInfoDao;

	public DataTablesResponse findListTraceAp(DataTablesRequest<?> dataTablesRequest) {
		DataTablesResponse dtres = new DataTablesResponse(dataTablesRequest);

		Map<String, Object> formData = dataTablesRequest.getFormData();
		String maclist = (String) formData.get("maclist");
		if (maclist == null) {
			return dtres;
		}
		StringBuilder joinTable = new StringBuilder();
		String[] mac = maclist.split(",");
		String start = (String) formData.get("start");
		String end = (String) formData.get("end");
		for (int i = 1; i < mac.length; i++) {
			joinTable.append(" inner join (select equipment_id, CAPTURE_TIME from H_SCAN_ENDING_TRACE where ENDING_MAC='" + mac[i] + "'");
			if (start != null && !start.isEmpty()) {
				joinTable.append(" and substr(capture_time,1,19) >= '" + start + "'");
			}
			if (end != null && !end.isEmpty()) {
				joinTable.append(" and substr(capture_time,1,19) <= '" + end + "'");
			}
			joinTable.append(") t" + i);
			joinTable.append(" on (a.equipment_id=t" + i + ".equipment_id and abs(unix_timestamp(a.CAPTURE_TIME)-unix_timestamp(t" + i + ".CAPTURE_TIME))<=900)");
		}
		formData.put("joinTable", joinTable.toString());
		formData.put("mac", mac[0]);
		List<IdentityContrastEntity> identityContrastList = null;
	try{
		long count = identityContrastDao.countFindListTraceAp(dataTablesRequest);
		if (count <= 0) {
			return dtres;
		}
		
		identityContrastList = identityContrastDao.findListTraceAp(dataTablesRequest);
		int identityContrastListLen = identityContrastList.size();
		List<String> machIdList = new ArrayList<>();
		for (int i = 0; i < identityContrastListLen; i++) {
			machIdList.add(identityContrastList.get(i).getEquipmentId());
		}
		List<MachineInfo> machineInfoList = getMachineInfoDao.getMachineInfoByMachId(machIdList);
		int machineInfoLen = machineInfoList.size();
		if(!machineInfoList.isEmpty()){
		IdentityContrastEntity identityContrast = null;// 身份比对
		MachineInfo machineInfo = null;// 设备信息
		String machineIdOr = null;
		String machineIdIm = null;
		for (int i = 0; i < machineInfoLen; i++) {
			machineInfo = machineInfoList.get(i);
			machineIdOr = machineInfo.getMachineId();
			for (int j = 0; j < identityContrastListLen; j++) {
				identityContrast = identityContrastList.get(j);
				machineIdIm = identityContrast.getEquipmentId();
				if (machineIdOr.equals(machineIdIm)) {
					identityContrast.setDeviceAddress(machineInfo.getServiceAddress());
					identityContrast.setCollectingDevice(machineInfo.getMachineName());
					identityContrast.setLatitude(machineInfo.getLatitude());
					identityContrast.setLongitudes(machineInfo.getLongitude());
				}
			}
		}
		}
		
		dtres.setData(identityContrastList);
		dtres.setRecordsTotal(count);
		dtres.setRecordsFiltered(count);
		}catch(org.springframework.jdbc.UncategorizedSQLException e){
			e.printStackTrace();
			dtres.setData(identityContrastList);
			dtres.setRecordsTotal(0);
			dtres.setRecordsFiltered(0);
			dtres.setError("查询超时");
			
		}
		return dtres;
	}

	/*
	 * public List<IdentityContrastEntity> findListTraceAp(DataTablesRequest<?>
	 * dataTablesRequest) {
	 * 
	 * 
	 * List<IdentityContrastEntity> identityContrastList =
	 * identityContrastDao.findListTraceAp(dataTablesRequest); int
	 * identityContrastListLen = identityContrastList.size(); List<String>
	 * machIdList = new ArrayList<>(); for(int i = 0; i <
	 * identityContrastListLen;i++){
	 * machIdList.add(identityContrastList.get(i).getEquipmentId()); }
	 * List<MachineInfo> machineInfoList =
	 * getMachineInfoDao.getMachineInfoByMachId(machIdList); int machineInfoLen
	 * = machineInfoList.size(); IdentityContrastEntity identityContrast =
	 * null;//身份比对 MachineInfo machineInfo = null;//设备信息 String machineIdOr =
	 * null; String machineIdIm = null; for(int i = 0;i <= machineInfoLen; i++){
	 * machineInfo = machineInfoList.get(i); machineIdOr =
	 * machineInfo.getMachineId(); for(int j = 0; j < identityContrastListLen;
	 * j++){ identityContrast = identityContrastList.get(j); machineIdIm =
	 * identityContrast.getEquipmentId(); if(machineIdOr.equals(machineIdIm)){
	 * identityContrast.setDeviceAddress(machineInfo.getServiceAddress());
	 * identityContrast.setCollectingDevice(machineInfo.getMachineName());
	 * identityContrast.setLatitude(machineInfo.getLatitude());
	 * identityContrast.setLongitudes(machineInfo.getLongitude()); } } }
	 * 
	 * 
	 * 
	 * return identityContrastList; }
	 * 
	 * public long countFindListTraceAp(DataTablesRequest<?> dataTablesRequest)
	 * { addServiceCodeTodataTablesRequest(dataTablesRequest); return
	 * identityContrastDao.countFindListTraceAp(dataTablesRequest); }
	 */
	public List<String> getServiceCode() {
		// 获取当前用户和城区
		User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		Iterator<AreaStation> it = user.getAreas().iterator();
		List<Long> areas = new ArrayList<>();
		while (it.hasNext()) {
			AreaStation area = it.next();
			areas.add(area.getCode());
		}
		return getMachineInfoDao.getUserServiceCode(areas);

	}

	public void addServiceCodeTodataTablesRequest(DataTablesRequest<?> dataTablesRequest) {
		Map<String, Object> map = dataTablesRequest.getFormData();
		map.put("serviceCode", this.getServiceCode());
		dataTablesRequest.setFormData(map);
	}

	public List<MachineInfo> getMachineInfoList(List<String> machIdList) {

		return getMachineInfoDao.getMachineInfoByMachId(machIdList);

	}
}