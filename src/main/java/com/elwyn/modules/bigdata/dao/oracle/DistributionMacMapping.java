package com.elwyn.modules.bigdata.dao.oracle;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.model.bigdata.Distribution;
import com.rainsoft.model.bigdata.DistributionMacRequest;

import java.util.Collection;

@MyBatisDao
public interface DistributionMacMapping {
	Collection<Distribution> macPlac(DistributionMacRequest distributionMacRequest);
	Collection<Distribution> macTime(DistributionMacRequest distributionMacRequest);
}
