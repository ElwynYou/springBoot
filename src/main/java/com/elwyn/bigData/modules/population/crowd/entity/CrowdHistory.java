package com.elwyn.bigData.modules.population.crowd.entity;

import java.io.Serializable;

import com.rainsoft.core.entity.BaseEntity;
/**
 * 
 * @Name com.rainsoft.modules.population.crowd.entity.CrowdHistory
 * @Description 
 * @Author xa
 * @Version 2017年4月12日 下午1:38:19
 * @Copyright 上海云辰信息科技有限公司
 */
public class CrowdHistory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -7394893953793009688L;
    
    private String dayTime;//日统计时间
    private String monthTime;//月统计时间
    private String yearTime;//年统计时间
	private String queryTime;//查询时间
	private String beginTime;// 开始时间
	private String overTime;// 结束时间
	private String serviceCode;//区域或者小区代码
	private String serviceName; //区域或者小区名称
	private String equipmentNum = "0";//设备数量
	private String totalCount = "0";//实有人数
	private String dangerPersonCount = "0";//重点关注人员数
	private String dangerAreaCount = "0";//重点地区人员数
	private String unmeantNum = "0";//闪现人员数
	private String captureTime;//日期时间点
	private String timeFomat;//日期时间点
    
	public String getTimeFomat() {
		return timeFomat;
	}
	public void setTimeFomat(String timeFomat) {
		this.timeFomat = timeFomat;
	}
	public String getDayTime() {
		return dayTime;
	}
	public void setDayTime(String dayTime) {
		this.dayTime = dayTime;
	}
	public String getMonthTime() {
		return monthTime;
	}
	public void setMonthTime(String monthTime) {
		this.monthTime = monthTime;
	}
	public String getYearTime() {
		return yearTime;
	}
	public void setYearTime(String yearTime) {
		this.yearTime = yearTime;
	}
	public String getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
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
	public String getCaptureTime() {
		return captureTime;
	}
	public void setCaptureTime(String captureTime) {
		this.captureTime = captureTime;
	}
	
}
