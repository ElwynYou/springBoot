package com.elwyn.modules.population.config.controller;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.core.paging.Page;
import com.rainsoft.modules.population.config.entity.DangerPersonGroup;
import com.rainsoft.modules.population.config.service.IDangerPersonGroupService;
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

/**
 * 高危人群（关注人群）分组管理Controller
 * @author Sugar
 * @version 2017-04-14
 */
@Controller
@RequestMapping(value = "/population/config/dangerPersonGroup")
public class DangerPersonGroupController extends BaseController {

	@Autowired
	private IDangerPersonGroupService dangerPersonGroupService;
	
	@ModelAttribute
	public DangerPersonGroup get(@RequestParam(required=false) String id) {
		DangerPersonGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dangerPersonGroupService.getById(id);
		}
		if (entity == null){
			entity = new DangerPersonGroup();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(DangerPersonGroup dangerPersonGroup, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                   @RequestParam(required = false, defaultValue = "10") Integer pageSize, Model model) {
		Page<DangerPersonGroup> page = dangerPersonGroupService.findPage(dangerPersonGroup, pageNo, pageSize);
		model.addAttribute("dangerPersonGroup", dangerPersonGroup);
		model.addAttribute("page", page);
		return "population/config/dangerPersonGroupList";
	}

	@RequestMapping(value = "form")
	public String form(DangerPersonGroup dangerPersonGroup, Model model) {
		model.addAttribute("dangerPersonGroup", dangerPersonGroup);
		return "population/config/dangerPersonGroupForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public String save(DangerPersonGroup dangerPersonGroup, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dangerPersonGroup)){
			String msg = (String) model.asMap().get("message");
			if (msg == null) {
				return "数据输入有误";
			}
			return msg;
		}
		dangerPersonGroup.setCreateBy(CurrentUser.get().getId());
		try {
			if (dangerPersonGroupService.save(dangerPersonGroup)) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "保存分组失败";
	}
	
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String id) {
		try {
			if (dangerPersonGroupService.deleteById(id) > 0) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "删除分组失败";
	}

	@RequestMapping("checkName")
	@ResponseBody
	public boolean checkName(String name,String id){
		return  0== dangerPersonGroupService.checkName(name, id);
	}
}