package com.elwyn.bigData.modules.bigdata.dao.oracle;

import java.util.Collection;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.model.bigdata.Distribution;
import com.rainsoft.model.bigdata.DistributionMacRequest;

@MyBatisDao
public interface DistributionMacMapping {
	Collection<Distribution> macPlac(DistributionMacRequest distributionMacRequest);
	Collection<Distribution> macTime(DistributionMacRequest distributionMacRequest);
}
