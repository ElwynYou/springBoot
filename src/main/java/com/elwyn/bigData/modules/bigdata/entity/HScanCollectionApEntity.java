package com.elwyn.bigData.modules.bigdata.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Name com.rainsoft.modules.bigdata.entity
 * @Description
 * @Author Elwyn
 * @Version 2016/12/20 14:26
 * @Copyright 上海云辰信息科技有限公司
 */
public class HScanCollectionApEntity implements Serializable {

    private static final long serialVersionUID = 8102397457856563448L;

    String id;
    String apSsid;
    String fieldStrength;
    String apChannel;
    String apMac;
    String captureTime;
    String dist;
    String encryptType;
    String equipmentId;
    String equipmentLat;
    String equipmentLon;
    String firstTime;
    String importTime;
    String lastTime;
    String manuf;
    String serviceCode;
    String xCoordinate;
    String yCoordinate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApSsid() {
        return apSsid;
    }

    public void setApSsid(String apSsid) {
        this.apSsid = apSsid;
    }

    public String getFieldStrength() {
        return fieldStrength;
    }

    public void setFieldStrength(String fieldStrength) {
        this.fieldStrength = fieldStrength;
    }

    public String getApChannel() {
        return apChannel;
    }

    public void setApChannel(String apChannel) {
        this.apChannel = apChannel;
    }

    public String getApMac() {
        return apMac;
    }

    public void setApMac(String apMac) {
        this.apMac = apMac;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentLat() {
        return equipmentLat;
    }

    public void setEquipmentLat(String equipmentLat) {
        this.equipmentLat = equipmentLat;
    }

    public String getEquipmentLon() {
        return equipmentLon;
    }

    public void setEquipmentLon(String equipmentLon) {
        this.equipmentLon = equipmentLon;
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

    public String getManuf() {
        return manuf;
    }

    public void setManuf(String manuf) {
        this.manuf = manuf;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(String xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public String getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(String yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HScanCollectionApEntity that = (HScanCollectionApEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
