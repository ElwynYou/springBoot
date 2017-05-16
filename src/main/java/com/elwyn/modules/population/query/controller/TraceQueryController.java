package com.elwyn.modules.population.query.controller;

import com.rainsoft.core.paging.Page;
import com.rainsoft.model.User;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;
import com.rainsoft.modules.population.query.service.ITraceQueryService;
import com.rainsoft.spring.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.populationMovement.controller
 * @Description
 * @Author Elwyn
 * @Version 2017/2/15 10:15
 * @Copyright 上海云辰信息科技有限公司
 */
@RequestMapping("/population/query")
@Controller("轨迹查询")
public class TraceQueryController {

	@Autowired
	ITraceQueryService traceQueryService;


	@RequestMapping(value = "/trace", method = RequestMethod.GET)
	public String gettraceQuery(Model model, String imsiCode, String startTime, String endTime) {
		model.addAttribute("imsiCode", imsiCode);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		return "population/query/traceQuery";
	}

	/**
	 * 搜索调用
	 *
	 * @param map
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Map getTraceQuery(@RequestBody Map map, HttpServletRequest httpServletRequest) {
		List<ScanEndingImproves> traceQuery;
		User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		traceQuery = traceQueryService.getTraceQuery(map, user);
		httpServletRequest.getSession().setAttribute("tarceQuery", traceQuery);
		Page page = new Page(10, 1, traceQuery);//默认第一页
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("page", page);
		dataMap.put("machineInfo", traceQuery);
		page.setUrl("javascript:goPage(#page#)");
		dataMap.put("nav",page.toString());
		return dataMap;
	}

	@RequestMapping("changePage")
	@ResponseBody
	public Map getPage(String pageNo, HttpServletRequest httpServletRequest) {
		List<ScanEndingImproves> tarceQuery = new ArrayList<>();
		tarceQuery = (List<ScanEndingImproves>) httpServletRequest.getSession().getAttribute("tarceQuery");
		Page page = new Page(10, Integer.valueOf(pageNo), tarceQuery);
		Map<String,Object> dataMap = new HashMap<>();
		page.setUrl("javascript:goPage(#page#)");
		dataMap.put("nav",page.toString());
		dataMap.put("page", page);
		return dataMap;
	}
}
