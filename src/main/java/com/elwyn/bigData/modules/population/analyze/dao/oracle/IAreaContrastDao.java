package com.elwyn.bigData.modules.population.analyze.dao.oracle;

import com.rainsoft.core.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.population.analyze.dao.oracle.IAreaContrastDao
 * @Description
 * @Author Elwyn
 * @Version 2017/4/8
 * @Copyright 上海云辰信息科技有限公司
 **/
@MyBatisDao
public interface IAreaContrastDao {
		List<Map<String,Object>> areaContrast(List<Map<String, Object>> map);
}
