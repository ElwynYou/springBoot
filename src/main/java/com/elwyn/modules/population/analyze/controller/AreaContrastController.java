package com.elwyn.modules.population.analyze.controller;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.core.paging.Page;
import com.rainsoft.modules.population.analyze.service.IAreaContrastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name com.rainsoft.modules.population.analyze.controller.AreaContrastController
 * @Description
 * @Author Elwyn
 * @Version 2017/4/7
 * @Copyright 上海云辰信息科技有限公司
 **/
@Controller("流动人口区域碰撞")
@RequestMapping("/population/analyze")
public class AreaContrastController extends BaseController{
	@Autowired
	IAreaContrastService areaContrastService;

	@RequestMapping("area")
	public String areaContrastMenu(){
		return "population/analyze/areacontrast";
	}

	@RequestMapping("areaContast")
	public String areaContast( String data,HttpServletRequest httpServletRequest){

		Page page=new Page(15,getInt(httpServletRequest,"pageNo"));
		page.setList(areaContrastService.areaContast(data,page));
		httpServletRequest.setAttribute("page",page);
		return "population/analyze/areaContrastResult";
	}
}
