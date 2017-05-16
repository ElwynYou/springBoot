package com.elwyn.modules.baseinfo.service;

import com.rainsoft.core.service.IService;
import com.rainsoft.model.machineInfo.MachineInfo;

import java.util.List;

/**
 * 
 * @Name com.rainsoft.modules.baseinfo.service.IMachineInfoService
 * @Description 设备信息Service
 * 
 * @Author Sugar
 * @Version 2017年4月8日 下午5:16:48
 * @Copyright 上海云辰信息科技有限公司
 */
public interface IMachineInfoService extends IService {
	List<MachineInfo> findList(MachineInfo entity);
}
