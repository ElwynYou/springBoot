package com.elwyn.modules.population.heatmap.entity;

import java.io.Serializable;

public class HeatingDistribution implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;//特定人群名称
	
	private int deviceNum; //设备数

	private String creatTime;
	
    private int status;
	private String doubtfulPeriod;
	private String doubtfulDays;
	private String doubtfulTimes;

	public String getDoubtfulPeriod() {
		return doubtfulPeriod;
	}

	public void setDoubtfulPeriod(String doubtfulPeriod) {
		this.doubtfulPeriod = doubtfulPeriod;
	}

	public String getDoubtfulDays() {
		return doubtfulDays;
	}

	public void setDoubtfulDays(String doubtfulDays) {
		this.doubtfulDays = doubtfulDays;
	}

	public String getDoubtfulTimes() {
		return doubtfulTimes;
	}

	public void setDoubtfulTimes(String doubtfulTimes) {
		this.doubtfulTimes = doubtfulTimes;
	}
	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public int getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(int deviceNum) {
		this.deviceNum = deviceNum;
	}

	
	

}
