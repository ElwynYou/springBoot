package com.elwyn.bigData.modules.bigdata.dao.oracle;

import java.util.List;
import java.util.Map;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.model.machineInfo.MachineInfo;

/**
 * @Name com.rainsoft.modules.bigdata.dao.oracle
 * @Description
 * @Author Elwyn
 * @Version 2016/12/20 12:44
 * @Copyright 上海云辰信息科技有限公司
 */
@MyBatisDao
public interface GetMachineInfoDao {
    //根据当前用户城区查询ServiceCode
    List<String> getUserServiceCode(List<Long> areaCode);
    //根据当前用户serviceCode查设备
    List<MachineInfo> getMachineInfoByMap(Map<String, Object> map);
    
    String getServiceCodeByName(String serviceName);
    
    List<MachineInfo> getMachineInfoByMachId(List<String> machIdList);
    
    MachineInfo getMachineInfoById(String machineId);
}
