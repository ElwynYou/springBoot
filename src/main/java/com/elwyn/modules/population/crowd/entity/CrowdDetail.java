package com.elwyn.modules.population.crowd.entity;

import com.rainsoft.core.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Name com.rainsoft.modules.population.crowd.entity.CrowdDetail
 * @Description 
 * @Author xa
 * @Version 2017年4月10日 下午2:01:23
 * @Copyright 上海云辰信息科技有限公司
 */
public class CrowdDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -7394893953793009688L;
 
    private Date firstTime;
    private Date lastTime;
    private String operators;
    private String phoneArea;
    private String count;
  
    //请求参数---------------------
    private String serviceName;
    private String machineId;
    private String imsiCode;
    private String highRisk;
    private String prevent;
    private String mathodType;
    private String peopleType;
    private String serviceCode;
    private String beginTime;
    private String overTime;
    private String city;
    private String queryTime;//查询时间
    
	public String getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Date getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public String getOperators() {
		return operators;
	}
	public void setOperators(String operators) {
		this.operators = operators;
	}
	public String getPhoneArea() {
		return phoneArea;
	}
	public void setPhoneArea(String phoneArea) {
		this.phoneArea = phoneArea;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public String getImsiCode() {
		return imsiCode;
	}
	public void setImsiCode(String imsiCode) {
		this.imsiCode = imsiCode;
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
	public String getMathodType() {
		return mathodType;
	}
	public void setMathodType(String mathodType) {
		this.mathodType = mathodType;
	}
	public String getPeopleType() {
		return peopleType;
	}
	public void setPeopleType(String peopleType) {
		this.peopleType = peopleType;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	
}
