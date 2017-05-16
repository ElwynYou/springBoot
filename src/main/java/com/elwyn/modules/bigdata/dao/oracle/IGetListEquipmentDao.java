/**
 * 
 */
package com.elwyn.modules.bigdata.dao.oracle;

import com.rainsoft.core.annotation.MyBatisDao;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.bigdata.entity.AreaCollisionEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Name com.rainsoft.modules.bigdata.dao.oracle.IGetListEquipment
 * @Description 
 * 
 * @Author lisicai
 * @Version 2016年12月22日 下午4:54:09
 * @Copyright 上海云辰信息科技有限公司
 *
*/
@MyBatisDao
public interface IGetListEquipmentDao {
	public List<MachineInfo> getMachineInfoByMap(Map map);
	public List<Map<String,String>> findListEquipment(AreaCollisionEntity areaCollision);
}
