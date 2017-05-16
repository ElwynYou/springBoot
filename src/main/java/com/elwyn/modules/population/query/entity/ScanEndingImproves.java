package com.elwyn.modules.population.query.entity;

import java.io.Serializable;
import java.util.Date;

public class ScanEndingImproves implements Serializable {
    private static final long serialVersionUID = -7394893953793009688L;
    private String id;
    private String equipmentMac;
    private String equipmentId;
    private String imsiCode;
    private String captureTime;
    private String operatorType;
    private String snCode;
    private String longitude;
    private String latitude;
    private Date firstTime;
    private Date lastTime;
    private String dist;
    private Date importTime;
    private String address;
    private String endingMac;
    private String areaCode;
    private String operators;
    private String highrisk;
    private String phoneArea;
    private String count;
    private String prevent;
    
    private String peopleType;
    private String configId;

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPeopleType() {
		return peopleType;
	}

	public void setPeopleType(String peopleType) {
		this.peopleType = peopleType;
	}

    public String getPrevent() {
        return prevent;
    }

    public void setPrevent(String prevent) {
        this.prevent = prevent;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEndingMac() {
        return endingMac;
    }

    public void setEndingMac(String endingMac) {
        this.endingMac = endingMac;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquipmentMac() {
        return equipmentMac;
    }

    public void setEquipmentMac(String equipmentMac) {
        this.equipmentMac = equipmentMac;
    }

    public String getImsiCode() {
        return imsiCode;
    }

    public void setImsiCode(String imsiCode) {
        this.imsiCode = imsiCode;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime.substring(0,19);
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

    public String getHighrisk() {
        return highrisk;
    }

    public void setHighrisk(String highrisk) {
        this.highrisk = highrisk;
    }

    public String getPhoneArea() {
        return phoneArea;
    }

    public void setPhoneArea(String phoneArea) {
        this.phoneArea = phoneArea;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

  
}
