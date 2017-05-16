package com.elwyn.modules.population.config.entity;


import java.util.List;
import java.util.Map;

/**
 * @Name com.rainsoft.modules.population.config.entity.DangerPersonResultEntity
 * @Description
 * @Author Administrator
 * @Version 2017/5/11
 * @Copyright 上海云辰信息科技有限公司
 */
public class DangerPersonResultEntity {

    private List<DangerPerson> dangerPersons;

    private List<Map<String,String>> errorInfo;

    private Integer totalRow;

    public List<DangerPerson> getDangerPersons() {
        return this.dangerPersons;
    }

    public void setDangerPersons(List<DangerPerson> dangerPersons) {
        this.dangerPersons = dangerPersons;
    }

    public List<Map<String, String>> getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(List<Map<String, String>> errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Integer getTotalRow() {
        return this.totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }
}
