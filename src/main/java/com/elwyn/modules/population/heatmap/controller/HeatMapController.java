package com.elwyn.modules.population.heatmap.controller;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.modules.population.heatmap.entity.HeatMapDetail;
import com.rainsoft.modules.population.heatmap.entity.HeatMapHistory;
import com.rainsoft.modules.population.heatmap.entity.HeatMapSuspicious;
import com.rainsoft.modules.population.heatmap.service.IHeatMapService;
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
 * @Name com.rainsoft.modules.population.heatmap.controller.HeatMapController
 * @Description
 * @Author Elwyn
 * @Version 2017/4/5
 * @Copyright 上海云辰信息科技有限公司
 **/
@Controller("热力图")
@RequestMapping("population/heatmap")
public class HeatMapController extends BaseController{
	@Autowired
	private IHeatMapService heatMapService;

	@RequestMapping(value="map")
	public String getAerogram(String id, Model model){
		model.addAttribute("id",id);
		return "population/heatmap/aerogram";
	}


	@RequestMapping(value="heatmapDetail")
	public String heatmapDetail(Model model, String configId){
		model.addAttribute("configId", configId);
		return "population/heatmap/heatmapDetail";
	}

	@RequestMapping(value="heatmapList")
	public String heatmapList(){

		return "population/heatmap/heatmapIndex";
	}


	
	
	
	@RequestMapping(value="heatmapHistory")
	public String heatMapHistory(HeatMapHistory heatMapHistory,Model model){
		if(StringUtils.isEmpty(heatMapHistory.getQueryTime())){
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			heatMapHistory.setQueryTime(sdf.format(new Date()));
			heatMapHistory.setQueryTimeType(HeatMapHistory.QUERY_TIME_TYPE_DAY);
		}
		model.addAttribute("heatMapHistory", heatMapHistory);
		model.addAttribute("heatMapHistoryList", heatMapService.getHeatMapHistory(heatMapHistory));
		return "population/heatmap/heatmapHistory";
	}
	
	@RequestMapping(value="heatmapSuspicious")
	public String heatMapSuspicious(HeatMapSuspicious heatMapSuspicious, Model model, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                                @RequestParam(required = false, defaultValue = "10") Integer pageSize){
		model.addAttribute("heatMapSuspicious", heatMapSuspicious);
		model.addAttribute("page", heatMapService.getHeatMapSuspicious(heatMapSuspicious, pageNo, pageSize));
		return "population/heatmap/heatmapSuspicious";
	}
	
	@RequestMapping(value = "updateSuspicious")
	@ResponseBody
	public String updateSuspicious(String imsiCode,String status,String configId) {
		try {
			HashMap<String, String> requestMap= new HashMap<String, String>();
			requestMap.put("imsiCode", imsiCode);
			requestMap.put("status", status);
			requestMap.put("configId", configId);
			if (heatMapService.updateSuspicious(requestMap) > 0) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "操作失败";
	}

	@RequestMapping(value = "heatmapDetailByDay")
	public String heatMapDetailByDay(HeatMapDetail heatMapDetail, Model model,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		model.addAttribute("heatMapDetail", heatMapDetail);
		model.addAttribute("page", heatMapService.getHeatMapDetailByDay(heatMapDetail, pageNo, pageSize));
		return "population/heatmap/heatmapDetailByDay";
	}

	@RequestMapping(value = "heatmapDetailByMonth")
	public String heatMapDetailByMonth(HeatMapDetail heatMapDetail, Model model,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		model.addAttribute("heatMapDetail", heatMapDetail);
		model.addAttribute("page", heatMapService.getHeatMapDetailByMonth(heatMapDetail, pageNo, pageSize));
		return "population/heatmap/heatmapDetailByMonth";
	}

	@RequestMapping(value = "heatmapDetailByYear")
	public String heatMapDetailByYear(HeatMapDetail heatMapDetail, Model model,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		model.addAttribute("heatMapDetail", heatMapDetail);
		model.addAttribute("page", heatMapService.getHeatMapDetailByYear(heatMapDetail, pageNo, pageSize));
		return "population/heatmap/heatmapDetailByYear";

	}
	
	@RequestMapping("heatmapCrowdQuery")
	public String heatMapCrowdQuery(CrowdQuery query, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                                @RequestParam(required = false, defaultValue = "10") Integer pageSize, Model model) {
		model.addAttribute("query", query);
		model.addAttribute("page", heatMapService.getHeatMapCrowdQuery(query, pageNo, pageSize));
		return "population/heatmap/heatmapCrowdQuery";
	}
}
