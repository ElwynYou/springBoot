package com.elwyn.bigData.modules.population.query.entity;

import com.rainsoft.core.paging.Page;

import java.io.Serializable;

/**
 * @Name com.rainsoft.modules.population.query.entity.CrowdQuery
 * @Description 人群查询条件
 * 
 * @Author Sugar
 * @Version 2017年4月8日 下午7:50:42
 * @Copyright 上海云辰信息科技有限公司
 */
public class CrowdQuery implements Serializable{
	private String areaCode;// 小区所属城区
	private String serviceCode;//小区或者区域编码
	private String configId;
	private String machineId;
	private String beginTime;
	private String endTime;
	private String imsi;
	private String phoneArea;// imsi归属地
	private String index;
	private int dangerPerson;
	private int dangerArea;
	private String orderBy;

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	private Page<ScanEndingImproves> page;
	
	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
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
	 * @return the machineId
	 */
	public String getMachineId() {
		return machineId;
	}

	/**
	 * @param machineId
	 *            the machineId to set
	 */
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	/**
	 * @return the beginTime
	 */
	public String getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the imsi
	 */
	public String getImsi() {
		return imsi;
	}

	/**
	 * @param imsi
	 *            the imsi to set
	 */
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	/**
	 * @return the phoneArea
	 */
	public String getPhoneArea() {
		return phoneArea;
	}

	/**
	 * @param phoneArea the phoneArea to set
	 */
	public void setPhoneArea(String phoneArea) {
		this.phoneArea = phoneArea;
	}

	/**
	 * @return the dangerPerson
	 */
	public int getDangerPerson() {
		return dangerPerson;
	}

	/**
	 * @param dangerPerson
	 *            the dangerPerson to set
	 */
	public void setDangerPerson(int dangerPerson) {
		this.dangerPerson = dangerPerson;
	}

	/**
	 * @return the dangerArea
	 */
	public int getDangerArea() {
		return dangerArea;
	}

	/**
	 * @param dangerArea
	 *            the dangerArea to set
	 */
	public void setDangerArea(int dangerArea) {
		this.dangerArea = dangerArea;
	}

	/**
	 * @return the page
	 */
	public Page<ScanEndingImproves> getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Page<ScanEndingImproves> page) {
		this.page = page;
	}
	
	
}
