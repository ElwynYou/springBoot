package com.elwyn.bigData.modules.bigdata.entity;

import java.io.Serializable;

/**
 * @Name com.rainsoft.modules.bigdata.entity
 * @Description
 * @Author Elwyn
 * @Version 2017/1/3 10:04
 * @Copyright 上海云辰信息科技有限公司
 */
public class CompanionGroup implements Serializable {

    private static final long serialVersionUID = 2806404505240335419L;


    private String audId; //对照id
    private String endingMac;//终端mac
    private String endingMacExtra;// 同伴终端mac
    private String equipCnt;//同时出现场所数
    private String cnt; //同时出现次数
    private String weight; //权重
    private String statsDate; //统计日期
    private String statsType; //数据统计类型 1 指定设备统计  2 指定mac统计
    private String audName;

    public String getAudName() {
        return audName;
    }

    public void setAudName(String audName) {
        this.audName = audName;
    }

    public String getAudId() {
        return audId;
    }

    public void setAudId(String audId) {
        this.audId = audId;
    }

    public String getEndingMac() {
        return endingMac;
    }

    public void setEndingMac(String endingMac) {
        this.endingMac = endingMac;
    }

    public String getEndingMacExtra() {
        return endingMacExtra;
    }

    public void setEndingMacExtra(String endingMacExtra) {
        this.endingMacExtra = endingMacExtra;
    }

    public String getEquipCnt() {
        return equipCnt;
    }

    public void setEquipCnt(String equipCnt) {
        this.equipCnt = equipCnt;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
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

    public String getStatsType() {
        return statsType;
    }

    public void setStatsType(String statsType) {
        this.statsType = statsType;
    }
}
