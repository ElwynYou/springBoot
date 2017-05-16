package com.elwyn.bigData.modules.population.heatmap.entity;

import java.io.Serializable;

public class HeatMapChartVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String configId;
	
	private String peopleNum;
	
	private String equipmentMac;
	
	private String snCode;
	
	private String startTime;
	
	private String endTime;
	
	private String insertTime;
	
	

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(String peopleNum) {
		this.peopleNum = peopleNum;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEquipmentMac() {
		return equipmentMac;
	}

	public void setEquipmentMac(String equipmentMac) {
		this.equipmentMac = equipmentMac;
	}

	public String getSnCode() {
		return snCode;
	}

	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
}
