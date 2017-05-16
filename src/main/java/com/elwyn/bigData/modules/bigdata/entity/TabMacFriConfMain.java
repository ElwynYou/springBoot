package com.elwyn.bigData.modules.bigdata.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Name com.rainsoft.modules.bigdata.entity
 * @Description
 * @Author Elwyn
 * @Version 2016/12/30 9:55
 * @Copyright 上海云辰信息科技有限公司
 */
public class TabMacFriConfMain implements Serializable {

    private static final long serialVersionUID = -5421376552705931736L;

    private String audId	    ;//对照组ID
    private String audName	    ;//对照组名称
    private String endingMac	;//手机MAC地址
    private String beginTime	;//开始时间
    private String endTime	    ;//结束时间
    private String weight	    ;//权重
    private String statsDate	;//统计日期
    private String statsTimes	;//统计次数 默认为 0
    private String statsStatus ;//统计状态 0 未统计 1 已统计 默认为 0
    private String validStatus ;//有效状态 0 无效 1 有效 默认为 1
    private String insertTime	;//插入时间
    private String equipmentIdAdd;//附加设备id
    private String equipmentName;//附加设备名称
    private String equipmentBeginTime;//附加设备开始时间
    private String equipmentEndTime;//附加设备结束时间

    public String getEquipmentBeginTime() {
        return equipmentBeginTime;
    }

    public void setEquipmentBeginTime(String equipmentBeginTime) {
        this.equipmentBeginTime = equipmentBeginTime;
    }

    public String getEquipmentEndTime() {
        return equipmentEndTime;
    }

    public void setEquipmentEndTime(String equipmentEndTime) {
        this.equipmentEndTime = equipmentEndTime;
    }

    public String getEquipmentIdAdd() {
        return equipmentIdAdd;
    }

    public void setEquipmentIdAdd(String equipmentIdAdd) {
        this.equipmentIdAdd = equipmentIdAdd;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    private String[] checkNames;//设备名称集合

    private String[] equipmentIdAdds;//附加设备id集合

    private String[] beginTimes;//设备检测开始时间集合

    private String[] endTimes;//设备检测结束时间集合

    public String[] getCheckNames() {
        return checkNames;
    }

    public void setCheckNames(String[] checkNames) {
        this.checkNames = checkNames;
    }

    public String[] getEquipmentIdAdds() {
        return equipmentIdAdds;
    }

    public void setEquipmentIdAdds(String[] equipmentIdAdds) {
        this.equipmentIdAdds = equipmentIdAdds;
    }

    public String[] getBeginTimes() {
        return beginTimes;
    }

    public void setBeginTimes(String[] beginTimes) {
        this.beginTimes = beginTimes;
    }

    public String[] getEndTimes() {
        return endTimes;
    }

    public void setEndTimes(String[] endTimes) {
        this.endTimes = endTimes;
    }



    public String getAudId() {
        return audId;
    }

    public void setAudId(String audId) {
        this.audId = audId;
    }

    public String getAudName() {
        return audName;
    }

    public void setAudName(String audName) {
        this.audName = audName;
    }

    public String getEndingMac() {
        return endingMac;
    }

    public void setEndingMac(String endingMac) {
        this.endingMac = endingMac;
    }

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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStatsDate() {
        return statsDate;
    }

    public void setStatsDate(String statsDate) {
        this.statsDate = statsDate;
    }

    public String getStatsTimes() {
        return statsTimes;
    }

    public void setStatsTimes(String statsTimes) {
        this.statsTimes = statsTimes;
    }

    public String getStatsStatus() {
        return statsStatus;
    }

    public void setStatsStatus(String statsStatus) {
        this.statsStatus = statsStatus;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TabMacFriConfMain that = (TabMacFriConfMain) o;
        return Objects.equals(audId, that.audId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(audId);
    }
}
