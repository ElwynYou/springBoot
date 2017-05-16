package com.elwyn.modules.population.crowd.controller;

import com.rainsoft.model.User;
import com.rainsoft.modules.population.crowd.entity.TotalCount;
import com.rainsoft.modules.population.crowd.service.IBureauIndexService;
import com.rainsoft.utils.CurrentUser;
import com.rainsoft.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Name com.rainsoft.modules.population.crowd.controller.bureauIndexController
 * @Description
 * @Author Elwyn
 * @Version 2017/4/1
 * @Copyright 上海云辰信息科技有限公司
 **/
@Controller("市局首页")
@RequestMapping("/population")
public class BureauIndexController {

	@Autowired
	IBureauIndexService bureauIndexService;

	@RequestMapping("/bureauIndex")
	public String IndexMenu(Model model){
		String areaCode = CurrentUser.get().getRegion();
		model.addAttribute("peopleCount", bureauIndexService.getTotalPeopleCount(areaCode));
		List<TotalCount> detailCount = getDetailCount();
		model.addAttribute("detailCount",detailCount);
		Date date = DateUtils.getDateByFormats(new Date(), -1, Calendar.DATE);
		model.addAttribute("beginTime",DateUtils.formatDate(DateUtils.getDayInfo(date,true),"yyyy-MM-dd HH:mm"));
		model.addAttribute("endTime",DateUtils.formatDate(DateUtils.getDayInfo(date,false),"yyyy-MM-dd HH:mm"));
		return "population/crowd/bureauIndex";
	}

	public List<TotalCount> getDetailCount(){
		User user= CurrentUser.getUser().getUser();
		String areaCode;
		if(user!=null){
			areaCode=user.getRegion();
		}else {
			areaCode=null;
		}
		return bureauIndexService.getDetailCount(areaCode);
	}


}
