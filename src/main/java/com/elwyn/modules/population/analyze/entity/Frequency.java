package com.elwyn.modules.population.analyze.entity;

import java.io.Serializable;

/**
 * @Name com.rainsoft.modules.population.analyze.entity.Frequency
 * @Description 频次分析
 * 
 * @Author Sugar
 * @Version 2017年4月7日 下午3:17:48
 * @Copyright 上海云辰信息科技有限公司
 */
public class Frequency implements Serializable {
	private String imsi;
	private String beginDate;
	private String endDate;
	private String type;

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
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate
	 *            the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}
