package com.elwyn.bigData.modules.bigdata.service.oracle;

import com.rainsoft.modules.bigdata.dao.oracle.TrajectoryTracingOracleDao;
import com.rainsoft.modules.bigdata.entity.HScanEndingTraceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.bigdata.service.oracle
 * @Description
 * @Author Elwyn
 * @Version 2016/12/28 10:29
 * @Copyright 上海云辰信息科技有限公司
 */
@Service
public class TrajectoryTracingOracleService {
    @Autowired
    private TrajectoryTracingOracleDao trajectoryTracingOracleDao;

    @SuppressWarnings("rawtypes")
	public List<HScanEndingTraceEntity>getTrajectoryTracing(Map map){
        /*List<String> serviceCodes=getServiceCodeByArea(getMachineInfoDao);
        map.put("serviceCodes",serviceCodes);*/
        List<HScanEndingTraceEntity> hScanEndingTraceList=trajectoryTracingOracleDao.getTrajectoryTraceList(map);
        int count=0;
        for(int i=0;i<hScanEndingTraceList.size();i++){
            for(int j=0;j<hScanEndingTraceList.size();j++){
                if(hScanEndingTraceList.get(i).equals(hScanEndingTraceList.get(j))){
                    count++;
                }
            }
            hScanEndingTraceList.get(i).setCountTimes(Integer.toString(count));
            count=0;
        }
        return hScanEndingTraceList;
    }

    /**
     * 获取时段频次
     * @param map
     * @return
     */
    @SuppressWarnings("rawtypes")
	public int[] getFrequencyCount(Map map){
       /* List<String> serviceCodes=getServiceCodeByArea(getMachineInfoDao);
        map.put("serviceCodes",serviceCodes);*/
        List<HScanEndingTraceEntity> hScanEndingTraceEntities=trajectoryTracingOracleDao.getFrequencyCount(map);
        int k=0;
        int[] j =new int[12];
        for (int i = 0; i < 12; i++) {
           k=2*i;
            for (HScanEndingTraceEntity hScanEndingTraceEntity : hScanEndingTraceEntities) {
                if (Integer.valueOf(hScanEndingTraceEntity.getHours())==k){
                    j[i]=Integer.valueOf(hScanEndingTraceEntity.getFrequency());
                    break;
                }
            }
        }
        return j;
    }
}
