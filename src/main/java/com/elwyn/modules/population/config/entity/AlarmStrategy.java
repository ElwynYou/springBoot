package com.elwyn.modules.population.config.entity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainsoft.core.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报警策略管理Entity
 * 
 * @author Sugar
 * @version 2017-04-05
 */
public class AlarmStrategy extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private static final String[][] ALARM_ARGS = new String[][] { new String[] { "total", "总人数" }, new String[] { "danger_person", "高危人群" }, new String[] { "danger_area", "重点防范人群" }};

	private String name; // 策略名称
	private String startDate; // 生效开始日期
	private String endDate; // 生效结束日期
	private String startTime; // 监控开始时段
	private String endTime; // 监控结束时段
	private String args; // 预警人数
	private Date updateTime; // 更新时间
	private long updateUid; // 更新人ID
	private List<Arg> argList; // 预警人数列表

	public AlarmStrategy() {
	}

	public AlarmStrategy(String id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public long getUpdateUid() {
		return updateUid;
	}

	public void setUpdateUid(long updateUid) {
		this.updateUid = updateUid;
	}

	/**
	 * @return the argList
	 */
	public List<Arg> getArgList() {
		if (argList == null) {
			argList = new ArrayList<>();
			for(String[] ss : ALARM_ARGS) {
				argList.add(new Arg(ss[0], ss[1]));
			}
			if (!StringUtils.isBlank(this.args)) {
				JSONObject obj = JSONObject.parseObject(this.args);
				this.argList = getArgList();
				for(Arg arg: argList) {
					if (obj.containsKey(arg.key)) {
						arg.setValid(true);
						arg.setNum(obj.getIntValue(arg.key));
					}
				}
			}
		}
		return argList;
	}

	/**
	 * 预警人数对象
	 */
	public static class Arg implements Serializable {
		private String key;// 标识
		private String label;// 显示名称
		private int num;// 数量
		private boolean valid; // 是否有效

		/**
		 * @param key
		 * @param label
		 */
		public Arg(String key, String label) {
			super();
			this.key = key;
			this.label = label;
		}

		/**
		 * @param key
		 * @param label
		 * @param num
		 * @param valid
		 */
		public Arg(String key, String label, int num, boolean valid) {
			super();
			this.key = key;
			this.label = label;
			this.num = num;
			this.valid = valid;
		}

		/**
		 * @return the key
		 */
		public String getKey() {
			return key;
		}

		/**
		 * @param key
		 *            the key to set
		 */
		public void setKey(String key) {
			this.key = key;
		}

		/**
		 * @return the label
		 */
		public String getLabel() {
			return label;
		}

		/**
		 * @param label
		 *            the label to set
		 */
		public void setLabel(String label) {
			this.label = label;
		}

		/**
		 * @return the num
		 */
		public int getNum() {
			return num;
		}

		/**
		 * @param num
		 *            the num to set
		 */
		public void setNum(int num) {
			this.num = num;
		}

		/**
		 * @return the valid
		 */
		public boolean isValid() {
			return valid;
		}

		/**
		 * @param valid
		 *            the valid to set
		 */
		public void setValid(boolean valid) {
			this.valid = valid;
		}
	}
}