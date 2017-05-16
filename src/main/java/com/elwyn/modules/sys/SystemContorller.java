package com.elwyn.modules.sys;

import com.rainsoft.cache.DictionaryCache;
import com.rainsoft.core.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.Map.Entry;

/**
 * @Name com.rainsoft.modules.sys.SystemContorller
 * @Description
 * 
 * @Author Sugar
 * @Version 2017年3月30日 下午3:05:35
 * @Copyright 上海云辰信息科技有限公司
 */
@Controller
@RequestMapping("/sys")
public class SystemContorller extends BaseController {
	/**
	 * 获取区域
	 * @param parent
	 * @return [key-value]
	 */
	@RequestMapping("/area")
	@ResponseBody
	public List<Map<String,String>> getArea(String parent) {
		Map<String, String> map = null;
		if (StringUtils.isEmpty(parent)) {
			map = DictionaryCache.getProvinceMap();
		} else if (parent.endsWith("0000")) {
			map = DictionaryCache.getCityMap(parent);
		} else if (parent.endsWith("00")) {
			map = DictionaryCache.getAreaMap(parent);
		}
		if (map != null) {
			List<Map<String,String>> list = new ArrayList<>();
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while(iterator.hasNext()) {
				Map<String, String> m = new HashMap<>();
				Entry<String, String> en = iterator.next();
				m.put("key", en.getKey());
				m.put("value", en.getValue());
				list.add(m);
			}
			return list;
		}
		return null;
	}
	
	/**
	 * 根据
	 * @param parent
	 * @return [key-value]
	 */
	@RequestMapping("/dic")
	@ResponseBody
	public Map<String,String> getDic(String parentId) {
		Map<String, String> map = null;
		if (StringUtils.isNotEmpty(parentId)) {
			map = DictionaryCache.getDicByParentCode(parentId);
		} 
		
		return map;
	}
}
