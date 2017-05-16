package com.elwyn.bigData.modules.population.config.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rainsoft.core.service.BaseCrudService;
import com.rainsoft.modules.population.config.dao.DangerPersonDao;
import com.rainsoft.modules.population.config.entity.DangerPerson;

/**
 * 高危人群库管理Service
 *
 * @author Sugar
 * @version 2017-04-14
 */
@Service
@Transactional(readOnly = true)
public class DangerPersonServiceImpl extends BaseCrudService<DangerPersonDao, DangerPerson> implements IDangerPersonService {

    @Override
    @Transactional(readOnly = false)
    public boolean save(DangerPerson entity) {
        entity.setUpdateTime(new Date());
        boolean result = true;
        if (StringUtils.isBlank(entity.getIdNo())) {
            entity.setIdNo("0");
        }
        if (StringUtils.isBlank(entity.getImsi())) {
            entity.setImsi("0");
        }
        if (StringUtils.isBlank(entity.getPhone())) {
            entity.setPhone("0");
        }
        if (entity.isNewRecord()) {
            entity.setCreateTime(new Date());
            dao.insert(entity);
        } else {
            result = dao.update(entity) > 0;
        }
        if (result) {
            dao.deleteGroupRel(entity.getId());
            String[] groupIds = StringUtils.split(entity.getGroupId(), ",");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("person_id", entity.getId());
            if (groupIds != null) {
                for (String groupId : groupIds) {
                    map.put("group_id", groupId);
                    dao.insertGroupRel(map);
                }
            }
        }
        return result;
    }

    @Override
    public boolean exists(DangerPerson entity) {
        return dao.count(entity) > 0;
    }

    @Override
    public int deleteById(String id) {
        int row = dao.deleteById(id);
        if (row > 0) {
            dao.deleteGroupRel(id);
        }
        return row;
    }
}