package com.elwyn.modules.bigdata.dao.impala;

import com.rainsoft.core.annotation.ImpalaDao;
import com.rainsoft.model.bigdata.Distribution;
import com.rainsoft.model.bigdata.DistributionMacRequest;

import java.util.Collection;


@ImpalaDao
public interface MacFrequencyDao {

    //时间规律
	Collection<Distribution> getFrequencyByMac(DistributionMacRequest distributionMacRequest);
    
    //地点规律
	Collection<Distribution> getLocationByMac(DistributionMacRequest distributionMacRequest);
}
