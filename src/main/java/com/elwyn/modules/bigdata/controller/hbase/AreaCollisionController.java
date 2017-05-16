/**
 * 
 */
package com.elwyn.modules.bigdata.controller.hbase;

import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.model.datatables.DataTablesResponse;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.bigdata.entity.AreaCollisionEntity;
import com.rainsoft.modules.bigdata.service.AreaCollisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 
 * @Name com.rainsoft.modules.bigdata.controller.AreaCollisionController
 * @Description 
 * 
 * @Author lisicai
 * @Version 2016年12月22日 下午3:57:28
 * @Copyright 上海云辰信息科技有限公司
 *
*/
@Controller("Hbase区域碰撞")
@RequestMapping("/areaCollisionH")
public class AreaCollisionController {
	
	@Autowired
	AreaCollisionService areaCollisionService;
	@RequestMapping("/find/equipment")
	@ResponseBody
	public List<Map<String,String>>
						viewEquipment(AreaCollisionEntity areaCollision){
		return areaCollisionService.findListEquipment(areaCollision);	
	}
	@RequestMapping(value="/getEquipment",method= RequestMethod.POST)
	@ResponseBody
	public List<MachineInfo> getEquipment(@RequestBody Map<String,Object> map ){
		return areaCollisionService.getMachineInfoByLngLat(map);
	}
	@RequestMapping("/list/collisionInfo")
	@ResponseBody
	public DataTablesResponse
					listCollisionInfo(@RequestBody DataTablesRequest<AreaCollisionEntity> dataTablesRequest){
			List<String> str = (List<String>) dataTablesRequest.getFormData().get("startTimeList");
			DataTablesResponse dtres = new DataTablesResponse(dataTablesRequest);
			List<Map<String,String>> data = null;
			try{
			
			long count = areaCollisionService.countCollisionInfo(dataTablesRequest);
			
			if(count > 0){
				data = areaCollisionService.findListCollision(dataTablesRequest);
			}
			dtres.setData(data);
			dtres.setRecordsTotal(count);
			dtres.setRecordsFiltered(count);
			}catch(org.springframework.jdbc.UncategorizedSQLException e){
				e.printStackTrace();
				dtres.setData(data);
				dtres.setRecordsTotal(0);
				dtres.setRecordsFiltered(0);
				dtres.setError("请求超时");
			}
			return dtres;
		
	}
}
