package com.elwyn.modules.bigdata.entity;

import java.io.Serializable;
import java.util.List;

/**
*
* @Name com.rainsoft.modules.bigdata.entity
* @Description 日常检查
* @Author LiSicai
* @Version 1.0
* @time 创建时间 2016年12月21日下午9:50:30
* @Copyright 上海云辰信息科技有限公司
*
*/
public class IdentityContrastEntity implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private List<String> macList;
	
	private String starTime;
	
	private String endTime;
	
	private String collectingDevice;//采集设备
	
	private String  deviceAddress;
	
	private long   number;//出现次数
	
	private String longitudes;//设备经度
	
	private String  latitude;//设备纬度
	
	private String serviceCode;//设备场所code 
	
	private String equipmentId;//设备id
	
	

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public List<String> getMacList() {
		return macList;
	}

	public void setMacList(List<String> macList) {
		this.macList = macList;
	}

	public String getStarTime() {
		return starTime;
	}

	public void setStarTime(String starTime) {
		this.starTime = starTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCollectingDevice() {
		return collectingDevice;
	}

	public void setCollectingDevice(String collectingDevice) {
		this.collectingDevice = collectingDevice;
	}

	public String getDeviceAddress() {
		return deviceAddress;
	}

	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}

	

	public String getLongitudes() {
		return longitudes;
	}

	public void setLongitudes(String longitudes) {
		this.longitudes = longitudes;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	

}
