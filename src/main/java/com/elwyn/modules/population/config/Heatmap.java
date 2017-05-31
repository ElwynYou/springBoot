package com.elwyn.modules.population.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Name com.elwyn.modules.population.config.Heatmap
 * @Description
 * @Author Elwyn
 * @Version 2017/5/31
 * @Copyright 上海云辰信息科技有限公司
 **/
@RequestMapping("/config")
@Controller
public class Heatmap {
	private Logger logger = LoggerFactory.getLogger(Heatmap.class);

	@RequestMapping("/heatmapList")
	public String heatMapList(){
		return "html/population/config/heatmap";
	}
}
