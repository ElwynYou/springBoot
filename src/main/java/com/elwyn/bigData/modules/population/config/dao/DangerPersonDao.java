package com.elwyn.bigData.modules.population.config.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.core.dao.IBaseDao;
import com.rainsoft.modules.population.config.entity.DangerPerson;

/**
 * 高危人群库管理DAO接口
 * 
 * @author Sugar
 * @version 2017-03-31
 */
@MyBatisDao
public interface DangerPersonDao extends IBaseDao<DangerPerson> {

	/**
	 * 保存分组关系
	 * 
	 * @param map
	 */
	public void insertGroupRel(Map<String, Object> map);

	/**
	 * 删除分组关系
	 * 
	 * @param id
	 *            personId
	 */
	public void deleteGroupRel(@Param("id") String id);
	/**
	 * 根据imsi查询
	 * @param imsi
	 * @return
	 */
	public List<DangerPerson> getByImsi(@Param("imsi") String imsi);
	
	/**
	 * 查询记录数
	 * @param entity
	 * @return
	 */
	public int count(DangerPerson entity);
}