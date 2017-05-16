/**
 * 
 */
package com.elwyn.bigData.modules.bigdata.dao.impala;

import java.util.List;
import java.util.Map;

import com.rainsoft.core.annotation.ImpalaDao;
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
@ImpalaDao
public interface IAreaCollisionDao {
	public List<Map<String,String>> findListCollisionInfo(DataTablesRequest<AreaCollisionEntity> dataTablesRequest);
	public long countCollisionInfo(DataTablesRequest<AreaCollisionEntity> dataTablesRequest);
}
