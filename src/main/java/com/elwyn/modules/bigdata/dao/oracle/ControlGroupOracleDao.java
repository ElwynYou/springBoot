package com.elwyn.modules.bigdata.dao.oracle;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.modules.bigdata.entity.ControlGroupEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * 
*
* @Name com.rainsoft.modules.bigdata.dao.oracle.ControlGroupOracleDao
* @Description
* 
* @Author Xian
* @Version 2016年12月30日 上午10:17:47
* @Copyright 上海云辰信息科技有限公司
*
*
 */
@MyBatisDao
public interface ControlGroupOracleDao {
	
	Collection<ControlGroupEntity> findPageControlGroup(DataTablesRequest<?> dataTablesRequest);
	long findPageCount(DataTablesRequest<?> dataTablesRequest);
    int addControlGroup(ControlGroupEntity controlGroupEntity);
    int updateValidStatus(ControlGroupEntity controlGroupEntity);
    int delViceControlGroupAll(ControlGroupEntity controlGroupEntity);
    int updateControlGroup(ControlGroupEntity controlGroupEntity);
    int delControlGroupByid(@Param("audId") int audId, @Param("controlId") String[] controlId);
    int delControlGroup(ControlGroupEntity controlGroupEntity);
    int addAssistantEquipment(ControlGroupEntity controlGroupEntity);
    ControlGroupEntity findControlGroupById(@Param("audId") int audId);
}
