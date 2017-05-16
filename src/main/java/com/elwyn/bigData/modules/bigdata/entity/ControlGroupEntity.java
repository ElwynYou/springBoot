package com.elwyn.bigData.modules.bigdata.entity;

import java.io.Serializable;

/**
 * 
* @Name com.rainsoft.modules.bigdata.entity.ControlGroupEntity
* @Description	对照组实体对象
* 
* @Author Xian
* @Version 2016年12月30日 上午10:43:07
* @Copyright 上海云辰信息科技有限公司
*
*
 */
public class ControlGroupEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7786992387838906689L;
	
	//统计次数 默认为 0
	public static final String STATS_TIMES = "0";
	
	//统计状态 0 未统计 1 已统计 默认为 0
	public static final String STATS_STATUS_0 = "0";
	public static final String STATS_STATUS_1 = "1";
	
	//有效状态 0 无效 1 有效 默认为 1
	public static final String VALID_STATUS_0 = "0";
	public static final String VALID_STATUS_1 = "1";
	
	//0代表添加1代表修改
	public static final int DATATYPE_0 = 0;
	public static final int DATATYPE_1 = 1;

	private int audId;//对照ID
	
	private String audName;//对照名称
	
	private String equipmentId;//对照主设备ID
	
	private String equipmentIdAdd;//附加设备id
	
	private String equipmentName;//附加设备名称
	
	private String equipmentBeginTime;//附加设备开始时间

	private String equipmentEndTime;//附加设备结束时间
	
	private String checkName;//设备名称
	
	private String beginTime;//开始时间
	
	private String endTime;//结束时间
	
	private String weight;//权重
	
	private String statsDate;//统计日期
	
	private String statsTimes;//统计次数 默认为 0
	
	private String statsStatus;//统计状态 0 未统计 1 已统计 默认为 0
	
	private String validStatus;//有效状态 0 无效 1 有效 默认为 1
	
	private String insertTime;//插入时间
	
	private String[] checkNames;//设备名称集合
	
	private String[] equipmentIdAdds;//附加设备id集合
	
	private String[] beginTimes;//设备检测开始时间集合
	
	private String[] endTimes;//设备检测结束时间集合
	
	private String delId;//删除副设备id
	
	private int[] dataType;//数据类型

	public int getAudId() {
		return audId;
	}

	public void setAudId(int audId) {
		this.audId = audId;
	}

	public String getAudName() {
		return audName;
	}

	public void setAudName(String audName) {
		this.audName = audName;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(String statsDate) {
		this.statsDate = statsDate;
	}

	public String getStatsTimes() {
		return statsTimes;
	}

	public void setStatsTimes(String statsTimes) {
		this.statsTimes = statsTimes;
	}

	public String getStatsStatus() {
		return statsStatus;
	}

	public void setStatsStatus(String statsStatus) {
		this.statsStatus = statsStatus;
	}

	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String[] getCheckNames() {
		return checkNames;
	}

	public void setCheckNames(String[] checkNames) {
		this.checkNames = checkNames;
	}

	public String[] getBeginTimes() {
		return beginTimes;
	}

	public void setBeginTimes(String[] beginTimes) {
		this.beginTimes = beginTimes;
	}

	public String[] getEndTimes() {
		return endTimes;
	}

	public void setEndTimes(String[] endTimes) {
		this.endTimes = endTimes;
	}

	public String getEquipmentIdAdd() {
		return equipmentIdAdd;
	}

	public void setEquipmentIdAdd(String equipmentIdAdd) {
		this.equipmentIdAdd = equipmentIdAdd;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String[] getEquipmentIdAdds() {
		return equipmentIdAdds;
	}

	public void setEquipmentIdAdds(String[] equipmentIdAdds) {
		this.equipmentIdAdds = equipmentIdAdds;
	}

	public String getEquipmentBeginTime() {
		return equipmentBeginTime;
	}

	public void setEquipmentBeginTime(String equipmentBeginTime) {
		this.equipmentBeginTime = equipmentBeginTime;
	}

	public String getEquipmentEndTime() {
		return equipmentEndTime;
	}

	public void setEquipmentEndTime(String equipmentEndTime) {
		this.equipmentEndTime = equipmentEndTime;
	}

	public String getDelId() {
		return delId;
	}

	public void setDelId(String delId) {
		this.delId = delId;
	}

	public int[] getDataType() {
		return dataType;
	}

	public void setDataType(int[] dataType) {
		this.dataType = dataType;
	}
	
}
