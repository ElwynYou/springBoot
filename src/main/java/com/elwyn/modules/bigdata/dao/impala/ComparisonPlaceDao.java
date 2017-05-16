package com.elwyn.modules.bigdata.dao.impala;

import com.rainsoft.core.annotation.ImpalaDao;
import com.rainsoft.model.bigdata.ComparisonPlaceRequest;
import com.rainsoft.model.datatables.DataTablesRequest;

import java.util.Collection;
import java.util.Map;

@ImpalaDao
public interface ComparisonPlaceDao {
	Collection<Map<Object, Object>> placeList(DataTablesRequest<ComparisonPlaceRequest> DataTablesRequest);
	long countComparisonPlace(DataTablesRequest<ComparisonPlaceRequest> DataTablesRequest);

}
