package com.elwyn.bigData.modules.population.analyze.service;

import com.rainsoft.core.paging.Page;

import java.util.List;
import java.util.Map;

/**
 * @Name IAreaContrastService
 * @Description
 * @Author Elwyn
 * @Version 2017/4/7
 * @Copyright 上海云辰信息科技有限公司
 **/
public interface IAreaContrastService {
	List<Map<String,Object>> areaContast(String data, Page page);
}
