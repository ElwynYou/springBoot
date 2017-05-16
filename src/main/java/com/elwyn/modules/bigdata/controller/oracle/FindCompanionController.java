package com.elwyn.modules.bigdata.controller.oracle;

import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.model.datatables.DataTablesResponse;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.bigdata.entity.ControlGroupEntity;
import com.rainsoft.modules.bigdata.entity.TabMacFriConfMain;
import com.rainsoft.modules.bigdata.service.oracle.FindCompanionOracleService;
import com.rainsoft.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.bigdata.controller.oracle
 * @Description 查找同伴
 * @Author Elwyn
 * @Version 2016/12/29 16:16
 * @Copyright 上海云辰信息科技有限公司
 */
@Controller
@RequestMapping("/findCompanion")
public class FindCompanionController {

	@Autowired
	private FindCompanionOracleService findCompanionOracleService;

	/**
	 * Mac设置
	 * 
	 * @param dataTablesRequest
	 * @return
	 */
	@RequestMapping("/companionMainConf")
	@ResponseBody
	public DataTablesResponse getCompanionMainConfList(@RequestBody DataTablesRequest dataTablesRequest) {

		return findCompanionOracleService.getMacFriConfMainList(dataTablesRequest);
	}

	/**
	 * 删除MacConfMain
	 */
	@RequestMapping("/companionMainConf/delete")
	@ResponseBody
	public Response getCompanionMainConfList(TabMacFriConfMain tabMacFriConfMain) {

		return findCompanionOracleService.deleteMacConfMain(tabMacFriConfMain);
	}

	/**
	 * 添加MacMain
	 */

	@RequestMapping("/companionMainConf/addMacMain")
	@ResponseBody
	public Response addMacMain(TabMacFriConfMain tabMacFriConfMain) {

		return findCompanionOracleService.addMacFriConfMainList(tabMacFriConfMain);
	}


	/**
	 * 修改有效状态
	 */

	@RequestMapping("/companionMainConf/validStatus")
	@ResponseBody
	public Response update(TabMacFriConfMain tabMacFriConfMain) {

		return findCompanionOracleService.updateMainValidStatus(tabMacFriConfMain);
	}


	/**
	 * 修改对照组以及副设备
	 */

	@RequestMapping("/companionMainConf/updateMacMainInfo")
	@ResponseBody
	public Response updateMacMainInfo(TabMacFriConfMain tabMacFriConfMain) {

		return findCompanionOracleService.updateMacMainInfo(tabMacFriConfMain);
	}
	/**
	 * 根据设备名称获取设备
	 */
	@RequestMapping("/getMachineinfoByNames")
	@ResponseBody
	public List<MachineInfo> getMachineInfoByNames(Map map) {
		return findCompanionOracleService.getMachineInfoByNames(map);
	}

	/**
	 * 查找同伴
	 */
	@RequestMapping("/getCompanionGroup")
	@ResponseBody
	public DataTablesResponse getCompanionGroup(@RequestBody DataTablesRequest dataTablesRequest) {
		return findCompanionOracleService.FindCompanionList(dataTablesRequest);
	}

	/**
	 * 对照组分页列表
	 * 
	 * @param dataTablesRequest
	 * @return
	 */
	@RequestMapping("/pageControlGroup")
	@ResponseBody
	public DataTablesResponse pageTemplate(@RequestBody DataTablesRequest<ControlGroupEntity> dataTablesRequest) {
		dataTablesRequest.setModeClass(ControlGroupEntity.class);
		Collection<ControlGroupEntity> data = findCompanionOracleService.findPageControlGroup(dataTablesRequest);
		long count = findCompanionOracleService.findPageCount(dataTablesRequest);
		DataTablesResponse dtres = new DataTablesResponse(dataTablesRequest);
		dtres.setData(data);
		dtres.setRecordsTotal(count);
		dtres.setRecordsFiltered(count);
		return dtres;
	}

	/**
	 * 添加对照组信息
	 * 
	 * @param controlGroupEntity
	 * @param model
	 * @return
	 */
	@RequestMapping("/addControlGroup")
	@ResponseBody
	public Response addControlGroup(ControlGroupEntity controlGroupEntity, Model model) {
		return findCompanionOracleService.addControlGroup(controlGroupEntity);
	}

	/**
	 * 修改对照组信息
	 * 
	 * @param controlGroupEntity
	 * @param model
	 * @return
	 */
	@RequestMapping("/editControlGroup")
	@ResponseBody
	public Response editControlGroup(ControlGroupEntity controlGroupEntity, Model model) {
		return findCompanionOracleService.editControlGroup(controlGroupEntity);
	}

	/**
	 * 修改对照组有效状态
	 * 
	 * @param controlGroupEntity
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateValidStatus")
	@ResponseBody
	public Response updateValidStatus(ControlGroupEntity controlGroupEntity, Model model) {
		return findCompanionOracleService.updateValidStatus(controlGroupEntity);
	}

	/**
	 * 删除对照组
	 * 
	 * @param controlGroupEntity
	 * @param model
	 * @return
	 */
	@RequestMapping("/delControlGroup")
	@ResponseBody
	public Response delValidStatus(ControlGroupEntity controlGroupEntity, Model model) {
		return findCompanionOracleService.delControlGroup(controlGroupEntity);
	}
}
