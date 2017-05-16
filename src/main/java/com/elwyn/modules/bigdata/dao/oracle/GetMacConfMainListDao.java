package com.elwyn.modules.bigdata.dao.oracle;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.modules.bigdata.entity.TabMacFriConfMain;

import java.util.List;

/**
 * @Name com.rainsoft.modules.bigdata.dao.oracle
 * @Description
 * @Author Elwyn
 * @Version 2016/12/30 15:02
 * @Copyright 上海云辰信息科技有限公司
 */
@MyBatisDao
public interface GetMacConfMainListDao {
    List<TabMacFriConfMain> getMacConfList(DataTablesRequest dataTablesRequest);
    int getCount(DataTablesRequest dataTablesRequest);
    void addMacConf(TabMacFriConfMain tabMacFriConfMain);
    void addAssistantMac(TabMacFriConfMain tabMacFriConfMain);
    void deleteMacMain(TabMacFriConfMain tabMacFriConfMain);
    void deleteMacConfAdd(TabMacFriConfMain tabMacFriConfMain);
    int updateMainValidStatus(TabMacFriConfMain tabMacFriConfMain);
    int updateMacMainInfo(TabMacFriConfMain tabMacFriConfMain);
    int updateMacAddInfo(TabMacFriConfMain tabMacFriConfMain);
}
