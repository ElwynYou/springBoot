package com.elwyn.bigData.modules.bigdata.dao.impala;

import java.util.Collection;
import java.util.Map;

import com.rainsoft.core.annotation.ImpalaDao;
import com.rainsoft.model.bigdata.ComparisonPlaceRequest;
import com.rainsoft.model.datatables.DataTablesRequest;

@ImpalaDao
public interface ComparisonPlaceDao {
	Collection<Map<Object, Object>> placeList(DataTablesRequest<ComparisonPlaceRequest> DataTablesRequest);
	long countComparisonPlace(DataTablesRequest<ComparisonPlaceRequest> DataTablesRequest);

}
