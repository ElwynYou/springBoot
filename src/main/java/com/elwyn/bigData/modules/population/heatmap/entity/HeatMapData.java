package com.elwyn.bigData.modules.population.heatmap.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Name com.rainsoft.modules.population.heatmap.entity.HeatMapData
 * @Description
 * @Author Elwyn
 * @Version 2017/4/12
 * @Copyright 上海云辰信息科技有限公司
 **/
public class HeatMapData implements Serializable {

	private static final long serialVersionUID = -1238650582083472348L;
	private String configId;// 热力图配置Id
	private String userId;// 当前用户
	private String autoRefresh;// 自动刷新频率
	private String statTime;// 热力图数据时间条件
	private List<TheHeat> theHeat;// 热力图显示数据

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getStatTime() {
		return statTime;
	}

	public void setStatTime(String statTime) {
		this.statTime = statTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAutoRefresh() {
		return autoRefresh;
	}

	public void setAutoRefresh(String autoRefresh) {
		this.autoRefresh = autoRefresh;
	}

	public List<TheHeat> getTheHeat() {
		return theHeat;
	}

	public void setTheHeat(List<TheHeat> theHeat) {
		this.theHeat = theHeat;
	}
}
