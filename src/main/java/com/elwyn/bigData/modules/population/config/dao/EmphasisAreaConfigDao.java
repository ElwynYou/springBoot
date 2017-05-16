package com.elwyn.bigData.modules.population.config.dao;

import java.util.List;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.core.dao.IBaseDao;
import com.rainsoft.model.ServiceInfo;
import com.rainsoft.modules.population.config.entity.EmphasisAreaConfig;

/**
 * 重点区域设置DAO接口
 * @author Sugar
 * @version 2017-04-14
 */
@MyBatisDao
public interface EmphasisAreaConfigDao extends IBaseDao<EmphasisAreaConfig> {
	/**
	 * 获取场所
	 * @param info
	 * @return
	 */
	List<ServiceInfo> findServiceInfoList(ServiceInfo info);
}