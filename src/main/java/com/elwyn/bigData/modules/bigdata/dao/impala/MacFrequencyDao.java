package com.elwyn.bigData.modules.bigdata.dao.impala;

import java.util.Collection;

import com.rainsoft.core.annotation.ImpalaDao;
import com.rainsoft.model.bigdata.Distribution;
import com.rainsoft.model.bigdata.DistributionMacRequest;


@ImpalaDao
public interface MacFrequencyDao {

    //时间规律
	Collection<Distribution> getFrequencyByMac(DistributionMacRequest distributionMacRequest);
    
    //地点规律
	Collection<Distribution> getLocationByMac(DistributionMacRequest distributionMacRequest);
}
