package com.elwyn.modules.population.config.controller;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.core.paging.Page;
import com.rainsoft.model.ServiceInfo;
import com.rainsoft.modules.population.config.entity.CommunityConfig;
import com.rainsoft.modules.population.config.service.ICommunityConfigService;
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

import java.util.Date;

/**
 * 小区设置Controller
 * @author Sugar
 * @version 2017-04-14
 */
@Controller
@RequestMapping(value = "/population/config/communityConfig")
public class CommunityConfigController extends BaseController {

	@Autowired
	private ICommunityConfigService communityConfigService;
	
	@ModelAttribute
	public CommunityConfig get(@RequestParam(required=false) String id) {
		CommunityConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = communityConfigService.getById(id);
		}
		if (entity == null){
			entity = new CommunityConfig();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(CommunityConfig communityConfig, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                   @RequestParam(required = false, defaultValue = "10") Integer pageSize, Model model) {
		Page<CommunityConfig> page = communityConfigService.findPage(communityConfig, pageNo, pageSize);
		model.addAttribute("communityConfig", communityConfig);
		model.addAttribute("page", page);
		return "population/config/communityConfigList";
	}

	@RequestMapping(value = "form")
	public String form(CommunityConfig communityConfig, Model model) {
		ServiceInfo info = new ServiceInfo();
		info.setAreaCode(CurrentUser.get().getRegion());
		info.setServiceCode(communityConfig.getServiceCode());
		model.addAttribute("serviceList", communityConfigService.findServiceInfoList(info));
		model.addAttribute("communityConfig", communityConfig);
		return "population/config/communityConfigForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public String save(CommunityConfig communityConfig, Model model, RedirectAttributes redirectAttributes) {
		communityConfig.setUpdateTime(new Date());
		communityConfig.setCreateBy(CurrentUser.get().getId());
		if (!beanValidator(model, communityConfig)){
			String msg = (String) model.asMap().get("message");
			if (msg == null) {
				return "数据输入有误";
			}
			return msg;
		}
		try {
			if (communityConfigService.save(communityConfig)) {
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
			if (communityConfigService.deleteById(id) > 0) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "删除设置失败";
	}
}