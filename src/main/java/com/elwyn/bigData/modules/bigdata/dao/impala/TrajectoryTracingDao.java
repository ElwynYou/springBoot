package com.elwyn.bigData.modules.bigdata.dao.impala;

import com.rainsoft.core.annotation.ImpalaDao;
import com.rainsoft.modules.bigdata.entity.HScanEndingTraceEntity;

import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.bigdata.dao.impala
 * @Description
 * @Author Elwyn
 * @Version 2016/12/23 16:25
 * @Copyright 上海云辰信息科技有限公司
 */
@ImpalaDao
public interface TrajectoryTracingDao {
    List<HScanEndingTraceEntity> getLocationByMac(Map map);
    List<HScanEndingTraceEntity> getFrequencyCount(Map map);
}
