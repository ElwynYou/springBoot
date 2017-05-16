package com.elwyn.modules.bigdata.dao.impala;

import com.rainsoft.core.annotation.ImpalaDao;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.modules.bigdata.entity.HClusterResult;
import com.rainsoft.modules.bigdata.entity.HScanCollectionApEntity;
import com.rainsoft.modules.bigdata.entity.HScanEndingTraceEntity;

import java.util.List;

/**
 * @Name com.rainsoft.modules.bigdata.dao.impala
 * @Description 查找同伴
 * @Author Elwyn
 * @Version 2016/12/20 14:13
 * @Copyright 上海云辰信息科技有限公司
 */
@ImpalaDao
public interface GetCompanionDao {
    //根据当前用户serviceCode查询能看到的设备
    List<HScanCollectionApEntity> getScanCollectionAp(List<String> serviceCode) ;
    
    List<HScanEndingTraceEntity> getCompanionInfo(DataTablesRequest dataTablesRequest);
    
    List<HScanEndingTraceEntity> getCompanionInfobyMachineId(List<String> machineIds);
    
    int getCount(DataTablesRequest request);
    
    List<HClusterResult> getHClusterResultInfo(DataTablesRequest dataTablesRequest);
    
    int getHClusterResultCount(DataTablesRequest request);
    
    List<HClusterResult> getGroupResultInfo(DataTablesRequest dataTablesRequest);
    
    int getGroupResultInfoCount(DataTablesRequest request);
}
