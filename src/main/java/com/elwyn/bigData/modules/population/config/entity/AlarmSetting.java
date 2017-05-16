package com.elwyn.bigData.modules.population.config.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 报警设置管理Entity
 * 
 * @author Sugar
 * @version 2017-04-05
 */
public class AlarmSetting extends RemindEntity {

	private static final long serialVersionUID = 1L;

	private long alarmId; // 报警策略
	private String alarmName; // 报警策略
	private String serviceCode; // 区域名称
	private String serviceName; // 区域名称
	private String receiver; // 接警人
	private Date updateTime; // 更新时间
	private long updateUid; // 更新人

	private String serviceType; // 场所类型
	private String areaCode; // 所属城区

	public AlarmSetting() {
		super();
	}

	public AlarmSetting(String id) {
		super(id);
	}

	public long getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(long alarmId) {
		this.alarmId = alarmId;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the alarmName
	 */
	public String getAlarmName() {
		return alarmName;
	}

	/**
	 * @param alarmName
	 *            the alarmName to set
	 */
	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName
	 *            the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the updateUid
	 */
	public long getUpdateUid() {
		return updateUid;
	}

	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType
	 *            the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode
	 *            the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @param updateUid
	 *            the updateUid to set
	 */
	public void setUpdateUid(long updateUid) {
		this.updateUid = updateUid;
	}
}