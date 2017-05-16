/**
 * 
 */
package com.elwyn.modules.bigdata.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @Name com.rainsoft.modules.bigdata.entity.AreaCollisionControllerEntity
 * @Description 
 * 
 * @Author lisicai
 * @Version 2016年12月22日 下午4:30:21
 * @Copyright 上海云辰信息科技有限公司
 *
*/
public class AreaCollisionEntity implements Serializable{
	
	
	private String sw_lng;//界面所画图形西南脚点经度
	private String sw_lat;//界面所画图形西南脚点纬度
	private String ne_lng;//界面所画图形东北脚点经度
	private String ne_lat;//界面所画图形东北脚点纬度度
	private String graphicType;//图形类型 1 为矩形，2为圆
	private String radius;//圆半径
	private String lng;//圆心经度
	private String lat;//圆心纬度
	private List<String> startTimeList;
	private List<String> endTimeList;
	private List<String> machineIdList;
	private String groupNumber;
	
	
	public String getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
	public List<String> getStartTimeList() {
		return startTimeList;
	}
	public void setStartTimeList(List<String> startTimeList) {
		this.startTimeList = startTimeList;
	}
	public List<String> getEndTimeList() {
		return endTimeList;
	}
	public void setEndTimeList(List<String> endTimeList) {
		this.endTimeList = endTimeList;
	}
	public List<String> getMachineIdList() {
		return machineIdList;
	}
	public void setMachineIdList(List<String> machineIdList) {
		this.machineIdList = machineIdList;
	}
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getGraphicType() {
		return graphicType;
	}
	public void setGraphicType(String graphicType) {
		this.graphicType = graphicType;
	}
	public String getSw_lng() {
		return sw_lng;
	}
	public void setSw_lng(String sw_lng) {
		this.sw_lng = sw_lng;
	}
	public String getSw_lat() {
		return sw_lat;
	}
	public void setSw_lat(String sw_lat) {
		this.sw_lat = sw_lat;
	}
	public String getNe_lng() {
		return ne_lng;
	}
	public void setNe_lng(String ne_lng) {
		this.ne_lng = ne_lng;
	}
	public String getNe_lat() {
		return ne_lat;
	}
	public void setNe_lat(String ne_lat) {
		this.ne_lat = ne_lat;
	}
		
}
