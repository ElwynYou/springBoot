/**
 * 
 */
package com.elwyn.modules.population.crowd.entity;

import java.io.Serializable;

/**
 * @Name com.rainsoft.modules.population.config.entity.EquipmentAnalyze
 * @Description
 * @Author xa
 * @Version 2017年4月5日 下午5:06:32
 * @Copyright 上海云辰信息科技有限公司
 */
public class MachineAnalyze implements Serializable{
	private static final long serialVersionUID = 1L;
	private String machineID;// 设备id
	private String machineName;// 设备名称
	private String totalCount;// 总人数
	private String dangerPersonCount;// 高危人群数
	private String dangerAreaCount;// 重点防范人群数

	public String getMachineID() {
		return machineID;
	}

	public void setMachineID(String machineID) {
		this.machineID = machineID;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getDangerPersonCount() {
		return dangerPersonCount;
	}

	public void setDangerPersonCount(String dangerPersonCount) {
		this.dangerPersonCount = dangerPersonCount;
	}

	public String getDangerAreaCount() {
		return dangerAreaCount;
	}

	public void setDangerAreaCount(String dangerAreaCount) {
		this.dangerAreaCount = dangerAreaCount;
	}

}
