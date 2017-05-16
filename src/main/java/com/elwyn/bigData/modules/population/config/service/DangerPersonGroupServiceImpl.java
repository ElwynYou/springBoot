package com.elwyn.bigData.modules.population.config.service;

import com.rainsoft.core.service.BaseCrudService;
import com.rainsoft.modules.population.config.dao.DangerPersonGroupDao;
import com.rainsoft.modules.population.config.entity.DangerPersonGroup;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 高危人群（关注人群）分组管理Service
 *
 * @author Sugar
 * @version 2017-04-14
 */
@Service
public class DangerPersonGroupServiceImpl extends BaseCrudService<DangerPersonGroupDao, DangerPersonGroup> implements IDangerPersonGroupService {
	@Override
	public int deleteById(String id) {
		int row = super.deleteById(id);
		if (row > 0) {
			dao.deleteGroupRel(id);
		}
		return row;
	}

	@Override
	public boolean save(DangerPersonGroup entity) {
		entity.setUpdateTime(new Date());
		return super.save(entity);
	}

	@Override
	public int checkName(String name,String id) {
		return dao.checkName(name,id);
	}

	@Override
	public DangerPersonGroup getByName(String name){
		return dao.getByName(name);
	}
}