package com.elwyn.bigData.modules.population.config.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rainsoft.core.service.BaseCrudService;
import com.rainsoft.modules.population.config.dao.DangerAreaDao;
import com.rainsoft.modules.population.config.entity.DangerArea;
import com.rainsoft.utils.Utils;

/**
 * 重点防范库管理Service
 * 
 * @author Sugar
 * @version 2017-04-01
 */
@Service
@Transactional(readOnly = true)
public class DangerAreaServiceImpl extends BaseCrudService<DangerAreaDao, DangerArea> implements IDangerAreaService {

	@Override
	public List<DangerArea> findList(DangerArea entity) {
		entity.setArea(Utils.parseArea(entity.getArea()));
		return super.findList(entity);
	}

	@Override
	public boolean save(DangerArea entity) {
//		if (entity.isNewRecord()) {
//			entity.setCreateTime(new Date());
//		}
		entity.setUpdateTime(new Date());
		return super.save(entity);
	}

	@Override
	public int checkName(String name,String id) {
		return dao.checkName(name, id);
	}
}