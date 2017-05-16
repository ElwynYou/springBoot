package com.elwyn.modules.population.crowd.entity;

import java.io.Serializable;

/**
 * @Name com.rainsoft.modules.population.crowd.entity.EmphasisRefresh
 * @Description
 * @Author Elwyn
 * @Version 2017/4/21
 * @Copyright 上海云辰信息科技有限公司
 **/
public class EmphasisRefresh implements Serializable {

	private static final long serialVersionUID = 6501463011554831168L;
	//首页用
	private String statTime;
	private String peopleNum;
	private String serviceCode;
	private String serviceName;
	private String dangerAreaCount;
	private String dangerPersonCount;
	private String suspiciousNum;
	private String areaCode;
	private String dangerAreaName;
	private String attentionType;
	private String dangerPersonGroupName;
	private String isDangerArea;
	private String isDangerPerson;
	private String capTimes;
	private String suspiciousImsiCode;
	private String suspiciousStatus;
	private String beginTime;
	private String endTime;
	private String showDays;

	public String getShowDays() {
		return showDays;
	}

	public void setShowDays(String showDays) {
		this.showDays = showDays;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSuspiciousStatus() {
		return suspiciousStatus;
	}

	public void setSuspiciousStatus(String suspiciousStatus) {
		this.suspiciousStatus = suspiciousStatus;
	}


	public String getIsDangerArea() {
		return isDangerArea;
	}

	public void setIsDangerArea(String isDangerArea) {
		this.isDangerArea = isDangerArea;
	}

	public String getIsDangerPerson() {
		return isDangerPerson;
	}

	public void setIsDangerPerson(String isDangerPerson) {
		this.isDangerPerson = isDangerPerson;
	}

	public String getCapTimes() {
		return capTimes;
	}

	public void setCapTimes(String capTimes) {
		this.capTimes = capTimes;
	}

	public String getSuspiciousImsiCode() {
		return suspiciousImsiCode;
	}

	public void setSuspiciousImsiCode(String suspiciousImsiCode) {
		this.suspiciousImsiCode = suspiciousImsiCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getDangerAreaName() {
		return dangerAreaName;
	}

	public void setDangerAreaName(String dangerAreaName) {
		this.dangerAreaName = dangerAreaName;
	}

	public String getAttentionType() {
		return attentionType;
	}

	public void setAttentionType(String attentionType) {
		this.attentionType = attentionType;
	}

	public String getDangerPersonGroupName() {
		return dangerPersonGroupName;
	}

	public void setDangerPersonGroupName(String dangerPersonGroupName) {
		this.dangerPersonGroupName = dangerPersonGroupName;
	}

	public String getStatTime() {
		return statTime;
	}

	public void setStatTime(String statTime) {
		this.statTime = statTime;
	}

	public String getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(String peopleNum) {
		this.peopleNum = peopleNum;
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

	public String getDangerAreaCount() {
		return dangerAreaCount;
	}

	public void setDangerAreaCount(String dangerAreaCount) {
		this.dangerAreaCount = dangerAreaCount;
	}

	public String getDangerPersonCount() {
		return dangerPersonCount;
	}

	public void setDangerPersonCount(String dangerPersonCount) {
		this.dangerPersonCount = dangerPersonCount;
	}

	public String getSuspiciousNum() {
		return suspiciousNum;
	}

	public void setSuspiciousNum(String suspiciousNum) {
		this.suspiciousNum = suspiciousNum;
	}
}
