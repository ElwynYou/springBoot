package com.elwyn.bigData.modules.bigdata.service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainsoft.model.bigdata.ComparisonPlaceRequest;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.model.datatables.DataTablesResponse;
import com.rainsoft.modules.bigdata.dao.impala.ComparisonPlaceDao;

@Repository
public class ComparisonHbaseService {

	@Autowired protected ObjectMapper objectMapper;
	@Autowired ComparisonPlaceDao comparisonDao;
	
	public DataTablesResponse place(DataTablesRequest<ComparisonPlaceRequest> dataTablesRequest) {
		if(null == dataTablesRequest.getFormData().get("json")){
			DataTablesResponse strD = new DataTablesResponse(dataTablesRequest);
			strD.setRecordsFiltered(0);
			strD.setRecordsFiltered(0);
			strD.setRecordsTotal(0);
			strD.setData(null);
			return strD;
		}
		String querydataString = dataTablesRequest.getFormData().get("json").toString().replace("&quot;", "\"");
		ComparisonPlaceRequest[] comparisonPlaceRequest = new ComparisonPlaceRequest[0];
		try {
			comparisonPlaceRequest = objectMapper.readValue(querydataString, ComparisonPlaceRequest[].class);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("comparisonPlaceRequests",comparisonPlaceRequest);
			map.put("comparisonPlaceSize",comparisonPlaceRequest.length);
			dataTablesRequest.setFormData(map);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		Collection<Map<Object, Object>> data = null;
		long count = 0;
		try {
			data = comparisonDao.placeList(dataTablesRequest);
			count = comparisonDao.countComparisonPlace(dataTablesRequest);
		} catch (org.springframework.jdbc.UncategorizedSQLException e) {
			e.printStackTrace();
			DataTablesResponse dt = new DataTablesResponse(dataTablesRequest);
			dt.setError("请求数据超时.");
			return dt;
		} 
		DataTablesResponse dtres = new DataTablesResponse(dataTablesRequest);
		dtres.setRecordsFiltered(count);
		dtres.setRecordsTotal(count);
		dtres.setData(data);
		return dtres;
	}
}
