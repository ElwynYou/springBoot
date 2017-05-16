package com.elwyn.bigData.modules.population.heatmap.entity;

import java.io.Serializable;

import com.rainsoft.core.entity.BaseEntity;
/**
 * 
 * @Name com.rainsoft.modules.population.heatmap.entity.HeatMapDetail
 * @Description 
 * @Author xa
 * @Version 2017年4月19日 上午11:07:32
 * @Copyright 上海云辰信息科技有限公司
 */
public class HeatMapDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -7394893953793009688L;
  
    private String configId; //配置ID
    private String configName; //配置名称
    private String statDate; // 查询时间节点
    private String imsiCode;// imsi代码
    private String operatorType;//运营商类型
    private String areaCode;//地区代码
    private String areaName;//地区名称
    private String attentionType;//关注类型
    private String attentionName;//关注名称
    private String firstCaptureTime;//首次出现时间
    private String lastCaptureTime;//最后出现时间
    private String captureNum;//出现次数
    //请求参数---------------------
    private String city;//地区
    private String queryTime;//查询时间
    private String limitingTime;//查询限制时间
    private String highRisk;//是否关注人群
    private String prevent;//是否重点防范地区人群
	
    public String getLimitingTime() {
		return limitingTime;
	}
	public void setLimitingTime(String limitingTime) {
		this.limitingTime = limitingTime;
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
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
	public String getImsiCode() {
		return imsiCode;
	}
	public void setImsiCode(String imsiCode) {
		this.imsiCode = imsiCode;
	}
	public String getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAttentionType() {
		return attentionType;
	}
	public void setAttentionType(String attentionType) {
		this.attentionType = attentionType;
	}
	public String getAttentionName() {
		return attentionName;
	}
	public void setAttentionName(String attentionName) {
		this.attentionName = attentionName;
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
	public String getCaptureNum() {
		return captureNum;
	}
	public void setCaptureNum(String captureNum) {
		this.captureNum = captureNum;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
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
}
