package com.elwyn.modules.population.config.controller;

import com.rainsoft.common.Consts;
import com.rainsoft.core.controller.BaseController;
import com.rainsoft.core.paging.Page;
import com.rainsoft.model.ServiceInfo;
import com.rainsoft.modules.population.config.entity.AlarmSetting;
import com.rainsoft.modules.population.config.entity.AlarmStrategy;
import com.rainsoft.modules.population.config.service.IAlarmSettingService;
import com.rainsoft.modules.population.config.service.IAlarmStrategyService;
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
import java.util.List;

/**
 * 报警设置管理Controller
 * 
 * @author Sugar
 * @version 2017-04-05
 */
@Controller
@RequestMapping(value = "/population/config/alarmSetting")
public class AlarmSettingController extends BaseController {

	@Autowired
	private IAlarmSettingService alarmSettingService;
	
	@Autowired
	private IAlarmStrategyService alarmStrategyService;

	@ModelAttribute
	public AlarmSetting get(@RequestParam(required = false) String id) {
		AlarmSetting entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = alarmSettingService.getById(id);
		}
		if (entity == null) {
			entity = new AlarmSetting();
			entity.setAreaCode(CurrentUser.get().getRegion());
			entity.setServiceType(Consts.SERVICE_TYPE_HOUSING);
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(AlarmSetting alarmSetting, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                   @RequestParam(required = false, defaultValue = "10") Integer pageSize, Model model) {
		if (!CurrentUser.get().isAdmin()) {//非管理员只能查看自己的列表
			alarmSetting.setUpdateUid(CurrentUser.get().getId());
		}
		Page<AlarmSetting> page = alarmSettingService.findPage(alarmSetting, pageNo, pageSize);
		model.addAttribute("alarmSetting", alarmSetting);
		model.addAttribute("page", page);
		return "population/config/alarmSettingList";
	}
	
	@RequestMapping("serviceInfoList")
	@ResponseBody
	public List<ServiceInfo> list(ServiceInfo info) {
		if (info == null) {
			info = new ServiceInfo();
			info.setAreaCode(CurrentUser.get().getRegion());
		} else if (StringUtils.isBlank(info.getAreaCode())) {
			info.setAreaCode(CurrentUser.get().getRegion());
		}
		return alarmSettingService.findServiceInfoList(info);
	}
	
	@RequestMapping(value = "form")
	public String form(AlarmSetting alarmSetting, Model model) {
		model.addAttribute("alarmSetting", alarmSetting);
		ServiceInfo info = new ServiceInfo();
		info.setAreaCode(alarmSetting.getAreaCode());
		info.setServiceType(alarmSetting.getServiceType());
		info.setServiceCode(alarmSetting.getServiceCode());
		model.addAttribute("serviceList", alarmSettingService.findServiceInfoList(info));
		AlarmStrategy strategy = new AlarmStrategy();
		if (!CurrentUser.get().isAdmin()) {//非管理员只能查看自己的列表
			strategy.setUpdateUid(CurrentUser.get().getId());
		}
		model.addAttribute("strategyList", alarmStrategyService.findList(strategy));
		return "population/config/alarmSettingForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public String save(AlarmSetting alarmSetting, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, alarmSetting)) {
			String msg = (String) model.asMap().get("message");
			if (msg == null) {
				return "数据输入有误";
			}
			return msg;
		}
		alarmSetting.setUpdateTime(new Date());
		alarmSetting.setUpdateUid(CurrentUser.get().getId());
		try {
			if (alarmSettingService.save(alarmSetting)) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "保存报警设置失败";
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String id) {
		try {
			if (alarmSettingService.deleteById(id) > 0) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "删除报警设置失败";
	}
}