package com.elwyn.bigData.modules.population.query.service;

import com.rainsoft.model.User;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;

import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.remoting.populationMovement
 * @Description
 * @Author Elwyn
 * @Version 2017/2/15 17:13
 * @Copyright 上海云辰信息科技有限公司
 */
public interface ITraceQueryService {
   List<ScanEndingImproves> getTraceQuery(@SuppressWarnings("rawtypes") Map map, User user);
}
