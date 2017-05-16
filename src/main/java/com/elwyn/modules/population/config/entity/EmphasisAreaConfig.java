package com.elwyn.modules.population.config.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 重点区域设置Entity
 * 
 * @author Sugar
 * @version 2017-04-14
 */
public class EmphasisAreaConfig extends RemindEntity {

	private static final long serialVersionUID = 1L;
	
	private String serviceCode; // 场所编码
	private String serviceName; // 重点区域名称
	private int doubtfulPeriod; // 可疑人群计算周期
	private int doubtfulDays; // 可疑人群出现天数
	private int doubtfulTimes; // 可疑人群出现次数
	private String msgTypes; // 待发送的消息类型，多个用英文逗号隔开
	private String remindBeginTime; // 提醒开始时段
	private String remindEndTime; // 提醒结束时段
	private Date updateTime; // update_time
	private long createBy; // 创建者

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public EmphasisAreaConfig() {
		super();
	}

	public EmphasisAreaConfig(String id) {
		super(id);
	}

	public long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public int getDoubtfulPeriod() {
		return doubtfulPeriod;
	}

	public void setDoubtfulPeriod(int doubtfulPeriod) {
		this.doubtfulPeriod = doubtfulPeriod;
	}

	public int getDoubtfulDays() {
		return doubtfulDays;
	}

	public void setDoubtfulDays(int doubtfulDays) {
		this.doubtfulDays = doubtfulDays;
	}

	public int getDoubtfulTimes() {
		return doubtfulTimes;
	}

	public void setDoubtfulTimes(int doubtfulTimes) {
		this.doubtfulTimes = doubtfulTimes;
	}

	public String getMsgTypes() {
		return msgTypes;
	}

	public void setMsgTypes(String msgTypes) {
		this.msgTypes = msgTypes;
	}

	public String getRemindBeginTime() {
		return remindBeginTime;
	}

	public void setRemindBeginTime(String remindBeginTime) {
		this.remindBeginTime = remindBeginTime;
	}

	public String getRemindEndTime() {
		return remindEndTime;
	}

	public void setRemindEndTime(String remindEndTime) {
		this.remindEndTime = remindEndTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}