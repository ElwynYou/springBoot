/**
 * 
 */
package com.elwyn.modules.bigdata.service;

import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.bigdata.dao.impala.IAreaCollisionDao;
import com.rainsoft.modules.bigdata.dao.oracle.IGetListEquipmentDao;
import com.rainsoft.modules.bigdata.entity.AreaCollisionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Name com.rainsoft.modules.bigdata.service.impl.AreaCollisionServiceImpl
 * @Description 
 * 
 * @Author lisicai
 * @Version 2016年12月22日 下午4:46:26
 * @Copyright 上海云辰信息科技有限公司
 *
*/

@Service
public class AreaCollisionService {
	
	@Autowired
	IGetListEquipmentDao iGetListEquipment;
	@Autowired
	IAreaCollisionDao iAreaCollisionDao;
	/* (non-Javadoc)
	 * @see com.rainsoft.modules.bigdata.service.IAreaCollisionService#findListEquipment()
	 */
	public List<Map<String, String>> findListEquipment(AreaCollisionEntity areaCollision) {
		return iGetListEquipment.findListEquipment(areaCollision);
	}
	 public List<MachineInfo> getMachineInfoByLngLat(Map map) {
	        return iGetListEquipment.getMachineInfoByMap(map);
	    }
	/* (non-Javadoc)
	 * @see com.rainsoft.modules.bigdata.service.IAreaCollisionService#findListCollisionInfo(com.rainsoft.model.datatables.DataTablesRequest)
	 */
	public List<Map<String, String>> 
			findListCollision(DataTablesRequest<AreaCollisionEntity> dataTablesRequest) {
		return iAreaCollisionDao.findListCollisionInfo(dataTablesRequest);
	}
	/* (non-Javadoc)
	 * @see com.rainsoft.modules.bigdata.service.IAreaCollisionService#countCollisionInfo(com.rainsoft.model.datatables.DataTablesRequest)
	 */
	public long countCollisionInfo(DataTablesRequest<AreaCollisionEntity> dataTablesRequest) {
		return iAreaCollisionDao.countCollisionInfo(regroupDataTablesRequest(dataTablesRequest));
	}

	public DataTablesRequest regroupDataTablesRequest(DataTablesRequest<AreaCollisionEntity> dataTablesRequest){
		Map<String,Object> formData = dataTablesRequest.getFormData();
		List<String> startTimeList = (List<String>) formData.get("startTimeList");
		int len = startTimeList.size();
/*		List<Object> list = new ArrayList<>(); */
		List<String> machineIdList = (List<String>) formData.get("machineIdList");
		List<String> endTimeList = (List<String>) formData.get("endTimeList");
		int lenMachineId = startTimeList.size();
		Map<String,Object> map = new HashMap<>();
		for(int i = 0;i < len;i++){
			List<String> list = new ArrayList<>();
			List<String> machineIdGroupList = new ArrayList<>();
			list.add(startTimeList.get(i));
			list.add(endTimeList.get(i));
			for (String machine : machineIdList) {
				String[] strarray = machine.split(",");
				int  areaId = 0;
				if(!"".equals(strarray[0])){
					  areaId= Integer.parseInt(strarray[0]);//设备区域和地图所画区域对应
				}
				if(areaId == (i+1)){
					machineIdGroupList.add(strarray[1]);
				}
			}
			formData.put("machineIdGroupList"+i, machineIdGroupList);
			formData.put("list"+i, list);
		}
		formData.put("groupNumber", String.valueOf(len));//传入分组数目
		dataTablesRequest.setFormData(formData);//重新给dataTableRequest设值
		return dataTablesRequest;
		
	}
	
}
