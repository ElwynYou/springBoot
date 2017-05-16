package com.elwyn.modules.bigdata.controller.hbase;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.modules.bigdata.entity.HScanEndingTraceEntity;
import com.rainsoft.modules.bigdata.service.TrajectoryTracingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.bigdata.controller.hbase.TraceController
 * @Description 轨迹追踪
 * 
 * @Author Sugar
 * @Version 2017年1月3日 下午12:20:18
 * @Copyright 上海云辰信息科技有限公司
 */
@Controller("HbaseTrace")
@RequestMapping("/bigdata/hbase/trace")
public class TraceController extends BaseController {

	@Autowired
	TrajectoryTracingService trajectoryTracingService;


	@RequestMapping("/location")
	public String mac() {
		return "bigdata/hbase/trace/location";
	}

	@RequestMapping("/getMachineLocation")
	@ResponseBody
	public List<HScanEndingTraceEntity> getMachineLocation (@RequestBody Map map){
		return trajectoryTracingService.getLocationByMap(map);
	}


	@RequestMapping("/getFrequencyCount")
	@ResponseBody
	public int[] getFrequencyCount (@RequestBody Map map){
		return trajectoryTracingService.getFrequencyCount(map);
	}
}
