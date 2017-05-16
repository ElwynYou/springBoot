/**
 * 
 */
package com.elwyn.modules.population.crowd.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @Name com.rainsoft.modules.population.crowd.entity.CrowdAnalyze
 * @Description 区域分析
 * @Author xa
 * @Version 2017年3月31日 下午1:56:13
 * @Copyright 上海云辰信息科技有限公司
 */
public class CrowdAnalyze implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String serviceCode;//区域或者小区代码
	private String serviceName; //区域或者小区名称
	private String equipmentNum;//设备数量
	private String totalCount;//总人数
	private String dangerPersonCount;//高危人群数
	private String dangerAreaCount;//重点防范人群数
	private String unmeantNum;//窜访人群数
	private String tempPeopleNum;//暂住人群数
	private String abnormalCount;//异常常住人群
	private List<CrowdAnalyzeTemp> dangerMap;//重点防范地区
	private List<CrowdAnalyzeTemp> personTypeMap;//人群类型
	private List<MachineAnalyze> machineAnalyzes;//设备明细
	
	
	public String getTempPeopleNum() {
		return tempPeopleNum;
	}
	public void setTempPeopleNum(String tempPeopleNum) {
		this.tempPeopleNum = tempPeopleNum;
	}
	public String getAbnormalCount() {
		return abnormalCount;
	}
	public void setAbnormalCount(String abnormalCount) {
		this.abnormalCount = abnormalCount;
	}
	public String getUnmeantNum() {
		return unmeantNum;
	}
	public void setUnmeantNum(String unmeantNum) {
		this.unmeantNum = unmeantNum;
	}
	public List<CrowdAnalyzeTemp> getPersonTypeMap() {
		return personTypeMap;
	}
	public void setPersonTypeMap(List<CrowdAnalyzeTemp> personTypeMap) {
		this.personTypeMap = personTypeMap;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getEquipmentNum() {
		return equipmentNum;
	}
	public void setEquipmentNum(String equipmentNum) {
		this.equipmentNum = equipmentNum;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getDangerPersonCount() {
		return dangerPersonCount;
	}
	public void setDangerPersonCount(String dangerPersonCount) {
		this.dangerPersonCount = dangerPersonCount;
	}
	public String getDangerAreaCount() {
		return dangerAreaCount;
	}
	public void setDangerAreaCount(String dangerAreaCount) {
		this.dangerAreaCount = dangerAreaCount;
	}
	public List<CrowdAnalyzeTemp> getDangerMap() {
		return dangerMap;
	}
	public void setDangerMap(List<CrowdAnalyzeTemp> dangerMap) {
		this.dangerMap = dangerMap;
	}
	public List<MachineAnalyze> getMachineAnalyzes() {
		return machineAnalyzes;
	}
	public void setMachineAnalyzes(List<MachineAnalyze> machineAnalyzes) {
		this.machineAnalyzes = machineAnalyzes;
	}
}
