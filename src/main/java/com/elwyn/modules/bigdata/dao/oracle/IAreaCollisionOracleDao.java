/**
 * 
 */
package com.elwyn.modules.bigdata.dao.oracle;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.modules.bigdata.entity.AreaCollisionEntity;

import java.util.List;
import java.util.Map;

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
	public List<Map<String,String>> findListCollisionInfo(DataTablesRequest<AreaCollisionEntity> dataTablesRequest);
	public long countCollisionInfo(DataTablesRequest<AreaCollisionEntity> dataTablesRequest);
}
