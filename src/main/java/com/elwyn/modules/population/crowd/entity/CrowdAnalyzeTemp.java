/**
 * 
 */
package com.elwyn.modules.population.crowd.entity;

import java.io.Serializable;

/**
 * @Name com.rainsoft.modules.population.crowd.entity.CrowdAnalyzeTemp
 * @Description
 * @Author xa
 * @Version 2017年4月7日 下午6:01:49
 * @Copyright 上海云辰信息科技有限公司
 */
public class CrowdAnalyzeTemp implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
