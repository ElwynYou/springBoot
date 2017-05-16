package com.elwyn.modules.bigdata.service.oracle;

import com.rainsoft.model.AreaStation;
import com.rainsoft.model.User;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.model.datatables.DataTablesResponse;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.bigdata.dao.oracle.CompanionGroupDao;
import com.rainsoft.modules.bigdata.dao.oracle.ControlGroupOracleDao;
import com.rainsoft.modules.bigdata.dao.oracle.GetMacConfMainListDao;
import com.rainsoft.modules.bigdata.dao.oracle.GetMachineInfoDao;
import com.rainsoft.modules.bigdata.entity.CompanionGroup;
import com.rainsoft.modules.bigdata.entity.ControlGroupEntity;
import com.rainsoft.modules.bigdata.entity.TabMacFriConfMain;
import com.rainsoft.modules.bigdata.util.StringUtils;
import com.rainsoft.spring.security.UserDetailsImpl;
import com.rainsoft.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Name com.rainsoft.modules.bigdata.service.oracle
 * @Description
 * @Author Elwyn
 * @Version 2016/12/29 16:19
 * @Copyright 上海云辰信息科技有限公司
 */
@Service
public class FindCompanionOracleService {
	@Autowired
	private CompanionGroupDao companionGroupDao;

	@Autowired
	private GetMachineInfoDao getMachineInfoDao;

	@Autowired
	private GetMacConfMainListDao getMacConfMainListDao;

	@Autowired
	private ControlGroupOracleDao controlGroupOracleDao;

	/**
	 * 查找同伴
	 *
	 * @param dataTablesRequest
	 * @return
	 */
	public DataTablesResponse FindCompanionList(DataTablesRequest dataTablesRequest) {
		DataTablesResponse dataTablesResponse = new DataTablesResponse(dataTablesRequest);
		int count = companionGroupDao.getCount(dataTablesRequest);
		if (count > 0) {
			List<CompanionGroup> companionGroupList = companionGroupDao.getCompanionGroupList(dataTablesRequest);
			dataTablesResponse.setData(companionGroupList);
			dataTablesResponse.setRecordsTotal(count);
			dataTablesResponse.setRecordsFiltered(count);
		}

		return dataTablesResponse;
	}

	// 获取当前用户和城区
	private static List<String> getServiceCodeByArea(GetMachineInfoDao getMachineInfoDao) {

		User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		Iterator<AreaStation> it = user.getAreas().iterator();
		List<Long> areas = new ArrayList<>();
		while (it.hasNext()) {
			AreaStation area = it.next();
			areas.add(area.getCode());
		}
		return getMachineInfoDao.getUserServiceCode(areas);
	}

	/**
	 * Mac设置
	 *
	 * @param dataTablesRequest
	 * @return
	 */
	public DataTablesResponse getMacFriConfMainList(DataTablesRequest dataTablesRequest) {
		dataTablesRequest.setModeClass(TabMacFriConfMain.class);
		DataTablesResponse dataTablesResponse = new DataTablesResponse(dataTablesRequest);
		List<TabMacFriConfMain> list = getMacConfMainListDao.getMacConfList(dataTablesRequest);
		int cout = getMacConfMainListDao.getCount(dataTablesRequest);
		dataTablesResponse.setData(list);
		dataTablesResponse.setRecordsTotal(cout);
		dataTablesResponse.setRecordsFiltered(cout);
		return dataTablesResponse;
	}

