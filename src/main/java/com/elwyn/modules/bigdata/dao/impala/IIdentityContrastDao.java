package com.elwyn.modules.bigdata.dao.impala;

import com.rainsoft.core.annotation.ImpalaDao;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.modules.bigdata.entity.IdentityContrastEntity;

import java.util.List;

/**
 * @Name com.rainsoft.modules.bigdata.dao.impala
 * @Description 身份比对
 * @Author lisicai
 * @Version 2016/12/21 10:13
 * @Copyright 上海云辰信息科技有限公司
 */
@ImpalaDao
public interface IIdentityContrastDao {
	
	
	public List<IdentityContrastEntity> findListTraceAp(DataTablesRequest<?> dataTablesRequest);
	
	public long countFindListTraceAp(DataTablesRequest<?> dataTablesRequest);
}
