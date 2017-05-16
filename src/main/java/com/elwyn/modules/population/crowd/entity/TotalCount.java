package com.elwyn.modules.population.crowd.entity;

import java.io.Serializable;

/**
 * @Name com.rainsoft.modules.population.crowd.entity.TotalCount
 * @Description
 * @Author Elwyn
 * @Version 2017/4/1
 * @Copyright 上海云辰信息科技有限公司
 **/
public class TotalCount implements Serializable {

	private static final long serialVersionUID = -7236360492625212104L;
	private String total;
	private String countName;
	private String areaName;//城区
	private String cell;//小区
	private String improtantArea;//重点区域
	private String machineNum;
	private String totalPerson;
	private String highRiskNum;
	private String preventNum;
	private String areaCode;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getImprotantArea() {
		return improtantArea;
	}

	public void setImprotantArea(String improtantArea) {
		this.improtantArea = improtantArea;
	}

	public String getMachineNum() {
		return machineNum;
	}

	public void setMachineNum(String machineNum) {
		this.machineNum = machineNum;
	}

	public String getTotalPerson() {
		return totalPerson;
	}

	public void setTotalPerson(String totalPerson) {
		this.totalPerson = totalPerson;
	}

	public String getHighRiskNum() {
		return highRiskNum;
	}

	public void setHighRiskNum(String highRiskNum) {
		this.highRiskNum = highRiskNum;
	}

	public String getPreventNum() {
		return preventNum;
	}

	public void setPreventNum(String preventNum) {
		this.preventNum = preventNum;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getCountName() {
		return countName;
	}

	public void setCountName(String countName) {
		this.countName = countName;
	}



}
