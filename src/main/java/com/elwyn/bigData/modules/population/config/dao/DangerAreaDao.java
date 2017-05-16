package com.elwyn.bigData.modules.population.config.dao;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.core.dao.IBaseDao;
import com.rainsoft.modules.population.config.entity.DangerArea;
import org.apache.ibatis.annotations.Param;

/**
 * 重点防范库管理DAO接口
 * @author Sugar
 * @version 2017-04-01
 */
@MyBatisDao
public interface DangerAreaDao extends IBaseDao<DangerArea> {
    int checkName(@Param("name") String name, @Param("id") String id);
}