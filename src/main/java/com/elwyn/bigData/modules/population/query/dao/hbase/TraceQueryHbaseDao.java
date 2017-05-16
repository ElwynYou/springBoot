package com.elwyn.bigData.modules.population.query.dao.hbase;

import com.rainsoft.core.annotation.ImpalaDao;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;

import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.populationMovement.dao
 * @Description
 * @Author Elwyn
 * @Version 2017/2/15 17:59
 * @Copyright 上海云辰信息科技有限公司
 */
@ImpalaDao("traceQueryHbaseDao")
public interface TraceQueryHbaseDao {
   List<ScanEndingImproves> getTraceQueryList(Map map);
   List<ScanEndingImproves> getIMSITraceQueryList(Map map);
}
