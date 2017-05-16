package com.elwyn.modules.bigdata.dao.impala;

import com.rainsoft.core.annotation.ImpalaDao;
import com.rainsoft.model.bigdata.DistributionMacRequest;

import java.util.Collection;

@ImpalaDao
public interface DistributionDao {
	Collection<String> macPlaceNew(DistributionMacRequest distributionMacRequest);
	//Collection<Distribution> macTimeNew(DistributionMacRequest distributionMacRequest);
}
