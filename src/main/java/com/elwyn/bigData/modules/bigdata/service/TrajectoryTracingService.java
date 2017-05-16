package com.elwyn.bigData.modules.bigdata.service;

import com.rainsoft.model.AreaStation;
import com.rainsoft.model.User;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.bigdata.dao.impala.TrajectoryTracingDao;
import com.rainsoft.modules.bigdata.dao.oracle.GetMachineInfoDao;
import com.rainsoft.modules.bigdata.dao.oracle.TrajectoryTracingOracleDao;
import com.rainsoft.modules.bigdata.entity.HScanEndingTraceEntity;
import com.rainsoft.modules.bigdata.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Name com.rainsoft.modules.bigdata.service
 * @Description
 * @Author Elwyn
 * @Version 2016/12/23 16:36
 * @Copyright 上海云辰信息科技有限公司
 */
@Service
public class TrajectoryTracingService {
    @Autowired
    private TrajectoryTracingDao trajectoryTracingDao;

    @Autowired
    private GetMachineInfoDao getMachineInfoDao;

    @Autowired
    TrajectoryTracingOracleDao trajectoryTracingOracleDao;


    public List<HScanEndingTraceEntity> getLocationByMap(User user, Map map) {
        //拿到用户城区相关的serviceCode
        List<String> serviceCodes = getServiceCodeByArea(user,getMachineInfoDao);
        map.put("serviceCode", serviceCodes);
        //根据虚拟身份查询相关Mac

        List<String> macs = new ArrayList<>();
        if (!"Mac".equals(map.get("protocolType"))) {
            macs = trajectoryTracingOracleDao.getMacByCerCode(map);//传入的不是mac
            List<String> newMacs = new ArrayList<>();
            for (String mac : macs) {
                newMacs.add(StringUtils.getMacAdr(mac));
            }
            macs = newMacs;
        } else {
            macs.add((String) map.get("certificateCode"));//直接传入mac
        }


        List<HScanEndingTraceEntity> hScanEndingTraceList = new ArrayList<>();

        if (macs != null && !macs.isEmpty()) {

            //根据Mac查询到相关轨迹信息
            map.put("macs", macs);
            hScanEndingTraceList = trajectoryTracingDao.getLocationByMac(map);
            int count = 0;
            for (int i = 0; i < hScanEndingTraceList.size(); i++) {
                for (int j = 0; j < hScanEndingTraceList.size(); j++) {
                    if (hScanEndingTraceList.get(i).equals(hScanEndingTraceList.get(j))) {
                        count++;
                    }
                }
                hScanEndingTraceList.get(i).setCountTimes(Integer.toString(count));
                count = 0;
            }
            //根据impala查询到的设备id去查oracle中设备信息
            List<MachineInfo> machineInfoList = null;
            if (!hScanEndingTraceList.isEmpty()) {
                Set<String> equipmentIds = new HashSet<>();
                for (HScanEndingTraceEntity hScanEndingTraceEntity : hScanEndingTraceList) {
                    equipmentIds.add(hScanEndingTraceEntity.getEquipmentId());
                }
                map.put("equipmentIds", equipmentIds);
                //根据impala查到的设备id在oracle中查询设备信息
                machineInfoList = getMachineInfoDao.getMachineInfoByMap(map);
            }
            //合并设备信息和终端信息
            if (machineInfoList != null && !machineInfoList.isEmpty() && !hScanEndingTraceList.isEmpty()) {
                for (HScanEndingTraceEntity hScanEndingTraceEntity : hScanEndingTraceList) {
                    for (MachineInfo machineInfo : machineInfoList) {
                        if (hScanEndingTraceEntity.getEquipmentId().equals(machineInfo.getMachineId())) {
                            hScanEndingTraceEntity.setMachineAddress(machineInfo.getServiceAddress());
                            hScanEndingTraceEntity.setMachineName(machineInfo.getMachineName());
                            hScanEndingTraceEntity.setLatitude(machineInfo.getLatitude());
                            hScanEndingTraceEntity.setLongitude(machineInfo.getLongitude());
                            break;
                        }
                    }
                }
                //去除经纬度为空的数据
                Iterator<HScanEndingTraceEntity> iterator = hScanEndingTraceList.iterator();
                while (iterator.hasNext()){
                    HScanEndingTraceEntity hScanEndingTraceEntity=iterator.next();
                    if (hScanEndingTraceEntity.getLatitude()==null || "".equals(hScanEndingTraceEntity.getLatitude())
                            ||hScanEndingTraceEntity.getLongitude()==null || "".equals(hScanEndingTraceEntity.getLongitude())){
                        iterator.remove();
                    }
                }

            } else {
                hScanEndingTraceList.clear();
            }
        }


        return hScanEndingTraceList;
    }

