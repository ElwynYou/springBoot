package com.elwyn.bigData.modules.bigdata.dao.oracle;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.modules.bigdata.entity.HScanEndingTraceEntity;

import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.bigdata.dao.oracle
 * @Description
 * @Author Elwyn
 * @Version 2016/12/28 10:30
 * @Copyright 上海云辰信息科技有限公司
 */
@MyBatisDao
public interface TrajectoryTracingOracleDao {
    List<HScanEndingTraceEntity> getTrajectoryTraceList(Map map);
    List<HScanEndingTraceEntity> getFrequencyCount(Map map);
    List<String> getMacByCerCode(Map map);
}
