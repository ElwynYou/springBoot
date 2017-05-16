package com.elwyn.bigData.modules.bigdata.util;

import com.rainsoft.model.AreaStation;
import com.rainsoft.model.User;
import com.rainsoft.modules.bigdata.dao.oracle.GetMachineInfoDao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Name com.rainsoft.modules.bigdata.util
 * @Description 根据登陆用户查询相关用户设备
 * @Author Elwyn
 * @Version 2017/2/16 9:51
 * @Copyright 上海云辰信息科技有限公司
 */
public class GetServiceCodesByUser {
    // 获取当前用户和城区
    public final static List<String> getServiceCodeByArea(User user, GetMachineInfoDao getMachineInfoDao) {
        Iterator<AreaStation> it = user.getAreas().iterator();
        List<Long> areas = new ArrayList<>();
        while (it.hasNext()) {
            AreaStation area = it.next();
            areas.add(area.getCode());
        }
        return getMachineInfoDao.getUserServiceCode(areas);
    }
}
