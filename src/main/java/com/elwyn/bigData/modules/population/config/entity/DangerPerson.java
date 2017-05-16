package com.elwyn.bigData.modules.population.config.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainsoft.core.entity.BaseEntity;

/**
 * 高危人群库管理Entity
 * 
 * @author Sugar
 * @version 2017-04-14
 */
public class DangerPerson extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private int type; // 关注类型
	private String name; // 姓名
	private String idNo; // 身份证号
	private String imsi; // IMSI号
	private String phone; // 手机号
	private int rank; // 危险等级：0-普通
	private String brief; // 备注说明
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间
	private long createBy; // 创建人ID
	private String groupId; // 分组Id
	private String groupName; // 分组名

	public DangerPerson() {
		super();
	}

	public DangerPerson(String id) {
		super(id);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
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
	 * @param createBy
	 *            the createBy to set
	 */
	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}
	
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	
	public String[] getGroupIds() {
		return StringUtils.split(groupId, ",");
	}
}