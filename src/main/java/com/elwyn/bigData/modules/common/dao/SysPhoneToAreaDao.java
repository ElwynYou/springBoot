/**
 * 
 */
package com.elwyn.bigData.modules.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.model.SysPhoneToArea;

/**
 * @Name com.rainsoft.modules.common.dao.SysPhoneToAreaDao
 * @Description 
 * @Author houxu
 * @Version 2017年3月6日 下午1:05:56
 * @Copyright 上海云辰信息科技有限公司
 */
@MyBatisDao
@Transactional(readOnly=true)
public interface SysPhoneToAreaDao {
	public List<SysPhoneToArea> findPhoneToArea(Map map);
	public long getCount();
 }
