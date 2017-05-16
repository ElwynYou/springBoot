package com.elwyn.bigData.modules.population.config.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.core.entity.BaseEntity;

/**
 * @Name com.rainsoft.modules.population.config.entity.RemindEntity
 * @Description
 * 
 * @Author Sugar
 * @Version 2017年5月10日 下午1:13:59
 * @Copyright 上海云辰信息科技有限公司
 */
public class RemindEntity extends BaseEntity {
	protected String remindType; // 消息发送方式
	protected List<Arg> argList; // 提醒方式参数设置

	/**
	 * 
	 */
	public RemindEntity() {
		super();
	}

	/**
	 * @param id
	 */
	public RemindEntity(String id) {
		super(id);
	}

	/**
	 * @return the remindType
	 */
	public String getRemindType() {
		return remindType;
	}

	/**
	 * @param remindType the remindType to set
	 */
	public void setRemindType(String remindType) {
		this.remindType = remindType;
	}

	public List<Arg> getArgList() {
		if (this.argList == null) {
			this.argList = newArgList();
			if (!StringUtils.isBlank(this.remindType)) {
				Map<String, Object> obj = JSONObject.parseObject(this.remindType, Map.class);
				for (Arg arg : argList) {
					if (obj.containsKey(arg.getKey())) {
						arg.setValid(true);
						Map<String, String> m = (Map<String, String>) obj.get(arg.getKey());
						for (ArgParam param : arg.getParam()) {
							param.setValue(m.get(param.getKey()));
						}
					}
				}
			}
		}
		return this.argList;
	}
	
	private List<Arg> newArgList(){
		List<Arg> list = new ArrayList<>();
		List<ArgParam> param = new ArrayList<>();
		param.add(new ArgParam("phone", "手机号", "^1[34578]\\d{9}$"));
		list.add(new Arg("sms", "短信", param));
		param = new ArrayList<>();
		param.add(new ArgParam("appId", "APP账号", "^[0-9a-zA-Z]{6,12}$"));
		param.add(new ArgParam("mac", "Mac地址", "^[0-9a-zA-Z]{2}(-?[0-9a-zA-Z]{2}){5}$"));
		list.add(new Arg("app", "云警APP", param));
		return list;
	}
	

	public class Arg implements Serializable {
		public String key;// 标识
		public String label;// 显示名称
		public List<ArgParam> param;// 变量值String[]{"key","label","value"}
		public boolean valid; // 是否有效

		/**
		 * @param key
		 * @param label
		 */
		public Arg(String key, String label, List<ArgParam> param) {
			super();
			this.key = key;
			this.label = label;
			this.param = param;
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
		 * @return the param
		 */
		public List<ArgParam> getParam() {
			return param;
		}

		/**
		 * @param param
		 *            the param to set
		 */
		public void setParam(List<ArgParam> param) {
			this.param = param;
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

	public class ArgParam implements Serializable {
		public String key;// 标识
		public String label;// 显示名称
		public String value;// 值
		public String regex;// 验证正则表达式

		/**
		 * @param key
		 * @param label
		 * @param value
		 */
		public ArgParam(String key, String label) {
			super();
			this.key = key;
			this.label = label;
		}

		public ArgParam(String key, String label, String regex) {
			super();
			this.key = key;
			this.label = label;
			this.regex = regex;
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
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param value
		 *            the value to set
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * @return the regex
		 */
		public String getRegex() {
			return regex;
		}

		/**
		 * @param regex
		 *            the regex to set
		 */
		public void setRegex(String regex) {
			this.regex = regex;
		}
	}

}
