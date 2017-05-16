package com.elwyn.bigData.modules.bigdata.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rainsoft.model.bigdata.Distribution;
import com.rainsoft.model.bigdata.DistributionMacRequest;
import com.rainsoft.model.bigdata.DistributionMacResponse;
import com.rainsoft.modules.bigdata.dao.impala.MacFrequencyDao;


@Service
public class MacFrequencyService {

    @Autowired
    private MacFrequencyDao macFrequencyDao;

    /**
     * 时间规律
     *
     * @param mac,startTime,endTime
     * @return
     */
    public DistributionMacResponse getFrequencyByMac(DistributionMacRequest distributionMacRequest) {
		Collection<Distribution> distributions = macFrequencyDao.getFrequencyByMac(distributionMacRequest);
		DistributionMacResponse distributionMacResponse = new DistributionMacResponse();
		distributionMacResponse.setData(distributions);
		distributionMacResponse.setTarget(distributionMacRequest.getMac());
		return distributionMacResponse;
    }

    /**
     * 地点规律
     *
     * @param mac,startTime,endTime
     * @return
     */
    public DistributionMacResponse getLocationByMac(DistributionMacRequest distributionMacRequest) {
        Collection<Distribution> distributions = macFrequencyDao.getLocationByMac(distributionMacRequest);
		DistributionMacResponse distributionMacResponse = new DistributionMacResponse();
		distributionMacResponse.setData(distributions);
		distributionMacResponse.setTarget(distributionMacRequest.getMac());
		return distributionMacResponse;
    }
}
