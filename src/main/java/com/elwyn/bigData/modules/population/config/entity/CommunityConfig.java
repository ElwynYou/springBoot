package com.elwyn.bigData.modules.population.config.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 小区设置Entity
 * @author Sugar
 * @version 2017-04-14
 */
public class CommunityConfig extends RemindEntity {

	private static final long serialVersionUID = 1L;
	
	private String serviceCode;		// 场所编码
	private String serviceName;     //小区名称
	private int longCalcDays;		// 常住人群计算天数
	private int stayCalcDays;		// 暂住人群计算天数
	private int newCalcDays;		// 闪现(新增加)人群计算天数
	private int longDisappearWarnDays;		// 常住人群未出现提醒天数
	private int longDisappearClearDays;		// 常住人群未出现自动移出天数
	private String msgTypes;		// 待发送的消息类型，多个用英文逗号隔开
	private Date updateTime;		// update_time
	private long createBy;        //创建者
	
	public CommunityConfig() {
		super();
	}

	public CommunityConfig(String id){
		super(id);
	}
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}
	public int getLongCalcDays() {
		return longCalcDays;
	}

	public void setLongCalcDays(int longCalcDays) {
		this.longCalcDays = longCalcDays;
	}
	
	public int getStayCalcDays() {
		return stayCalcDays;
	}

	public void setStayCalcDays(int stayCalcDays) {
		this.stayCalcDays = stayCalcDays;
	}
	
	public int getNewCalcDays() {
		return newCalcDays;
	}

	public void setNewCalcDays(int newCalcDays) {
		this.newCalcDays = newCalcDays;
	}
	
	public int getLongDisappearWarnDays() {
		return longDisappearWarnDays;
	}

	public void setLongDisappearWarnDays(int longDisappearWarnDays) {
		this.longDisappearWarnDays = longDisappearWarnDays;
	}
	
	public int getLongDisappearClearDays() {
		return longDisappearClearDays;
	}

	public void setLongDisappearClearDays(int longDisappearClearDays) {
		this.longDisappearClearDays = longDisappearClearDays;
	}
	
	public String getMsgTypes() {
		return msgTypes;
	}

	public void setMsgTypes(String msgTypes) {
		this.msgTypes = msgTypes;
	}
		
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}