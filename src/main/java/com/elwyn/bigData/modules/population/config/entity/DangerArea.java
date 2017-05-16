package com.elwyn.bigData.modules.population.config.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainsoft.core.entity.BaseEntity;

/**
 * 重点防范库管理Entity
 * 
 * @author Sugar
 * @version 2017-04-01
 */
public class DangerArea extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String area; // 区域
	private String brief; // 备注说明
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间
	private long createBy;

	public DangerArea() {
		super();
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the createBy
	 */
	public long getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}

}