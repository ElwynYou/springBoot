package com.elwyn.bigData.modules.bigdata.dao.impala;

import java.util.Collection;

import com.rainsoft.core.annotation.ImpalaDao;
import com.rainsoft.model.bigdata.DistributionMacRequest;

@ImpalaDao
public interface DistributionDao {
	Collection<String> macPlaceNew(DistributionMacRequest distributionMacRequest);
	//Collection<Distribution> macTimeNew(DistributionMacRequest distributionMacRequest);
}
