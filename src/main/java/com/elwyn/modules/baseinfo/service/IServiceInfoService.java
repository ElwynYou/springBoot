package com.elwyn.modules.baseinfo.service;

import com.rainsoft.core.service.IService;
import com.rainsoft.model.ServiceInfo;

import java.util.List;

/**
 * @Name com.rainsoft.modules.baseinfo.IServiceInfoService
 * @Description 
 * 
 * @Author Sugar
 * @Version 2017年4月5日 下午7:45:21
 * @Copyright 上海云辰信息科技有限公司
 */
public interface IServiceInfoService extends IService {
	List<ServiceInfo> findList(ServiceInfo info);
}
