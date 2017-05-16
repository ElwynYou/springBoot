/**
 * 
 */
package com.elwyn.bigData.modules.bigdata.dao.oracle;

import java.util.List;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.model.bigdata.CollisionInfo;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.modules.bigdata.entity.AreaCollisionEntity;

/**
 * 
 * @Name com.rainsoft.modules.bigdata.dao.impala.IAreaCollisionDao
 * @Description 
 * 
 * @Author lisicai
 * @Version 2016年12月26日 下午6:05:46
 * @Copyright 上海云辰信息科技有限公司
 *
*/
@MyBatisDao
public interface IAreaCollisionOracleDao {
	public List<CollisionInfo> findListCollisionInfo(DataTablesRequest<AreaCollisionEntity> dataTablesRequest);
	public long countCollisionInfo(DataTablesRequest<AreaCollisionEntity> dataTablesRequest);
}
