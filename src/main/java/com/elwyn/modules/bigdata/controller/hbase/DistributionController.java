package com.elwyn.modules.bigdata.controller.hbase;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.model.bigdata.DistributionMacRequest;
import com.rainsoft.modules.bigdata.service.MacFrequencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Name com.rainsoft.modules.bigdata.controller.hbase.DistributionController
 * @Description 频次分析
 * 
 * @Author Sugar
 * @Version 2017年1月3日 下午12:20:18
 * @Copyright 上海云辰信息科技有限公司
 */
@Controller("HbaseDistribution")
@RequestMapping("/bigdata/hbase/distribution")
public class DistributionController extends BaseController {

	@Autowired
    private MacFrequencyService macFrequencyService;
	
	@RequestMapping("/mac")
	public String mac() {
		return "bigdata/hbase/distribution/mac";
	}
	

    /**
     * 时间规律
     *
     * @param mac
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getFrequencyByMac", method= RequestMethod.POST)
    public Object getFrequencyByMac(@RequestBody DistributionMacRequest distributionMacRequest) {
        return macFrequencyService.getFrequencyByMac(distributionMacRequest);
    }

    /**
     * 地点规律
     *
     * @param mac
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getLocationByMac", method= RequestMethod.POST)
    public Object getLocationByMac(@RequestBody DistributionMacRequest distributionMacRequest) {
        return macFrequencyService.getLocationByMac(distributionMacRequest);
    }
	
	
}
