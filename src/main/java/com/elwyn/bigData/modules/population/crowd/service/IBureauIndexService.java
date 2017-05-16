package com.elwyn.bigData.modules.population.crowd.service;


import com.rainsoft.core.service.IService;
import com.rainsoft.modules.population.crowd.entity.TotalCount;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Name com.rainsoft.modules.population.crowd.service.RpcCrowdManageService
 * @Description rpc人群管理服务
 * @Author xa
 * @Version 2017年3月29日 下午4:28:23
 * @Copyright 上海云辰信息科技有限公司
 */
public interface IBureauIndexService extends IService{
	/**
	 * 获取首页统计
	 * @return
	 */
	Map<String, Object> getTotalPeopleCount(String areaCode);

	List<TotalCount> getDetailCount(String areaCode);
	
}
