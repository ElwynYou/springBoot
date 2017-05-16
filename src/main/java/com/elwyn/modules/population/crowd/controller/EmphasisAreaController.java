package com.elwyn.modules.population.crowd.controller;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.modules.population.crowd.entity.EmphasisAreaDetail;
import com.rainsoft.modules.population.crowd.entity.EmphasisAreaHistory;
import com.rainsoft.modules.population.crowd.entity.EmphasisAreaSuspicious;
import com.rainsoft.modules.population.crowd.service.IEmphasisAreaService;
import com.rainsoft.modules.population.heatmap.entity.HeatMapHistory;
import com.rainsoft.modules.population.query.entity.CrowdQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @Name com.rainsoft.modules.population.controller.CrowdManageController
 * @Description 重点区域控制器
 * @Author xa
 * @Version 2017年3月29日 下午2:23:01
 * @Copyright 上海云辰信息科技有限公司
 */
 
@Controller
@RequestMapping("/population/crowd")
public class EmphasisAreaController extends BaseController {

	@Autowired
	IEmphasisAreaService emphasisAreaService;
	
	@RequestMapping(value="emphasisAreaHistory")
	public String emphasisAreaHistory(EmphasisAreaHistory emphasisAreaHistory,Model model){
		if(StringUtils.isEmpty(emphasisAreaHistory.getQueryTime())){
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			emphasisAreaHistory.setQueryTime(sdf.format(new Date()));
			emphasisAreaHistory.setQueryTimeType(HeatMapHistory.QUERY_TIME_TYPE_DAY);
		}
		model.addAttribute("emphasisAreaHistory", emphasisAreaHistory);
		model.addAttribute("emphasisAreaHistoryList", emphasisAreaService.getEmphasisAreaHistory(emphasisAreaHistory));
		return "population/crowd/emphasisAreaHistory";
	}
	
	@RequestMapping(value="emphasisAreaSuspicious")
	public String emphasisAreaSuspicious(EmphasisAreaSuspicious emphasisAreaSuspicious, Model model, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize){
		model.addAttribute("emphasisAreaSuspicious", emphasisAreaSuspicious);
		model.addAttribute("page", emphasisAreaService.getEmphasisAreaSuspicious(emphasisAreaSuspicious, pageNo, pageSize));
		return "population/crowd/emphasisAreaSuspicious";
	}
	
	@RequestMapping(value = "updateSuspicious")
	@ResponseBody
	public String updateSuspicious(String imsiCode,String status,String configId) {
		try {
			HashMap<String, String> requestMap= new HashMap<String, String>();
			requestMap.put("imsiCode", imsiCode);
			requestMap.put("status", status);
			requestMap.put("configId", configId);
			if (emphasisAreaService.updateSuspicious(requestMap) > 0) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "操作失败";
	}

	@RequestMapping(value = "emphasisAreaDetailByDay")
	public String emphasisAreaDetailByDay(EmphasisAreaDetail emphasisAreaDetail, Model model,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		model.addAttribute("emphasisAreaDetail", emphasisAreaDetail);
		model.addAttribute("page", emphasisAreaService.getEmphasisAreaDetailByDay(emphasisAreaDetail, pageNo, pageSize));
		return "population/crowd/emphasisAreaDetailByDay";
	}

	@RequestMapping(value = "emphasisAreaDetailByMonth")
	public String emphasisAreaDetailByMonth(EmphasisAreaDetail emphasisAreaDetail, Model model,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		model.addAttribute("emphasisAreaDetail", emphasisAreaDetail);
		model.addAttribute("page", emphasisAreaService.getEmphasisAreaDetailByMonth(emphasisAreaDetail, pageNo, pageSize));
		return "population/crowd/emphasisAreaDetailByMonth";
	}

	@RequestMapping(value = "emphasisAreaDetailByYear")
	public String emphasisAreaDetailByYear(EmphasisAreaDetail emphasisAreaDetail, Model model,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		model.addAttribute("emphasisAreaDetail", emphasisAreaDetail);
		model.addAttribute("page", emphasisAreaService.getEmphasisAreaDetailByYear(emphasisAreaDetail, pageNo, pageSize));
		return "population/crowd/emphasisAreaDetailByYear";

	}
	
	@RequestMapping("emphasisAreaCrowdQuery")
	public String emphasisAreaCrowdQuery(CrowdQuery query, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize, Model model) {
		model.addAttribute("query", query);
		model.addAttribute("page", emphasisAreaService.getEmphasisAreaCrowdQuery(query, pageNo, pageSize));
		return "population/crowd/emphasisAreaCrowdQuery";
	}

	@RequestMapping("emphasisAreaIndex")
	public String emphasisAreaIndex(){
		return "population/crowd/emphasisAreaIndex";
	}

	@RequestMapping("emphasisAreaDetail")
	public String emphasisAreaDetail(String serviceCode,Model model){
		model.addAttribute("serviceCode",serviceCode);
		return "population/crowd/emphasisAreaDetail";
	}
}
