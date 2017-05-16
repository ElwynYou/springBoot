package com.elwyn.modules.bigdata.controller.oracle;

import com.rainsoft.modules.bigdata.entity.HScanEndingTraceEntity;
import com.rainsoft.remoting.RpcTrajectoryTracingOracleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.bigdata.controller.oracle
 * @Description
 * @Author Elwyn
 * @Version 2016/12/28 10:27
 * @Copyright 上海云辰信息科技有限公司
 */
@Controller
@RequestMapping("/trajectoryTracingOracle")
public class TrajectoryTracingOracleController {
//    @Autowired
//    private TrajectoryTracingOracleService trajectoryTracingOracleService;
    
    
    @Autowired
    private RpcTrajectoryTracingOracleService rpcRrajectoryTracingOracleService;
    

    @SuppressWarnings("rawtypes")
	@RequestMapping("/getTrajectoryTracing")
    @ResponseBody
    public List<HScanEndingTraceEntity> getTrajectoryTracing(@RequestBody Map map){
        return rpcRrajectoryTracingOracleService.getTrajectoryTracing(map);
    }
    @SuppressWarnings("rawtypes")
	@RequestMapping("/getFrequencyCount")
    @ResponseBody
    public String getFrequencyCount(@RequestBody Map map){
        return Arrays.toString(rpcRrajectoryTracingOracleService.getFrequencyCount(map));
    }

}