    /**
     * 获取时段频次
     *
     * @param map
     * @return
     */
    public int[] getFrequencyCount(User user,Map map) {
        //拿到用户城区相关的serviceCode
        List<String> serviceCodes = getServiceCodeByArea(user,getMachineInfoDao);
        map.put("serviceCode", serviceCodes);
        //根据虚拟身份查询相关Mac

        List<String> macs = new ArrayList<>();
        if (!"Mac".equals(map.get("protocolType"))) {
            macs = trajectoryTracingOracleDao.getMacByCerCode(map);//传入的不是mac
            List<String> newMacs = new ArrayList<>();
            for (String mac : macs) {
                newMacs.add(StringUtils.getMacAdr(mac));
            }
            macs = newMacs;
        } else {
            macs.add((String) map.get("certificateCode"));//直接传入mac
        }
        int[] frequence = new int[12];
        List<HScanEndingTraceEntity> hScanEndingTraceList = new ArrayList<>();
        if (macs != null && !macs.isEmpty()) {
            //根据Mac查询到相关轨迹信息
            map.put("macs", macs);
            hScanEndingTraceList = trajectoryTracingDao.getLocationByMac(map);
            //根据impala查询到的设备id去查oracle中设备信息
            List<MachineInfo> machineInfoList = null;
            if (!hScanEndingTraceList.isEmpty()) {
                Set<String> equipmentIds = new HashSet<>();
                for (HScanEndingTraceEntity hScanEndingTraceEntity : hScanEndingTraceList) {
                    equipmentIds.add(hScanEndingTraceEntity.getEquipmentId());
                }
                map.put("equipmentIds", equipmentIds);
                //根据impala查到的设备id在oracle中查询设备信息
                machineInfoList = getMachineInfoDao.getMachineInfoByMap(map);
                //重新拿到有效的eqid
                equipmentIds.clear();
                for (MachineInfo machineInfo : machineInfoList) {
                    equipmentIds.add(machineInfo.getMachineId());
                }
                map.put("equipmentIds", equipmentIds);

            }

            //合并设备信息和终端信息
            if (machineInfoList != null && !machineInfoList.isEmpty() && !hScanEndingTraceList.isEmpty()) {
                hScanEndingTraceList = trajectoryTracingDao.getFrequencyCount(map);
                int k = 0;
                for (int i = 0; i < 12; i++) {
                    k = 2 * i;
                    for (HScanEndingTraceEntity hScanEndingTraceEntity : hScanEndingTraceList) {
                        if (Integer.valueOf(hScanEndingTraceEntity.getHours()) == k) {
                            frequence[i] = Integer.valueOf(hScanEndingTraceEntity.getFrequency());
                            break;
                        }
                    }
                }
                return frequence;
            } else {
                return frequence;
            }
        }
        return frequence;


    }

    //获取当前用户和城区
    private static List<String> getServiceCodeByArea(User user,GetMachineInfoDao getMachineInfoDao) {

        
        Iterator<AreaStation> it = user.getAreas().iterator();
        List<Long> areas = new ArrayList<>();
        while (it.hasNext()) {
            AreaStation area = it.next();
            areas.add(area.getCode());
        }
        return getMachineInfoDao.getUserServiceCode(areas);
    }


}
