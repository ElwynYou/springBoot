package com.elwyn.modules.bigdata.entity;

import java.io.Serializable;

/**
 * @Name com.rainsoft.modules.bigdata.entity
 * @Description
 * @Author Elwyn
 * @Version 2016/12/27 18:57
 * @Copyright 上海云辰信息科技有限公司
 */
public class HClusterResult implements Serializable{
    private static final long serialVersionUID = -135653346217151672L;
    private String endingMac;
    private String totalWeights;
    private String countTimes;
    private String machineId;
    private String machineAddress;
    private String machineName;
    private String serviceCount;
    private String latitude;// 设备纬度
	private String longitude; // 设备经度
	
	
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
	
	

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(String serviceCount) {
		this.serviceCount = serviceCount;
	}

	public String getEndingMac() {
        return endingMac;
    }

    public void setEndingMac(String endingMac) {
        this.endingMac = endingMac;
    }
    
    /**
	 * @return the totalWeights
	 */
	public String getTotalWeights() {
		return totalWeights;
	}

	/**
	 * @param totalWeights the totalWeights to set
	 */
	public void setTotalWeights(String totalWeights) {
		this.totalWeights = totalWeights;
	}

    public String getCountTimes() {
        return countTimes;
    }

    public void setCountTimes(String countTimes) {
        this.countTimes = countTimes;
    }

	/**
	 * @return the machineId
	 */
	public String getMachineId() {
		return machineId;
	}

	/**
	 * @param machineId the machineId to set
	 */
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	/**
	 * @return the machineAddress
	 */
	public String getMachineAddress() {
		return machineAddress;
	}

	/**
	 * @param machineAddress the machineAddress to set
	 */
	public void setMachineAddress(String machineAddress) {
		this.machineAddress = machineAddress;
	}
}
