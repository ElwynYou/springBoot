package com.elwyn.bigData.modules.population.crowd.dao;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.modules.population.crowd.entity.TotalCount;

import java.util.List;
import java.util.Map;
/**
 * 
 * @Name com.rainsoft.modules.population.crowd.service.RpcCrowdManageService
 * @Description 人群管理
 * @Author xa
 * @Version 2017年3月29日 下午4:28:23
 * @Copyright 上海云辰信息科技有限公司
 */
@MyBatisDao
public interface BureauIndexDao {

	Map<String, Object> getTotalPeopleCount(String areaCode);
	List<TotalCount>  getDetailCount(String areaCode);

}
