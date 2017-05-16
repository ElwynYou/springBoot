package com.elwyn.bigData.modules.population.query.service.hbase;

import com.rainsoft.model.User;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.bigdata.dao.oracle.GetMachineInfoDao;
import com.rainsoft.modules.bigdata.util.GetServiceCodesByUser;
import com.rainsoft.modules.bigdata.util.StringUtils;
import com.rainsoft.modules.population.query.dao.hbase.TraceQueryHbaseDao;
import com.rainsoft.modules.population.query.dao.oracle.TraceQueryDao;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;
import com.rainsoft.modules.population.query.service.ITraceQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Name 
 *       TraceQueryServiceImpl
 * @Description
 * @Author Elwyn
 * @Version 2017/2/15 18:14
 * @Copyright 上海云辰信息科技有限公司
 */
@Service("traceQueryServiceHbaseImpl")
public class TraceQueryServiceImpl implements ITraceQueryService {
	@Autowired
	TraceQueryHbaseDao traceQueryHbaseDao;

	@Autowired
	TraceQueryDao traceQueryDao;

	@Autowired
	GetMachineInfoDao getMachineInfoDao;

	public List<ScanEndingImproves> getTraceQuery(@SuppressWarnings("rawtypes") Map map, User user) {
		List<String> serviceCodeByArea = GetServiceCodesByUser.getServiceCodeByArea(user, getMachineInfoDao);
		if (serviceCodeByArea != null && !serviceCodeByArea.isEmpty()) {
			map.put("serviceCodes", serviceCodeByArea);
		}
		List<ScanEndingImproves> scanEndingImproves = new ArrayList<>();

		String protocolType = String.valueOf(map.get("protocolType"));
		if ("imsi".equals(protocolType)) {
			scanEndingImproves = traceQueryHbaseDao.getIMSITraceQueryList(map);
		} else {
			// 根据虚拟身份查询相关Mac
//			List<String> macs = new ArrayList<>();
//			if (!"Mac".equals(map.get("protocolType"))) {
//				macs = traceQueryDao.getMacByCerCode(map);// 传入的不是mac
//				List<String> newMacs = new ArrayList<>();
//				for (String mac : macs) {
//					newMacs.add(StringUtils.getMacAdr(mac));
//				}
//				macs = newMacs;
//			} else {
//				macs.add((String) map.get("certificateCode"));// 直接传入mac
//			}
//
//			if (macs != null && !macs.isEmpty()) {
//				// 根据Mac查询到相关轨迹信息
//				map.put("macs", macs);
//				scanEndingImproves = traceQueryHbaseDao.getTraceQueryList(map);
//			}
		}

		// 根据impala查询到的设备id去查oracle中设备信息
		List<MachineInfo> machineInfoList = null;
		if (!scanEndingImproves.isEmpty()) {
			Set<String> equipmentMacs = new HashSet<>();
			if ("imsi".equals(protocolType)) {
				for (ScanEndingImproves scanEndingImprove : scanEndingImproves) {
					equipmentMacs.add(StringUtils.getMacAdr(scanEndingImprove.getEquipmentMac()));
				}
				map.put("equipmentMacs", equipmentMacs);
				// 根据impala查到的设备mac在oracle中查询设备信息
				machineInfoList = getMachineInfoDao.getMachineInfoByMap(map);
			} else {
				for (ScanEndingImproves scanEndingImprove : scanEndingImproves) {
					equipmentMacs.add(scanEndingImprove.getEquipmentId());
				}
				map.put("equipmentIds", equipmentMacs);
				// 根据impala查到的设备Id在oracle中查询设备信息
				machineInfoList = getMachineInfoDao.getMachineInfoByMap(map);
			}

		}
		// 合并设备信息和终端信息
		if (machineInfoList != null && !machineInfoList.isEmpty() && !scanEndingImproves.isEmpty()) {
			if ("imsi".equals(protocolType)) {
				for (ScanEndingImproves scanEndingImprove : scanEndingImproves) {
					for (MachineInfo machineInfo : machineInfoList) {
						if (StringUtils.getMacAdr(scanEndingImprove.getEquipmentMac()).equals(machineInfo.getMachineMac())) {
							scanEndingImprove.setAddress(machineInfo.getServiceAddress());
							break;
						}
					}
				}
			} else {
				for (ScanEndingImproves scanEndingImprove : scanEndingImproves) {
					for (MachineInfo machineInfo : machineInfoList) {
						if (scanEndingImprove.getEquipmentId().equals(machineInfo.getMachineId())) {
							scanEndingImprove.setLatitude(machineInfo.getLatitude());
							scanEndingImprove.setLongitude(machineInfo.getLongitude());
							scanEndingImprove.setAddress(machineInfo.getServiceAddress());
							break;
						}
					}
				}
			}

			// 去除经纬度为空的数据
			Iterator<ScanEndingImproves> iterator = scanEndingImproves.iterator();
			while (iterator.hasNext()) {
				ScanEndingImproves scanEndingImprove = iterator.next();
				if (scanEndingImprove.getLatitude() == null || "".equals(scanEndingImprove.getLatitude()) || scanEndingImprove.getLongitude() == null
						|| "".equals(scanEndingImprove.getLongitude())) {
					iterator.remove();
				}
			}
		} else {
			scanEndingImproves.clear();
		}
		return scanEndingImproves;
	}
}