	/**
	 * 删除MacConfMain
	 */
	public Response deleteMacConfMain(TabMacFriConfMain tabMacFriConfMain) {
		try {
			// 删除主表
			getMacConfMainListDao.deleteMacMain( tabMacFriConfMain);
			// 删除附表
			getMacConfMainListDao.deleteMacConfAdd( tabMacFriConfMain);
			return Response.getSuccess("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getSuccess("删除失败");
		}
	}

	/**
	 * @param map
	 * @return
	 */
	public List<MachineInfo> getMachineInfoByNames(Map map) {
		return getMachineInfoDao.getMachineInfoByMap(map);
	}

	/**
	 * 添加Mac
	 *
	 * @param
	 */
	public Response addMacFriConfMainList(TabMacFriConfMain tabMacFriConfMain) {
		try {
			// 添加MacMain
			getMacConfMainListDao.addMacConf(tabMacFriConfMain);

			addMacAddInfo(tabMacFriConfMain);

			return Response.getSuccess("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("添加失败");
		}

	}

	public void addMacAddInfo(TabMacFriConfMain tabMacFriConfMain) {
		String[] equipmentIdAddArr = tabMacFriConfMain.getEquipmentIdAdds();
		String[] startTimeArr = tabMacFriConfMain.getBeginTimes();
		String[] stopTimeArr = tabMacFriConfMain.getEndTimes();
		if (null != equipmentIdAddArr) {
            for (int i = 0; i < equipmentIdAddArr.length; i++) {
                if (StringUtils.isNotEmpty(equipmentIdAddArr[i])) {
                    tabMacFriConfMain.setEquipmentIdAdd(equipmentIdAddArr[i]);
                    tabMacFriConfMain.setBeginTime(startTimeArr[i]);
                    tabMacFriConfMain.setEndTime(stopTimeArr[i]);
                    getMacConfMainListDao.addAssistantMac(tabMacFriConfMain);
                }
            }
        }
	}

	/**
	 * 修改MacMainValidStatus
	 */
	public Response updateMainValidStatus(TabMacFriConfMain tabMacFriConfMain) {

		int i = getMacConfMainListDao.updateMainValidStatus(tabMacFriConfMain);
		if (i >= 0) {
			return Response.getSuccess("更新失败");
		}
		return Response.getError("更新成功");
	}
	/**
	 * 修改MacMainInfo
	 */
	public Response updateMacMainInfo(TabMacFriConfMain tabMacFriConfMain) {
		try {
			//更新Mac主表
			getMacConfMainListDao.updateMacMainInfo(tabMacFriConfMain);
			//删除附表
			getMacConfMainListDao.deleteMacConfAdd(tabMacFriConfMain);
			//重新添加
			addMacAddInfo(tabMacFriConfMain);
			return Response.getSuccess("更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("更新失败");
		}




	}

	/**
	 * 分页
	 * 
	 * @param dataTablesRequest
	 * @return
	 */
	public Collection<ControlGroupEntity> findPageControlGroup(DataTablesRequest<?> dataTablesRequest) {
		return controlGroupOracleDao.findPageControlGroup(dataTablesRequest);
	}

	/**
	 * 分页count
	 * 
	 * @param dataTablesRequest
	 * @return
	 */
	public long findPageCount(DataTablesRequest<?> dataTablesRequest) {
		return controlGroupOracleDao.findPageCount(dataTablesRequest);
	}

	/**
	 * 添加对照组
	 * 
	 * @param controlGroupEntity
	 */
	public Response addControlGroup(ControlGroupEntity controlGroupEntity) {
		try {
			controlGroupEntity.setStatsTimes(ControlGroupEntity.STATS_TIMES);
			controlGroupEntity.setStatsStatus(ControlGroupEntity.STATS_STATUS_0);
			controlGroupEntity.setValidStatus(ControlGroupEntity.VALID_STATUS_1);

			// 添加主设备
			controlGroupOracleDao.addControlGroup(controlGroupEntity);

			String[] equipmentIdAddArr = controlGroupEntity.getEquipmentIdAdds();
			String[] startTimeArr = controlGroupEntity.getBeginTimes();
			String[] stopTimeArr = controlGroupEntity.getEndTimes();

			// 添加副设备
			if (null != equipmentIdAddArr) {
				for (int i = 0; i < equipmentIdAddArr.length; i++) {
					if (StringUtils.isNotEmpty(equipmentIdAddArr[i])) {
						controlGroupEntity.setEquipmentIdAdd(equipmentIdAddArr[i]);
						controlGroupEntity.setBeginTime(startTimeArr[i]);
						controlGroupEntity.setEndTime(stopTimeArr[i]);
						controlGroupOracleDao.addAssistantEquipment(controlGroupEntity);
					}
				}
			}
			return Response.getSuccess("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("添加失败");
		}
	}

	/**
	 * 修改对照组
	 * 
	 * @param controlGroupEntity
	 */
	public Response editControlGroup(ControlGroupEntity controlGroupEntity) {
		try {
			// 删除副设备
			// delControlGroupByid(controlGroupEntity.getAudId(),controlGroupEntity.getDelId());
			// 修改对照组信息
			controlGroupOracleDao.updateControlGroup(controlGroupEntity);

			String[] equipmentIdAddArr = controlGroupEntity.getEquipmentIdAdds();
			String[] startTimeArr = controlGroupEntity.getBeginTimes();
			String[] stopTimeArr = controlGroupEntity.getEndTimes();
			// int[] dataType = controlGroupEntity.getDataType();

			// 删除对照组所有副设备
			controlGroupOracleDao.delViceControlGroupAll(controlGroupEntity);
			if (null != equipmentIdAddArr) {
				ControlGroupEntity control = findControlGroupById(controlGroupEntity.getAudId());
				// 添加副设备
				for (int i = 0; i < equipmentIdAddArr.length; i++) {
					if (StringUtils.isNotEmpty(equipmentIdAddArr[i])) {
						control.setEquipmentIdAdd(equipmentIdAddArr[i]);
						control.setValidStatus(ControlGroupEntity.VALID_STATUS_1);
						control.setBeginTime(startTimeArr[i]);
						control.setEndTime(stopTimeArr[i]);
						controlGroupOracleDao.addAssistantEquipment(control);
						/*
						 * if(dataType[i] == ControlGroupEntity.DATATYPE_0){//添加
						 * controlGroupOracleDao
						 * .insertAssistantEquipment(control); } else
						 * if(dataType[i] == ControlGroupEntity.DATATYPE_1){//修改
						 * controlGroupOracleDao
						 * .updateAssistantEquipment(control); }
						 */

					}
				}
			}

			return Response.getSuccess("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("修改失败");
		}
	}

	/**
	 * 修改对照组有效状态
	 * 
	 * @param controlGroupEntity
	 * @return
	 */
	public Response updateValidStatus(ControlGroupEntity controlGroupEntity) {
		try {
			controlGroupEntity.setValidStatus(ControlGroupEntity.VALID_STATUS_0.equals(controlGroupEntity.getValidStatus()) ? ControlGroupEntity.VALID_STATUS_0
					: ControlGroupEntity.VALID_STATUS_1);
			controlGroupOracleDao.updateValidStatus(controlGroupEntity);
			return Response.getSuccess("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("修改失败");
		}
	}

	/**
	 * 根据audId获得对照组信息
	 * 
	 * @param audId
	 * @return
	 */
	public ControlGroupEntity findControlGroupById(int audId) {
		return controlGroupOracleDao.findControlGroupById(audId);
	}

	/**
	 * 根据对照组id 和副设备id删除
	 * 
	 * @param audId
	 * @param controlIdStr
	 */
	public void delControlGroupByid(int audId, String controlIdStr) {
		if (null != controlIdStr && !("".equals(controlIdStr))) {
			String[] controlId = controlIdStr.split(",");
			controlGroupOracleDao.delControlGroupByid(audId, controlId);
		}
	}

	/**
	 * 删除主设备以及相关所有副设备
	 * 
	 * @param controlGroupEntity
	 * @return
	 */
	public Response delControlGroup(ControlGroupEntity controlGroupEntity) {
		try {
			// 删除对照组所有副设备
			controlGroupOracleDao.delViceControlGroupAll(controlGroupEntity);
			// 删除对照组
			controlGroupOracleDao.delControlGroup(controlGroupEntity);
			return Response.getSuccess("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("删除失败");
		}
	}
}
