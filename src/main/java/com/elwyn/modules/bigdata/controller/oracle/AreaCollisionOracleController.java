/**
 * 
 */
package com.elwyn.modules.bigdata.controller.oracle;

import com.rainsoft.model.User;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.model.datatables.DataTablesResponse;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.bigdata.entity.AreaCollisionEntity;
import com.rainsoft.remoting.RpcAreaCollisionOracleService;
import com.rainsoft.spring.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Controller("区域碰撞")
@RequestMapping("/areaCollision")
public class AreaCollisionOracleController {

//	@Autowired
//	AreaCollisionOracleService areaCollisionService;

	@Autowired
	RpcAreaCollisionOracleService rpcAreaCollisionService;
	

	@RequestMapping(value = "/getEquipment", method = RequestMethod.POST)
	@ResponseBody
	public List<MachineInfo> getEquipment(@RequestBody Map<String, Object> map) {
		
		User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		
		return rpcAreaCollisionService.getMachineInfoByLngLat(user,map);
	}

	@RequestMapping("/list/collisionInfo")
	@ResponseBody
	public DataTablesResponse listCollisionInfo(@RequestBody DataTablesRequest<AreaCollisionEntity> dataTablesRequest) {
		
//		long count = rpcAreaCollisionService.countCollisionInfo(dataTablesRequest);
//		List<Map<String, String>> data = null;
//		if (count > 0) {
//			data = rpcAreaCollisionService.findListCollision(dataTablesRequest);
//		}
//		DataTablesResponse dtres = new DataTablesResponse(dataTablesRequest);
//		dtres.setData(data);
//		dtres.setRecordsTotal(count);
//		dtres.setRecordsFiltered(count);
//		return dtres;
		
		DataTablesResponse response = rpcAreaCollisionService.listCollisionPage(dataTablesRequest);
		response.setDataTablesRequest(dataTablesRequest);
		response.setDraw(dataTablesRequest.getDraw());
		return response;
	}

	@RequestMapping("/find/equipment")
	@ResponseBody
	public List<Map<String, String>> viewEquipment(AreaCollisionEntity areaCollision) {
		return rpcAreaCollisionService.findListEquipment(areaCollision);
	}
}
