package com.elwyn.bigData.modules.population.config.entity;

import java.util.List;
import java.util.Map;

import com.rainsoft.core.entity.BaseEntity;

/**
 * @Name com.rainsoft.modules.populationMovement.entity
 * @Description
 * @Author Elwyn
 * @Version 2017/2/10 10:26
 * @Copyright 上海云辰信息科技有限公司
 */
public class HeatmapConfig extends BaseEntity{

	private static final long serialVersionUID = 9142637959408630078L;
	private String name;// 热力分布名称
	private Integer warnNum;// 热力图预警数
	private Integer alermNum;// 区域内报警人数
	private String startTime;
	private String endTime;
	private String status;// 是否启用
	private List<String> machineIds;
	private String createBy;
	private Integer deviceNum;
	private Map mapData;
	private String mapType;
	private String mapInfo;
	private String isEdit;
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

	public Map getMapData() {
		return mapData;
	}

	public void setMapData(Map mapData) {
		this.mapData = mapData;
	}

	public String getMapType() {
		return mapType;
	}

	public void setMapType(String mapType) {
		this.mapType = mapType;
	}

	public String getMapInfo() {
		return mapInfo;
	}

	public void setMapInfo(String mapInfo) {
		this.mapInfo = mapInfo;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public Integer getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(Integer deviceNum) {
		this.deviceNum = deviceNum;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public List<String> getMachineIds() {
		return machineIds;
	}

	public void setMachineIds(List<String> machineIds) {
		this.machineIds = machineIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWarnNum() {
		return warnNum;
	}

	public void setWarnNum(Integer warnNum) {
		this.warnNum = warnNum;
	}

	public Integer getAlermNum() {
		return alermNum;
	}

	public void setAlermNum(Integer alermNum) {
		this.alermNum = alermNum;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
