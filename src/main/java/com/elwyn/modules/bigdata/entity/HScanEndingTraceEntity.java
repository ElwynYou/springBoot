package com.elwyn.modules.bigdata.entity;

import java.util.Objects;

/**
 * @Name com.rainsoft.modules.bigdata.entity
 * @Description
 * @Author Elwyn
 * @Version 2016/12/22 9:41
 * @Copyright 上海云辰信息科技有限公司
 */
@SuppressWarnings("serial")
public class HScanEndingTraceEntity implements java.io.Serializable{
    private String id;  //
    private String apMac;
    private String bcpName;  //
    private String captureTime;  // 采集时间
    private String certificateCode;  //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HScanEndingTraceEntity that = (HScanEndingTraceEntity) o;
        return Objects.equals(certificateCode, that.certificateCode) &&
                Objects.equals(endingMac, that.endingMac) &&
                Objects.equals(equipmentId, that.equipmentId) &&
                Objects.equals(serviceCode, that.serviceCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificateCode, endingMac, equipmentId, serviceCode);
    }

    private String certificateType;  //
    private String dist;  // 估算距离
    private String endingMac;  // 终端mac
    private String equipmentId;  // 采集设备编号
    private String fieldStrength;  //
    private String firstTime;  //  首次发现时间
    private String importTime;  //
    private String lastTime;  // 最后发现时间
    private String manufacturerCode;  //
    private String rownumber;  //
    private String serviceCode;  // 场所编码
    private String zipName;  //
    private String machineAddress;
    private String machineName;
    private String latitude;// 设备纬度
    private String longitude; // 设备经度
    private String countTimes;//采集次数
    private String frequency;//频次
    private String hours;//时间段
    private String totalWeights;
    private String mac1;
    
    private String mac2;

    public String getTotalWeights() {
        return totalWeights;
    }

    public void setTotalWeights(String totalWeights) {
        this.totalWeights = totalWeights;
    }

    public String getMac1() {
		return mac1;
	}

	public void setMac1(String mac1) {
		this.mac1 = mac1;
	}

	public String getMac2() {
		return mac2;
	}

	public void setMac2(String mac2) {
		this.mac2 = mac2;
	}

    private String firstTimeAddr;
    private String lastTimeAddr;
    private String captureTimeAddr;

    public String getFirstTimeAddr() {
        return firstTimeAddr;
    }

    public void setFirstTimeAddr(String firstTimeAddr) {
        this.firstTimeAddr = firstTimeAddr;
    }

    public String getLastTimeAddr() {
        return lastTimeAddr;
    }

    public void setLastTimeAddr(String lastTimeAddr) {
        this.lastTimeAddr = lastTimeAddr;
    }

    public String getCaptureTimeAddr() {
        return captureTimeAddr;
    }

    public void setCaptureTimeAddr(String captureTimeAddr) {
        this.captureTimeAddr = captureTimeAddr;
    }

	public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCountTimes() {
        return countTimes;
    }

    public void setCountTimes(String countTimes) {
        this.countTimes = countTimes;
    }

    public HScanEndingTraceEntity() {
    }

    public HScanEndingTraceEntity(String endingMac, String equipmentId, String firstTime, String lastTime, String machineAddress, String machineName, String latitude, String longitude) {
        this.endingMac = endingMac;
        this.equipmentId = equipmentId;
        this.firstTime = firstTime;
        this.lastTime = lastTime;
        this.machineAddress = machineAddress;
        this.machineName = machineName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

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

    public String getMachineAddress() {
        return machineAddress;
    }

    public void setMachineAddress(String machineAddress) {
        this.machineAddress = machineAddress;
    }

    public String getApMac() {
        return apMac;
    }

    public void setApMac(String apMac) {
        this.apMac = apMac;
    }

    public String getBcpName() {
        return bcpName;
    }

    public void setBcpName(String bcpName) {
        this.bcpName = bcpName;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getEndingMac() {
        return endingMac;
    }

    public void setEndingMac(String endingMac) {
        this.endingMac = endingMac;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getFieldStrength() {
        return fieldStrength;
    }

    public void setFieldStrength(String fieldStrength) {
        this.fieldStrength = fieldStrength;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public String getImportTime() {
        return importTime;
    }

    public void setImportTime(String importTime) {
        this.importTime = importTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getRownumber() {
        return rownumber;
    }

    public void setRownumber(String rownumber) {
        this.rownumber = rownumber;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getZipName() {
        return zipName;
    }

    public void setZipName(String zipName) {
        this.zipName = zipName;
    }

}
