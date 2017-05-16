package com.elwyn.bigData.modules.population.config.dao;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.core.dao.IBaseDao;
import com.rainsoft.modules.population.config.entity.DangerPersonGroup;
import org.apache.ibatis.annotations.Param;

/**
 * 高危人群（关注人群）分组管理DAO接口
 * 
 * @author Sugar
 * @version 2017-04-14
 */
@MyBatisDao
public interface DangerPersonGroupDao extends IBaseDao<DangerPersonGroup> {
	/**
	 * 删除分组关系
	 * 
	 * @param id
	 *            groupId
	 */
	public void deleteGroupRel(@Param("id") String id);

	int checkName(@Param("name") String name, @Param("id") String id);

	DangerPersonGroup getByName(@Param("name") String name);
}