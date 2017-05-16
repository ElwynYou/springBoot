package com.elwyn.modules.baseinfo.controller;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.baseinfo.service.IMachineInfoService;
import com.rainsoft.utils.CurrentUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Name com.rainsoft.modules.baseinfo.controller.MachineInfoController
 * @Description 设备信息
 * 
 * @Author Sugar
 * @Version 2017年4月5日 下午8:46:00
 * @Copyright 上海云辰信息科技有限公司
 */
@Controller("baseinfo/machineInfoController")
@RequestMapping("/baseinfo/machineInfo")
public class MachineInfoController extends BaseController {
	@Autowired
	private IMachineInfoService machineInfoService;

	@RequestMapping("list")
	@ResponseBody
	public List<MachineInfo> list(MachineInfo info) {
		if (info == null) {
			info = new MachineInfo();
			info.setAreaCode(CurrentUser.get().getRegion());
		} else if (StringUtils.isBlank(info.getAreaCode())) {
			info.setAreaCode(CurrentUser.get().getRegion());
		}
		return machineInfoService.findList(info);
	}
}
