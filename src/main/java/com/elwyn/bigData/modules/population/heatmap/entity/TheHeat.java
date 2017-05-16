package com.elwyn.bigData.modules.population.heatmap.entity;

import java.io.Serializable;

public class TheHeat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8960648937704046912L;

	private String statTime;
	
	private String longitude;
	
	private String latitude;
	
	private Integer peopleNum;
	private String beginTime;
	private String endTime;
	
	private String snCode;
	private String attentionType;
	private String areaCode;
	private String isDangerArea;
	private String isDangerPerson;
	private String capTimes;
	private String showDays;
	//首页用
	private String configId;
	private String configName;
	private String warnNum;
	private String totalPerson;
	private String suspiciousNum;
	//定时刷新用
	private String dangerAreaName;
	private String dangerAreaCount;
	private String dangerPersonCount;
	private String dangerPersonGroupName;
	private String suspiciousImsiCode;
	//定时刷新用


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

	public String getSuspiciousNum() {
		return suspiciousNum;
	}

	public void setSuspiciousNum(String suspiciousNum) {
		this.suspiciousNum = suspiciousNum;
	}

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getWarnNum() {
		return warnNum;
	}

	public void setWarnNum(String warnNum) {
		this.warnNum = warnNum;
	}

	public String getTotalPerson() {
		return totalPerson;
	}

	public void setTotalPerson(String totalPerson) {
		this.totalPerson = totalPerson;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAttentionType() {
		return attentionType;
	}

	public void setAttentionType(String attentionType) {
		this.attentionType = attentionType;
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

	public String getDangerPersonCount() {
		return dangerPersonCount;
	}

	public void setDangerPersonCount(String dangerPersonCount) {
		this.dangerPersonCount = dangerPersonCount;
	}

	public String getDangerPersonGroupName() {
		return dangerPersonGroupName;
	}

	public void setDangerPersonGroupName(String dangerPersonGroupName) {
		this.dangerPersonGroupName = dangerPersonGroupName;
	}

	public String getDangerAreaName() {
		return dangerAreaName;
	}

	public void setDangerAreaName(String dangerAreaName) {
		this.dangerAreaName = dangerAreaName;
	}

	public String getDangerAreaCount() {
		return dangerAreaCount;
	}

	public void setDangerAreaCount(String dangerAreaCount) {
		this.dangerAreaCount = dangerAreaCount;
	}

	public String getSnCode() {
		return snCode;
	}

	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}

	public String getStatTime() {
		return statTime;
	}

	public void setStatTime(String statTime) {
		this.statTime = statTime;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Integer getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
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
}
