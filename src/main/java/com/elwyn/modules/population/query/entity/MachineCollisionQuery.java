package com.elwyn.modules.population.query.entity;

import com.rainsoft.core.paging.Page;

import java.io.Serializable;

/**
 * @Name com.rainsoft.modules.population.query.entity.MachineCollisionQuery
 * @Description
 * @Author Elwyn
 * @Version 2017/4/10
 * @Copyright 上海云辰信息科技有限公司
 **/
public class MachineCollisionQuery implements Serializable {

	private static final long serialVersionUID = 1389256714436896838L;

	private Page page;
	private String machineId1;
	private String machineId2;
	private String machineId3;
	private String machineId4;
	private String machineId5;
	private String beginTime1;
	private String endTime1;
	private String beginTime2;
	private String endTime2;
	private String beginTime3;
	private String endTime3;
	private String beginTime4;
	private String endTime4;
	private String beginTime5;
	private String endTime5;
	private String flag;
	private String dangerPerson;
	private String dangerArea;

	public String getDangerPerson() {
		return dangerPerson;
	}

	public void setDangerPerson(String dangerPerson) {
		this.dangerPerson = dangerPerson;
	}

	public String getDangerArea() {
		return dangerArea;
	}

	public void setDangerArea(String dangerArea) {
		this.dangerArea = dangerArea;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getMachineId1() {
		return machineId1;
	}

	public void setMachineId1(String machineId1) {
		this.machineId1 = machineId1;
	}

	public String getMachineId2() {
		return machineId2;
	}

	public void setMachineId2(String machineId2) {
		this.machineId2 = machineId2;
	}

	public String getMachineId3() {
		return machineId3;
	}

	public void setMachineId3(String machineId3) {
		this.machineId3 = machineId3;
	}

	public String getMachineId4() {
		return machineId4;
	}

	public void setMachineId4(String machineId4) {
		this.machineId4 = machineId4;
	}

	public String getMachineId5() {
		return machineId5;
	}

	public void setMachineId5(String machineId5) {
		this.machineId5 = machineId5;
	}

	public String getBeginTime1() {
		return beginTime1;
	}

	public void setBeginTime1(String beginTime1) {
		this.beginTime1 = beginTime1;
	}

	public String getEndTime1() {
		return endTime1;
	}

	public void setEndTime1(String endTime1) {
		this.endTime1 = endTime1;
	}

	public String getBeginTime2() {
		return beginTime2;
	}

	public void setBeginTime2(String beginTime2) {
		this.beginTime2 = beginTime2;
	}

	public String getEndTime2() {
		return endTime2;
	}

	public void setEndTime2(String endTime2) {
		this.endTime2 = endTime2;
	}

	public String getBeginTime3() {
		return beginTime3;
	}

	public void setBeginTime3(String beginTime3) {
		this.beginTime3 = beginTime3;
	}

	public String getEndTime3() {
		return endTime3;
	}

	public void setEndTime3(String endTime3) {
		this.endTime3 = endTime3;
	}

	public String getBeginTime4() {
		return beginTime4;
	}

	public void setBeginTime4(String beginTime4) {
		this.beginTime4 = beginTime4;
	}

	public String getEndTime4() {
		return endTime4;
	}

	public void setEndTime4(String endTime4) {
		this.endTime4 = endTime4;
	}

	public String getBeginTime5() {
		return beginTime5;
	}

	public void setBeginTime5(String beginTime5) {
		this.beginTime5 = beginTime5;
	}

	public String getEndTime5() {
		return endTime5;
	}

	public void setEndTime5(String endTime5) {
		this.endTime5 = endTime5;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
