package com.elwyn.bigData.modules.population.heatmap.dao.hbase;

import com.rainsoft.core.annotation.ImpalaDao;
import com.rainsoft.modules.population.heatmap.entity.TheHeat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@ImpalaDao
public interface HeatmapHbaseDao {

	List<TheHeat> findHeatmap(@Param("id") String id, @Param("userId") String userId, @Param("statTime") String statTime);
	
}
