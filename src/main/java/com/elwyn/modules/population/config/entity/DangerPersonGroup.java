package com.elwyn.modules.population.config.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainsoft.core.entity.BaseEntity;

import java.util.Date;

/**
 * 高危人群（关注人群）分组管理Entity
 * 
 * @author Sugar
 * @version 2017-04-14
 */
public class DangerPersonGroup extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String name; // 分组名
	private Date updateTime; // 更新时间
	private String note; // 备注说明
	private long createBy; // 创建人ID
	private long personCount;// 高危人群数
	private String areaCode; // 所属辖区

	public DangerPersonGroup() {
		super();
	}

	public DangerPersonGroup(String id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the createBy
	 */
	public long getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy
	 *            the createBy to set
	 */
	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the personCount
	 */
	public long getPersonCount() {
		return personCount;
	}

	/**
	 * @param personCount
	 *            the personCount to set
	 */
	public void setPersonCount(long personCount) {
		this.personCount = personCount;
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
}