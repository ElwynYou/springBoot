package com.elwyn.bigData.modules.bigdata.dao.oracle;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.modules.bigdata.entity.CompanionGroup;

import java.util.List;

/**
 * @Name com.rainsoft.modules.bigdata.dao.oracle
 * @Description
 * @Author Elwyn
 * @Version 2017/1/3 9:55
 * @Copyright 上海云辰信息科技有限公司
 */
@MyBatisDao
public interface CompanionGroupDao {
    List<CompanionGroup> getCompanionGroupList(DataTablesRequest dataTablesRequest);
    int getCount(DataTablesRequest dataTablesRequest);
}
