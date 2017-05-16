package com.elwyn.modules.population.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.core.controller.BaseController;
import com.rainsoft.core.paging.Page;
import com.rainsoft.model.JsonResult;
import com.rainsoft.model.User;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.population.config.entity.HeatmapConfig;
import com.rainsoft.modules.population.config.service.IHeatmapConfigService;
import com.rainsoft.spring.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 热力分布管理
 *
 * @Name Yk
 * @Description
 * @Author Administrator
 * @Version 2017年2月9日 下午1:25:21
 * @Copyright 上海云辰信息科技有限公司
 */

@Controller("热力分布管理")
@RequestMapping("/population/config/heatmap")
public class HeatmapConfigController extends BaseController {

	@Autowired
	public IHeatmapConfigService heatmapConfigService;

	/**
	 * 热力添加页面
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public String form(Model model, String id) {
		if (id != null) {
			HeatmapConfig heatingDistributionById = heatmapConfigService.getHeatmapConfigById(id);
			model.addAttribute("heatDistribution", JSONObject.toJSONString(heatingDistributionById));
		}
		return "population/config/heatmapForm";
	}

	@RequestMapping("/list")
	public String list(HeatmapConfig config, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                   @RequestParam(required = false, defaultValue = "20") Integer pageSize, Model model) {
		model.addAttribute("heatmapConfig", config);
		Page<HeatmapConfig> page = heatmapConfigService.findPage(config, pageNo, pageSize);
		model.addAttribute("page", page);
		return "population/config/heatmapList";
	}

	/**
	 * 热力配置添加
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public JsonResult addHeatingDistributionConf(@RequestBody HeatmapConfig addHeatingDistribution) {
		User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		addHeatingDistribution.setCreateBy(user.getId() + "");
		Map map = addHeatingDistribution.getMapData();
		List ary_circle_label = (List) map.get("ary_circle_label");
		List ary_rectangle_label = (List) map.get("ary_rectangle_label");

		if (ary_circle_label.size() > 0) {
			addHeatingDistribution.setMapType("2");
			addHeatingDistribution.setMapInfo(JSONObject.toJSONString(ary_circle_label));
		}
		if (ary_rectangle_label.size() > 0) {
			addHeatingDistribution.setMapType("1");
			addHeatingDistribution.setMapInfo(JSONObject.toJSONString(ary_rectangle_label));
		}

		if (addHeatingDistribution.getId() != null) {
			return heatmapConfigService.updateHeatmapConfig(addHeatingDistribution);
		}
		return heatmapConfigService.addHeatmapConfig(addHeatingDistribution);
	}

	/**
	 * 检查选中范围是否有设备
	 *
	 * @return
	 */
	@RequestMapping("/checkMachine")
	@ResponseBody
	public List<MachineInfo> checkMachine(@RequestBody Map<String, Object> map) {
		User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		return heatmapConfigService.checkMachine(user, map);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delHeatingConfig(@RequestParam("id") String configId) {
		try {
			heatmapConfigService.delHeatingConfig(configId);
			return new JsonResult(JsonResult.SUCCESS, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.ERROR, "删除失败");
		}
	}

	@RequestMapping("/updateStatus")
	@ResponseBody
	public JsonResult updateStatus(String id, String status) {
		int i = heatmapConfigService.updateStatus(id, status);
		if (i > 0) {
			return new JsonResult(JsonResult.SUCCESS, "更新成功");
		} else {
			return new JsonResult(JsonResult.SUCCESS, "更新失败");
		}
	}
}
