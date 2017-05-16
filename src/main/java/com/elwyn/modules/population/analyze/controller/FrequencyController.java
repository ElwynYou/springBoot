package com.elwyn.modules.population.analyze.controller;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.modules.population.analyze.entity.Frequency;
import com.rainsoft.modules.population.analyze.service.IFrequencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.population.analyze.controller.FrequencyController
 * @Description 频次分析
 * 
 * @Author Sugar
 * @Version 2017年4月7日 下午3:02:01
 * @Copyright 上海云辰信息科技有限公司
 */
@Controller
@RequestMapping("/population/analyze/frequency")
public class FrequencyController extends BaseController {

	@Autowired
	private IFrequencyService frequencyService;

	@RequestMapping(value = { "", "index" })
	public String index() {
		return "population/analyze/frequency";
	}

	@RequestMapping("analyze")
	@ResponseBody
	public List<Map<String, Object>> analyze(Frequency entity) {
		if ("time".equals(entity.getType())) {
			return frequencyService.timeAnalyze(entity);
		} else {
			return frequencyService.placeAnalyze(entity);
		}
	}
}
