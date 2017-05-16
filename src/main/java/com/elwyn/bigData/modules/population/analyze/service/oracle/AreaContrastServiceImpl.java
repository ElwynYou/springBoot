package com.elwyn.bigData.modules.population.analyze.service.oracle;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.core.paging.Page;
import com.rainsoft.modules.population.analyze.dao.oracle.IAreaContrastDao;
import com.rainsoft.modules.population.analyze.service.IAreaContrastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.population.analyze.service.oracle.AreaContrastService
 * @Description
 * @Author Elwyn
 * @Version 2017/4/7
 * @Copyright 上海云辰信息科技有限公司
 **/
@Service
public class AreaContrastServiceImpl implements IAreaContrastService {
	@Autowired
	IAreaContrastDao iAreaContrastDao;
	@Override
	public List<Map<String,Object>> areaContast(String data, Page page) {
		/*Map<String, Object> map = new HashMap<>();*/
		List<Map<String,Object>> data1= (List<Map<String, Object>>) JSONObject.parse(data.replace("&quot;", "\""));
		/*map.put("page", page);
		map.put("data", data1);*/

		List<Map<String, Object>> maps = iAreaContrastDao.areaContrast(data1);

		return maps;
	}
}
