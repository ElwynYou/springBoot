package com.elwyn.modules.population.heatmap.entity;

import com.rainsoft.core.entity.BaseEntity;

import java.io.Serializable;

/**
 * 
 * @Name com.rainsoft.modules.population.heatmap.entity.HeatMapHistory
 * @Description 
 * @Author xa
 * @Version 2017年4月18日 下午1:21:09
 * @Copyright 上海云辰信息科技有限公司
 */
public class HeatMapHistory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -7394893953793009688L;
   
    public final static String QUERY_TIME_TYPE_DAY = "day";
    public final static String QUERY_TIME_TYPE_MONTH = "month";
    public final static String QUERY_TIME_TYPE_YEAR = "year";

    private String queryTime;//查询时间
	private String queryTimeType;//查询时间类型
	private String configId;//区域代码
	private String configName; //区域或者小区名称
	private int totalPeople = 0;//实有人数
	private int attentionPeople = 0;//关注人员数
	private int dangerPeople = 0;//高危地区人员数
	private String statDate;//日期时间点
	private String simpStatDate;//简单日期时间点
	private String timeFomat;//日期格式
	
	public String getSimpStatDate() {
		return simpStatDate;
	}
	public void setSimpStatDate(String simpStatDate) {
		this.simpStatDate = simpStatDate;
	}
	public String getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}
	public String getQueryTimeType() {
		return queryTimeType;
	}
	public void setQueryTimeType(String queryTimeType) {
		this.queryTimeType = queryTimeType;
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
	public int getTotalPeople() {
		return totalPeople;
	}
	public void setTotalPeople(int totalPeople) {
		this.totalPeople = totalPeople;
	}
	public int getAttentionPeople() {
		return attentionPeople;
	}
	public void setAttentionPeople(int attentionPeople) {
		this.attentionPeople = attentionPeople;
	}
	public int getDangerPeople() {
		return dangerPeople;
	}
	public void setDangerPeople(int dangerPeople) {
		this.dangerPeople = dangerPeople;
	}
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
	public String getTimeFomat() {
		return timeFomat;
	}
	public void setTimeFomat(String timeFomat) {
		this.timeFomat = timeFomat;
	}
}
