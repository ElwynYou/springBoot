package com.elwyn.modules.bigdata.entity;

import java.io.Serializable;

/**
 * 
*
* @Name com.rainsoft.modules.bigdata.entity.MacResult
* @Description
* 
* @Author Xian
* @Version 2017年1月4日 下午4:24:16
* @Copyright 上海云辰信息科技有限公司
*
*
 */
public class MacResult implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8247533720666515684L;
	private String hour;
    private String count;
    private String serviceCode;
    private String endingMac;

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

	public String getEndingMac() {
		return endingMac;
	}

	public void setEndingMac(String endingMac) {
		this.endingMac = endingMac;
	}
    
}
