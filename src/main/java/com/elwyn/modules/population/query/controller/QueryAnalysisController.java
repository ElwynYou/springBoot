package com.elwyn.modules.population.query.controller;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.core.controller.BaseController;
import com.rainsoft.core.paging.Page;
import com.rainsoft.model.User;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.model.datatables.DataTablesResponse;
import com.rainsoft.modules.population.query.entity.AnalysisMachine;
import com.rainsoft.modules.population.query.entity.CrowdQuery;
import com.rainsoft.modules.population.query.entity.MachineCollisionQuery;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;
import com.rainsoft.modules.population.query.service.IQueryAnalysisService;
import com.rainsoft.taglib.SysFunctions;
import com.rainsoft.utils.CurrentUser;
import com.rainsoft.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Name QueryAnalysisController
 * @Description
 * @Author Elwyn
 * @Version 2017/3/29
 * @Copyright 上海云辰信息科技有限公司
 **/
@Controller("查询分析")
@RequestMapping("/population/query")
public class QueryAnalysisController extends BaseController {
	@Autowired
	IQueryAnalysisService queryAnalysisService;

	/**
	 * 人群查询页面
	 * 
	 * @return
	 */
	@RequestMapping("/crowdList")
	public String crowdQueryMenu(CrowdQuery query, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                             @RequestParam(required = false, defaultValue = "20") Integer pageSize, Model model) {
		if (StringUtils.isBlank(query.getAreaCode())) {
			query.setAreaCode(CurrentUser.get().getRegion());
		}
		if(StringUtils.isEmpty(query.getBeginTime()) && StringUtils.isEmpty(query.getEndTime())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = new Date(); 
			query.setEndTime(sdf.format(date));
			Calendar calendar = Calendar.getInstance(); //得到日历
			calendar.setTime(date);//把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
			Date dBefore = calendar.getTime(); 
			query.setBeginTime(sdf.format(dBefore));
			
			Page<ScanEndingImproves> page= new Page<ScanEndingImproves>();
			page.setList(new ArrayList<ScanEndingImproves>());
			model.addAttribute("page", page);
		}else{
			model.addAttribute("page", queryAnalysisService.findCowrdList(query, pageNo, pageSize));
		}
		model.addAttribute("query", query);
		return "population/query/crowdQuery";
	}
	/**
	 * 人群查询页面下载
	 * 
	 * @return
	 */
	@RequestMapping("/crowdListDownload")
	@ResponseBody
	public DataTablesResponse crowdListDownload(@RequestBody DataTablesRequest<ScanEndingImproves> dataTablesRequest){
		 Object obj = dataTablesRequest.getFormData().get("crowdQuery");
		 CrowdQuery crowdQuery = JSONObject.toJavaObject(JSONObject.parseObject(JSONObject.toJSONString(obj)), CrowdQuery.class);
		 List<ScanEndingImproves> list = null;
		 DataTablesResponse dtr = new DataTablesResponse();
		 dtr.setDataTablesRequest(dataTablesRequest);
		 int pageNo = dataTablesRequest.getDraw();
		 int pageSize = dataTablesRequest.getLength();
		 if(pageNo!= 0 && pageSize !=0){
			 Page<ScanEndingImproves> page = queryAnalysisService.findCowrdList(crowdQuery, pageNo, pageSize);
			 list = page.getList();
		} else {
			list = new ArrayList<ScanEndingImproves>();
			int no=1;
			int size = 1000;
			int totalCount = dataTablesRequest.getReturnCount();
			int count = totalCount <= size ? 1 : totalCount % size != 0 ? (totalCount / size) + 1 : totalCount / size;
			for (; no <= count; no++) {
				list.addAll(queryAnalysisService.findCowrdList(crowdQuery, no, size).getList());
			}
		}
		
		 for(ScanEndingImproves sei:list){
			 sei.setOperators(SysFunctions.dictLabel("1001", sei.getOperators()));
			 sei.setPhoneArea(SysFunctions.areaName(sei.getPhoneArea()));
			sei.setHighrisk(sei.getHighrisk().equals("1") ? "是" : "否");
		 }
		 dtr.setData(list);
		 return dtr;
		
	}
	/**
	 * 设备查询
	 * 
	 * @return
	 */
	@RequestMapping("/machineList")
	public String machineQuery(AnalysisMachine analysisMachine, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                           @RequestParam(required = false, defaultValue = "20") Integer pageSize, Model model) {
		if (StringUtils.isBlank(analysisMachine.getAreaCode())) {
			analysisMachine.setAreaCode(CurrentUser.get().getRegion());
		}

		if(analysisMachine.getBeginTime()!=null&&!"".equals(analysisMachine.getBeginTime())){

			Page<AnalysisMachine> page= queryAnalysisService.findMachineList(analysisMachine, pageNo, pageSize);
			model.addAttribute("page",page);

		}
		if(analysisMachine.getBeginTime()==null || "".equals(analysisMachine.getBeginTime())){
			Date date = DateUtils.getDateByFormats(new Date(), -7, Calendar.DATE);
			analysisMachine.setBeginTime(DateUtils.formatDate(DateUtils.getDayInfo(date,true),"yyyy-MM-dd HH:mm"));
			analysisMachine.setEndTime(DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm"));
		}
		model.addAttribute("analysisMachine",analysisMachine);
		return "population/query/machineQuery";
	}


	/**
	 * 设备查询地图页面
	 * 
	 * @return
	 */
	@RequestMapping("/machineMapList")
	public String machineQueryMapMenu(HttpServletRequest httpServletRequest) {
		User user = CurrentUser.get();
		httpServletRequest.setAttribute("areaCode", user.getRegion());
		return "population/query/machineMapQuery";
	}

	/**
	 * 设备查询
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/machineMapQuery")
	@ResponseBody
	public List<AnalysisMachine> machineMapQuery(AnalysisMachine analysisMachine) {
		User user = CurrentUser.get();
		List<AnalysisMachine> analysisMachines = queryAnalysisService.machineMapQuery(analysisMachine, user);
		return analysisMachines;
	}

	/**
	 * 碰撞分析
	 * 
	 * @return
	 */
	@RequestMapping("/collision")
	public String collision(MachineCollisionQuery machineCollisionQuery, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                        @RequestParam(required = false, defaultValue = "20") Integer pageSize, Model model) {
		model.addAttribute("machineList", queryAnalysisService.getMachine(CurrentUser.getUser().getUser().getRegion()));
		Page<ScanEndingImproves> page = queryAnalysisService.collisionQuery(machineCollisionQuery, pageNo, pageSize);
		model.addAttribute("page",page);
		model.addAttribute("machineCollisionQuery",machineCollisionQuery);
		return "population/analyze/collisionQuery";
	}


}
