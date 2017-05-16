package com.elwyn.bigData.modules.population.query.entity;

import com.rainsoft.core.paging.Page;

import java.io.Serializable;

/**
 * @Name AnalysisMachine
 * @Description
 * @Author Elwyn
 * @Version 2017/3/30
 * @Copyright 上海云辰信息科技有限公司
 **/
public class AnalysisMachine implements Serializable {

	private static final long serialVersionUID = 6789275038989033472L;
	private String machineId;
	private String machineName;
	private String address;
	private String highriskCount;
	private String timesCount;
	private String preventCount;
	private String areaCode;
	private String beginTime;
	private String endTime;
	private String dangerPerson;
	private String dangerArea;
	private String latitude;
	private String longitude;
	private String index;
	private Page page;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
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

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDangerPerson() {
		return dangerPerson;
	}

	public void setDangerPerson(String dangerPerson) {
		this.dangerPerson = dangerPerson;
	}

	public String getDangerArea() {
		return dangerArea;
	}

	public void setDangerArea(String dangerArea) {
		this.dangerArea = dangerArea;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHighriskCount() {
		return highriskCount;
	}

	public void setHighriskCount(String highriskCount) {
		this.highriskCount = highriskCount;
	}

	public String getTimesCount() {
		return timesCount;
	}

	public void setTimesCount(String timesCount) {
		this.timesCount = timesCount;
	}

	public String getPreventCount() {
		return preventCount;
	}

	public void setPreventCount(String preventCount) {
		this.preventCount = preventCount;
	}
}
