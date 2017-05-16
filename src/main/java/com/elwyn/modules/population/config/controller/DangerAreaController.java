package com.elwyn.modules.population.config.controller;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.core.paging.Page;
import com.rainsoft.modules.population.config.entity.DangerArea;
import com.rainsoft.modules.population.config.service.IDangerAreaService;
import com.rainsoft.utils.CurrentUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 重点防范库管理Controller
 * @author Sugar
 * @version 2017-04-01
 */
@Controller
@RequestMapping(value = "/population/config/danger/area")
public class DangerAreaController extends BaseController {

	@Autowired
	private IDangerAreaService dangerAreaService;
	
	@ModelAttribute
	public DangerArea get(@RequestParam(required=false) String id) {
		DangerArea entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dangerAreaService.getById(id);
		}
		if (entity == null){
			entity = new DangerArea();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(DangerArea dangerArea, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DangerArea> page = dangerAreaService.findPage(dangerArea, getInt(request, "pageNo"), getInt(request, "pageSize", 10));
		model.addAttribute("dangerArea", dangerArea);
		model.addAttribute("page", page);
		return "population/config/dangerAreaList";
	}

	@RequestMapping(value = "form")
	public String form(DangerArea dangerArea, Model model) {
		model.addAttribute("dangerArea", dangerArea);
		return "population/config/dangerAreaForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public String save(DangerArea dangerArea, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dangerArea)){
			String msg = (String) model.asMap().get("message");
			if (msg == null) {
				return "数据输入有误";
			}
			return msg;
		}
		dangerArea.setCreateBy(CurrentUser.get().getId());
		try {
			if (dangerAreaService.save(dangerArea)) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "保存高危地区失败";
	}
	
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String id) {
		try {
			if (dangerAreaService.deleteById(id) > 0) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "删除高危地区失败";
	}

	@RequestMapping("checkName")
	@ResponseBody
	public boolean checkName(String name,String id){
		return  0== dangerAreaService.checkName(name, id);
	}
}