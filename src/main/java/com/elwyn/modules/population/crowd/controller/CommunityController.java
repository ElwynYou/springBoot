/**
 * 
 */
package com.elwyn.modules.population.crowd.controller;

import com.rainsoft.cache.DictionaryCache;
import com.rainsoft.core.controller.BaseController;
import com.rainsoft.core.paging.Page;
import com.rainsoft.model.User;
import com.rainsoft.modules.population.crowd.entity.*;
import com.rainsoft.modules.population.crowd.service.ICommunityService;
import com.rainsoft.utils.CurrentUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * @Name com.rainsoft.modules.population.crowd.controller.CommunityController
 * @Description 
 * @Author xa
 * @Version 2017年4月13日 下午12:56:52
 * @Copyright 上海云辰信息科技有限公司
 */
@Controller
@RequestMapping("/population/crowd")
public class CommunityController  extends BaseController{
	@Autowired
	ICommunityService communtiyService;
	
	@RequestMapping("/communityEstate")
	public String communityEstate(CrowdManage crowdManage, HttpServletRequest request, Model model, @RequestParam(value="changeType",required=false) String changeType, @CookieValue(value="communityType",required = false, defaultValue = "colorLump")String communityType) {
		if(StringUtils.isNotEmpty(changeType)){
			communityType =changeType;
		}
		String returnPage = "population/crowd/communityEstate";
		Integer pageSize = 10;
		if (communityType.equals("colorLump")) {
			returnPage = "population/crowd/communityEstateColorLump";
			pageSize = 6;
		}
		if (StringUtils.isEmpty(crowdManage.getAreaCode())) {
			User user = CurrentUser.getUser().getUser();
			String areaCode = user.getRegion();
			crowdManage.setAreaCode(areaCode);
		}
		if (StringUtils.isEmpty(crowdManage.getQueryTime())) {
			Date nowDate = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			crowdManage.setQueryTime(sdf.format(nowDate));
		}
		crowdManage.setServiceType("z");
		Page<CrowdManage> page = communtiyService.getCommunityManage(crowdManage, getInt(request, "pageNo", 1),
				getInt(request, "pageSize", pageSize));
		model.addAttribute("crowdManage", crowdManage);
		model.addAttribute("page", page);
		return returnPage;
	}
	
	@RequestMapping("/communityDetail" )
	public String communityDetail(CrowdDetail crowdDetail,
	                              @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                              @RequestParam(required = false, defaultValue = "10") Integer pageSize, Model model) {
		Map<String, String> peopleTypes = DictionaryCache.getDicByParentCode("X1001");
		model.addAttribute("peopleTypes", peopleTypes);
		Page<CrowdDetail> page = communtiyService.getCommunityDetail(crowdDetail, pageNo, pageSize);
		model.addAttribute("crowdDetail", crowdDetail);
		model.addAttribute("page", page);
		return "population/crowd/communityDetail";
	}
	
	@RequestMapping("/communityAnalyze" )
    public String communityAnalyze(Model model, String serviceCode, String serviceName, String queryTime){
        model.addAttribute("serviceCode",serviceCode);
        model.addAttribute("serviceName",serviceName);
        model.addAttribute("queryTime",queryTime);
        return "population/crowd/communityAnalyze";
    }
	
	@RequestMapping("/queryCommunityAnalyze")
	@ResponseBody
	public CrowdAnalyze getAnalyze(@RequestBody HashMap<String, String> requestMap) {
		CrowdAnalyze crowdAnalyze = communtiyService.getAnalyze(requestMap);
		List<CrowdAnalyzeTemp> pt = crowdAnalyze.getPersonTypeMap();
		List<CrowdAnalyzeTemp> newpt = new ArrayList<CrowdAnalyzeTemp>();
		Map<String, String> peopleTypes = DictionaryCache.getDicByParentCode("X1001");
		for (Entry<String, String> peopleType : peopleTypes.entrySet()) {
			CrowdAnalyzeTemp newCrowdAnalyzeTemp = new CrowdAnalyzeTemp();
			newCrowdAnalyzeTemp.setKey(peopleType.getValue());
			if (pt != null && !pt.isEmpty()) {
				for (int i = 0; i < pt.size(); i++) {
					if (peopleType.getKey().equals(pt.get(i).getKey())) {
						newCrowdAnalyzeTemp.setValue(pt.get(i).getValue());
					}
				}
			}
			if (StringUtils.isEmpty(newCrowdAnalyzeTemp.getValue())) {
				newCrowdAnalyzeTemp.setValue("0");
			}
			newpt.add(newCrowdAnalyzeTemp);
		}
		crowdAnalyze.setPersonTypeMap(newpt);
		return crowdAnalyze;
	}
	@RequestMapping("/communityAbnormalDetail" )
	public String communityAbnormalDetail(CrowdDetail crowdDetail,
	                                      @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize, Model model) {
		model.addAttribute("crowdDetail", crowdDetail);
		Page<CrowdDetail> page = communtiyService.getCommunityAbnormalDetail(crowdDetail, pageNo, pageSize);
		model.addAttribute("page", page);
		return "population/crowd/communityAbnormalDetail";
	}
	@RequestMapping("/deleteAbnormal")
	@ResponseBody
	public String deleteAbnormal(String imsiCode,String serviceCode) {
		try {
			HashMap<String, String> requestMap= new HashMap<String, String>();
			requestMap.put("imsiCode", imsiCode);
			requestMap.put("serviceCode", serviceCode);
			int row = communtiyService.deleteAbnormal(requestMap);
			if (row > 0) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "操作失败";
	}
	@RequestMapping("/updateCommunitySignDetail")
	@ResponseBody
	public String updateSignDetail(String imsiCode,String signType,String serviceCode) {
		try {
			HashMap<String, String> requestMap= new HashMap<String, String>();
			requestMap.put("imsiCode", imsiCode);
			requestMap.put("peopleType", signType);
			requestMap.put("serviceCode", serviceCode);
			int row = communtiyService.updateSignDetail(requestMap);
			if (row > 0) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "操作失败";
	}
	
	@RequestMapping("/communityHistory")
	public String crowdHistory(CrowdHistory crowdHistory, Model model) {
		crowdHistory.setTimeFomat("YYYY-MM-DD");
		model.addAttribute("crowdHistory", crowdHistory);
		model.addAttribute("communityHistoryList", communtiyService.getCommunityHistory(crowdHistory));
		return "population/crowd/communityHistory";
	}
}
