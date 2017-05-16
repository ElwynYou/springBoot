package com.elwyn.bigData.modules.population.heatmap.entity;

import java.io.Serializable;

import com.rainsoft.core.entity.BaseEntity;
/**
 * 
 * @Name com.rainsoft.modules.population.heatmap.entity.HeatMapSuspicious
 * @Description 
 * @Author xa
 * @Version 2017年4月18日 下午4:30:36
 * @Copyright 上海云辰信息科技有限公司
 */
public class HeatMapSuspicious extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -7394893953793009688L;
    private String queryTime;//查询时间
    private String beginTime;//开始时间
    private String endTime;//结束时间
	private String configId;//区域代码
	private String configName; //区域或者小区名称
	private String firstCaptureTime;//首次出现时间
	private String lastCaptureTime;//最后出现时间
	private String imsiCode;//高危地区人员数
	private String statDate;//日期时间点
	private String status;//状态
	private String confirmTime;//状态更新时间
	private String captureTimes;//出现次数
	private String highRisk; //是否是关注人群
	
	
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
	public String getHighRisk() {
		return highRisk;
	}
	public void setHighRisk(String highRisk) {
		this.highRisk = highRisk;
	}
	public String getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
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
	public String getFirstCaptureTime() {
		return firstCaptureTime;
	}
	public void setFirstCaptureTime(String firstCaptureTime) {
		this.firstCaptureTime = firstCaptureTime;
	}
	public String getLastCaptureTime() {
		return lastCaptureTime;
	}
	public void setLastCaptureTime(String lastCaptureTime) {
		this.lastCaptureTime = lastCaptureTime;
	}
	public String getImsiCode() {
		return imsiCode;
	}
	public void setImsiCode(String imsiCode) {
		this.imsiCode = imsiCode;
	}
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
	public String getCaptureTimes() {
		return captureTimes;
	}
	public void setCaptureTimes(String captureTimes) {
		this.captureTimes = captureTimes;
	}
}
