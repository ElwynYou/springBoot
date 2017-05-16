/**
 * 
 */
package com.elwyn.modules.bigdata.service.oracle;

import com.rainsoft.model.AreaStation;
import com.rainsoft.model.User;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.bigdata.dao.oracle.GetMachineInfoDao;
import com.rainsoft.modules.bigdata.dao.oracle.IAreaCollisionOracleDao;
import com.rainsoft.modules.bigdata.dao.oracle.IGetListEquipmentDao;
import com.rainsoft.modules.bigdata.entity.AreaCollisionEntity;
import com.rainsoft.spring.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 
 * @Name com.rainsoft.modules.bigdata.service.impl.AreaCollisionServiceImpl
 * @Description 
 * 
 * @Author lisicai
 * @Version 2016年12月22日 下午4:46:26
 * @Copyright 上海云辰信息科技有限公司
 * @deprecated 该类已迁移至iSecBigdata工程中，请使用RpcAreaCollisionOracleService远程接口替换  张云鹏  2017-01-19
 *
*/
@Service
public class AreaCollisionOracleService {
	
	@Autowired
	IGetListEquipmentDao iGetListEquipment;
	@Autowired
	IAreaCollisionOracleDao iAreaCollisionOracleDao;
	@Autowired
	GetMachineInfoDao getMachineInfoDao;
	/* (non-Javadoc)
	 * @see com.rainsoft.modules.bigdata.service.IAreaCollisionService#findListEquipment()
	 */
	public List<Map<String, String>> findListEquipment(AreaCollisionEntity areaCollision) {
		return iGetListEquipment.findListEquipment(areaCollision);
	}
	 public List<MachineInfo> getMachineInfoByLngLat(Map map) {
		//获取当前用户和城区
	        User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
	        Iterator<AreaStation> it = user.getAreas().iterator();
	        List<Long> areas = new ArrayList<>();
	        while (it.hasNext()) {
	            AreaStation area = it.next();
	            areas.add(area.getCode());
	        }
	        List<String> serviceCode = getMachineInfoDao.getUserServiceCode(areas);
	        map.put("serviceCode", serviceCode);
	        return iGetListEquipment.getMachineInfoByMap(map);
	    }
	/* (non-Javadoc)
	 * @see com.rainsoft.modules.bigdata.service.IAreaCollisionService#findListCollisionInfo(com.rainsoft.model.datatables.DataTablesRequest)
	 */
	public List<Map<String, String>> 
			findListCollision(DataTablesRequest<AreaCollisionEntity> dataTablesRequest) {
		return iAreaCollisionOracleDao.findListCollisionInfo(dataTablesRequest);
	}
	/* (non-Javadoc)
	 * @see com.rainsoft.modules.bigdata.service.IAreaCollisionService#countCollisionInfo(com.rainsoft.model.datatables.DataTablesRequest)
	 */
	public long countCollisionInfo(DataTablesRequest<AreaCollisionEntity> dataTablesRequest) {
		return iAreaCollisionOracleDao.countCollisionInfo(regroupDataTablesRequest(dataTablesRequest));
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
