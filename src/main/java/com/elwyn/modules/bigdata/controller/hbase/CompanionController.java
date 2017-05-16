package com.elwyn.modules.bigdata.controller.hbase;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.core.utils.Utils;
import com.rainsoft.model.User;
import com.rainsoft.model.dataFeedBack.DataQualityStats;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.model.datatables.DataTablesResponse;
import com.rainsoft.model.functiontree.Function;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.remoting.RpcFindCompanionService;
import com.rainsoft.service.dataFeedback.DataQualityService;
import com.rainsoft.spring.security.UserDetailsImpl;
import com.rainsoft.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.bigdata.controller.hbase.CompanioController
 * @Description 同伴分析
 * 
 * @Author Sugar
 * @Version 2017年1月3日 上午11:29:14
 * @Copyright 上海云辰信息科技有限公司
 */
@Controller("HbaseCompanion")
@RequestMapping("/bigdata/hbase/companion")
public class CompanionController extends BaseController {

//	@Autowired
//	private FindCompanionService findCompanionService;
	
	@Autowired
	private RpcFindCompanionService rpcFindCompanionService;
	
	
	
	@Qualifier("dataerrorService")
	@Autowired
	private DataQualityService dataQualityService;

	@RequestMapping("/find")
	public String find() {
		return "bigdata/hbase/companion/find";
	}

	@RequestMapping("/group")
	public String group() {
		return "bigdata/hbase/companion/group";
	}

	/**
	 * 查找同伴
	 * 
	 * @param dataTablesRequest
	 * @return
	 */
	@RequestMapping("/find/list")
	@ResponseBody
	@Function("同伴分析列表")
	public DataTablesResponse findList(@RequestBody DataTablesRequest<DataQualityStats> dataTablesRequest) {
		int type = Utils.parseInt(dataTablesRequest.getFormData().get("id"));

		if (type == 0) {
			DataTablesResponse dtres = new DataTablesResponse(dataTablesRequest);
			dtres.setData(null);
			dtres.setRecordsTotal(0);
			dtres.setRecordsFiltered(0);
			return dtres;
		}
		
		
		User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		
		return rpcFindCompanionService.getCompanionInfo(user,dataTablesRequest);
	}
	
	
	@RequestMapping("/find/code")
	@ResponseBody
	public String getServiceCodeByName(String serviceName){
		
		return rpcFindCompanionService.getServiceCodeByName(serviceName);
	}
	
	

	@RequestMapping(value = "/getEquipmentAll", method = RequestMethod.POST)
	@ResponseBody
	public List<MachineInfo> getEquipmentAll(@RequestBody Map<String, Object> map) {
		// 获取当前用户和城区
		User user = CurrentUser.getUser().getUser();
		String region = user.getRegion();
		Map<String, Object> data = new HashMap<>();
		if (map != null && !map.entrySet().isEmpty()) {
			data.putAll(map);
		}
		data.put("region", region);
		List<MachineInfo> machineInfoList = rpcFindCompanionService.getMachineInfoByServiceCode(data);
		return machineInfoList;
	}
	
	@ResponseBody
	@RequestMapping(value="/group/list", method= RequestMethod.POST)
	@Function("同伴归类列表")
	public DataTablesResponse getCompanionGroup(@RequestBody DataTablesRequest  dataTablesRequest){
		
		return rpcFindCompanionService.getCompanionGroup(dataTablesRequest);
	}
}
