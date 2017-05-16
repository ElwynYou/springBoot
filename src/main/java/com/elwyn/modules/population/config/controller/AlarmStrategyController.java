package com.elwyn.modules.population.config.controller;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.core.paging.Page;
import com.rainsoft.modules.population.config.entity.AlarmStrategy;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报警策略管理Controller
 * 
 * @author Sugar
 * @version 2017-04-05
 */
@Controller
@RequestMapping(value = "/population/config/alarmStrategy")
public class AlarmStrategyController extends BaseController {

	@Autowired
	private IAlarmStrategyService alarmStrategyService;

	@ModelAttribute
	public AlarmStrategy get(@RequestParam(required = false) String id) {
		AlarmStrategy entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = alarmStrategyService.getById(id);
		}
		if (entity == null) {
			entity = new AlarmStrategy();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(AlarmStrategy alarmStrategy, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
	                   @RequestParam(required = false, defaultValue = "10") Integer pageSize, Model model) {
		if (!CurrentUser.get().isAdmin()) {//非管理员只能查看自己的列表
			alarmStrategy.setUpdateUid(CurrentUser.get().getId());
		}
		Page<AlarmStrategy> page = alarmStrategyService.findPage(alarmStrategy, pageNo, pageSize);
		model.addAttribute("alarmStrategy", alarmStrategy);
		model.addAttribute("page", page);
		return "population/config/alarmStrategyList";
	}

	@RequestMapping(value = "form")
	public String form(AlarmStrategy alarmStrategy, Model model) {
		model.addAttribute("alarmStrategy", alarmStrategy);
		List<String> hours = new ArrayList<String>();
		for(int i=0; i<24; i++) {
			if (i < 10) {
				hours.add("0"+i);
			} else {
				hours.add(i+"");
			}
		}
		model.addAttribute("hours", hours);
		return "population/config/alarmStrategyForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public String save(AlarmStrategy alarmStrategy, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, alarmStrategy)) {
			String msg = (String) model.asMap().get("message");
			if (msg == null) {
				return "数据输入有误";
			}
			return msg;
		}
		alarmStrategy.setUpdateTime(new Date());
		alarmStrategy.setUpdateUid(CurrentUser.get().getId());
		try {
			if (alarmStrategyService.save(alarmStrategy)) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "保存报警策略失败";
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String id) {
		try {
			if (alarmStrategyService.deleteById(id) > 0) {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "删除报警策略失败";
	}
}