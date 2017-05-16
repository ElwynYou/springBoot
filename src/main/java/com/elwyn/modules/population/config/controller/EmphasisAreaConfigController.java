package com.elwyn.modules.population.config.controller;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.core.paging.Page;
import com.rainsoft.model.ServiceInfo;
import com.rainsoft.modules.population.config.entity.EmphasisAreaConfig;
import com.rainsoft.modules.population.config.service.IEmphasisAreaConfigService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 重点区域设置Controller
 * @author Sugar
 * @version 2017-04-14
 */
@Controller
@RequestMapping(value = "/population/config/emphasisAreaConfig")
public class EmphasisAreaConfigController extends BaseController {

	@Autowired
	private IEmphasisAreaConfigService emphasisAreaConfigService;
	
	@ModelAttribute
	public EmphasisAreaConfig get(@RequestParam(required=false) String id) {
		EmphasisAreaConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = emphasisAreaConfigService.getById(id);
		}
		if (entity == null){
			entity = new EmphasisAreaConfig();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(EmphasisAreaConfig emphasisAreaConfig, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                   @RequestParam(required = false, defaultValue = "10") Integer pageSize, Model model) {
		Page<EmphasisAreaConfig> page = emphasisAreaConfigService.findPage(emphasisAreaConfig, pageNo, pageSize);
		model.addAttribute("emphasisAreaConfig", emphasisAreaConfig);
		model.addAttribute("page", page);
		return "population/config/emphasisAreaConfigList";
	}

	@RequestMapping(value = "form")
	public String form(EmphasisAreaConfig emphasisAreaConfig, Model model) {
		ServiceInfo info = new ServiceInfo();
		info.setAreaCode(CurrentUser.get().getRegion());
		info.setServiceCode(emphasisAreaConfig.getServiceCode());
		model.addAttribute("serviceList", emphasisAreaConfigService.findServiceInfoList(info));
		model.addAttribute("emphasisAreaConfig", emphasisAreaConfig);
		List<String> hours = new ArrayList<String>();
		for(int i=0; i<24; i++) {
			if (i < 10) {
				hours.add("0"+i);
			} else {
				hours.add(i+"");
			}
		}
		model.addAttribute("hours", hours);
		return "population/config/emphasisAreaConfigForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public String save(EmphasisAreaConfig emphasisAreaConfig, Model model, RedirectAttributes redirectAttributes) {
		emphasisAreaConfig.setUpdateTime(new Date());
		emphasisAreaConfig.setCreateBy(CurrentUser.get().getId());
		if (!beanValidator(model, emphasisAreaConfig)){
			String msg = (String) model.asMap().get("message");
			if (msg == null) {
				return "数据输入有误";
			}
			return msg;
		}
		try {
			if (emphasisAreaConfigService.save(emphasisAreaConfig)) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "保存设置失败";
	}
	
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String id) {
		try {
			if (emphasisAreaConfigService.deleteById(id) > 0) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "删除设置失败";
	}
}