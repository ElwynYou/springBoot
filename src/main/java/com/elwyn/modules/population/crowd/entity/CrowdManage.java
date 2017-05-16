/**
 * 
 */
package com.elwyn.modules.population.crowd.entity;

import com.rainsoft.core.entity.BaseEntity;

import java.io.Serializable;

/**
 * @Name com.rainsoft.modules.population.crowd.entity.CrowdManage
 * @Description 区域总览
 * @Author xa
 * @Version 2017年3月29日 下午7:49:24
 * @Copyright 上海云辰信息科技有限公司
 */
public class CrowdManage extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String queryTime;//查询时间
	private String areaCode;// 区域地址
	private String beginTime;// 开始时间
	private String overTime;// 结束时间
	private String serviceCode;//区域或者小区代码
	private String serviceName; //区域或者小区名称
	private String equipmentNum = "0";//设备数量
	private String totalCount = "0";//总人数
	private String dangerPersonCount = "0";//重点关注人员数
	private String dangerAreaCount = "0";//重点地区人员数
	private String unmeantNum = "0";//闪现人员数
	private String highRisk = "0";// 高危人群
	private String prevent  = "0";// 重点防范
	private String serviceType;// 区域类型
	
	
	public String getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
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
	public String getUnmeantNum() {
		return unmeantNum;
	}
	public void setUnmeantNum(String unmeantNum) {
		this.unmeantNum = unmeantNum;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	public String getHighRisk() {
		return highRisk;
	}
	public void setHighRisk(String highRisk) {
		this.highRisk = highRisk;
	}
	public String getPrevent() {
		return prevent;
	}
	public void setPrevent(String prevent) {
		this.prevent = prevent;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
}
