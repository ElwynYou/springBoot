package com.elwyn.bigData.modules.population.heatmap.service.oracle;


import com.rainsoft.core.paging.Page;
import com.rainsoft.modules.population.heatmap.dao.oracle.HeatmapDao;
import com.rainsoft.modules.population.heatmap.entity.HeatMapDetail;
import com.rainsoft.modules.population.heatmap.entity.HeatMapHistory;
import com.rainsoft.modules.population.heatmap.entity.HeatMapSuspicious;
import com.rainsoft.modules.population.heatmap.entity.TheHeat;
import com.rainsoft.modules.population.heatmap.service.IHeatmapService;
import com.rainsoft.modules.population.heatmap.websocket.runner.*;
import com.rainsoft.modules.population.query.entity.CrowdQuery;
import com.rainsoft.modules.population.query.entity.ScanEndingImproves;
import com.rainsoft.utils.DateUtil;
import com.rainsoft.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class HeatmapServiceImpl implements IHeatmapService {

	@Autowired
	private HeatmapDao heatmapDao;


	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();


	@Override
	public Map<String, Object> initHeatMapDatas(String configId) {
		try {
			Map<String,Object> time = heatmapDao.getMaxTime(null);
			Set<String> machineIds = heatmapDao.getMachineIdsByConfigId(configId);
			time.put("machineIds",machineIds);
			time.put("configId",configId);
			List<TheHeat> initDangerAreaCount = heatmapDao.initDangerAreaCount(time);
			List<TheHeat> initDangerPersonCount = heatmapDao.initDangerPersonCount(time);
			List<TheHeat> initHeatmap = heatmapDao.initHeatmap(time);

			List<Map<String, Object>> initTotalPerson = heatmapDao.initTotalPerson(time);
			String beginTime = String.valueOf(time.get("beginTime"));
			String endTime = String.valueOf(time.get("endTime"));
			Map<String, Object> timeMap = new HashMap<>();
			timeMap.put("machineIds",machineIds);
			timeMap.put("beginTime", DateUtil.getDateByString(beginTime, "yyyy-MM-dd HH:mm", -1, Calendar.DATE));
			timeMap.put("endTime", DateUtil.getDateByString(endTime, "yyyy-MM-dd HH:mm", -1, Calendar.DATE));
			List<Map<String, Object>> initTotalPersonPreDay = heatmapDao.initTotalPerson(timeMap);
			timeMap.put("beginTime", DateUtil.getDateByString(beginTime, "yyyy-MM-dd HH:mm", -7, Calendar.DATE));
			timeMap.put("endTime", DateUtil.getDateByString(endTime, "yyyy-MM-dd HH:mm", -7, Calendar.DATE));
			List<Map<String, Object>> initTotalPersonPreWeek = heatmapDao.initTotalPerson(timeMap);
			timeMap.put("beginTime", DateUtil.getDateByString(beginTime, "yyyy-MM-dd HH:mm", -1, Calendar.YEAR));
			timeMap.put("endTime", DateUtil.getDateByString(endTime, "yyyy-MM-dd HH:mm", -1, Calendar.YEAR));
			List<Map<String, Object>> initTotalPersonPreYear = heatmapDao.initTotalPerson(timeMap);
			TheHeat heatClock = heatmapDao.initHeatClock(time);
			List<TheHeat> initSuspiciousPeople = heatmapDao.initSuspiciousPeople(time);
			Map<String, Object> resultData = new HashMap<>();
			resultData.put("totalPerson", initTotalPerson);
			resultData.put("heatMapData", initHeatmap);
			resultData.put("dangerAreaCount", initDangerAreaCount);
			resultData.put("dangerPersonCount", initDangerPersonCount);
			resultData.put("suspiciousPeople", initSuspiciousPeople);
			resultData.put("totalPersonPreDay", initTotalPersonPreDay);
			resultData.put("totalPersonPreWeek", initTotalPersonPreWeek);
			resultData.put("totalPersonPreYear", initTotalPersonPreYear);
			resultData.put("heatClock", heatClock);
			resultData.putAll( time);
			resultData.put("initdata", "init");
			return resultData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public Map<String, Object> getHeatMapDatas(String configId, String interval) {
		Set<String> machineIds = heatmapDao.getMachineIdsByConfigId(configId);
		try {
			Map<String, Object> resultData = heatmapDao.getMaxTime(interval);
			resultData.put("machineIds",machineIds);
			cachedThreadPool.execute(new HeatMapRunner(resultData));
			cachedThreadPool.execute(new TotalPersonRunner(resultData));
			cachedThreadPool.execute(new DangerAreaCountRunner(resultData));
			cachedThreadPool.execute(new DangerPersonCountRunner(resultData));
			cachedThreadPool.execute(new SuspiciousPeopleRunner(resultData));
			cachedThreadPool.execute(new HeatClockRunner(resultData));
			while (true) {
				if (resultData.containsKey("totalPerson") && resultData.containsKey("heatMapData")
						&& resultData.containsKey("dangerAreaCount") && resultData.containsKey("dangerPersonCount")
						&& resultData.containsKey("suspiciousPeople") && resultData.containsKey("totalPersonPreDay")
						&& resultData.containsKey("totalPersonPreWeek") && resultData.containsKey("totalPersonPreYear")
						&& resultData.containsKey("heatClock")
						) {
					return resultData;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}


	@Override
	public List<TheHeat> initHeatMapIndex(String init, String interval) {

		if (init != null) {
			Map<String,Object> time = heatmapDao.getMaxTime(null);
			return heatmapDao.initHeatMapIndex(time);
		} else {
			Map<String,Object> time = heatmapDao.getMaxTime(interval);
			return heatmapDao.initHeatMapIndex(time);
		}
	}

	private Map<String, HeatMapHistory> assembleMap(DateParm dp) {
		SimpleDateFormat sdf = new SimpleDateFormat(dp.queryDateFromat);
		String queryTimeStr = null;
		Date nowdate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.clear();
		try {
			queryTimeStr = sdf.format(sdf.parse(dp.queryTime));
			cal.setTime(sdf.parse(queryTimeStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int maxDate = 0;
		if (sdf.format(nowdate).startsWith(queryTimeStr)) {
			cal.setTime(nowdate);
			maxDate = cal.get(dp.calendarType);
		} else {
			maxDate = cal.getActualMaximum(dp.calendarType);

		}
		if (dp.queryTimeType.equals("month")) {
			maxDate -= 1;
		}
		int tempDate = 0;
		SimpleDateFormat sdf2 = new SimpleDateFormat(dp.showDateFromat);
		Map<String, HeatMapHistory> hm = new LinkedHashMap<String, HeatMapHistory>();
		while (tempDate != maxDate + 1) {
			int saveDate = 0;
			if (!dp.queryTimeType.equals("day")) {
				saveDate = tempDate + 1;
			} else {
				saveDate = tempDate;
			}
			cal.set(dp.calendarType, saveDate);
			HeatMapHistory hh = new HeatMapHistory();
			hh.setSimpStatDate((saveDate < 10 ? "0" + saveDate : saveDate) + "");
			hh.setStatDate(sdf2.format(cal.getTime()));
			hm.put(sdf2.format(cal.getTime()), hh);
			tempDate++;
		}
		return hm;
	}

	private class DateParm {
		String queryDateFromat;// 查询时间格式
		String showDateFromat;// 查询结果时间格式
		int calendarType;// 需要的type
		String queryTime;// 查询时间
		String queryTimeType;// 查询类型 day month year
	}
	
	@Override
	public List<HeatMapHistory> getHeatMapHistory(HeatMapHistory heatMapHistory) {
		List<HeatMapHistory> heatMapHistoryList = null;
		Map<String, HeatMapHistory> tempMap = null;
		String showDateFromat = null;
		DateParm dp = new DateParm();
		if (heatMapHistory.getQueryTimeType().equals(HeatMapHistory.QUERY_TIME_TYPE_DAY)) {
			heatMapHistoryList = heatmapDao.getHeatMapHistoryListByDay(heatMapHistory);
			dp.calendarType = Calendar.HOUR_OF_DAY;
			dp.queryDateFromat = "yyyy-MM-dd";
			showDateFromat = dp.showDateFromat = "yyyy-MM-dd HH";
			dp.queryTime = heatMapHistory.getQueryTime();
			dp.queryTimeType = HeatMapHistory.QUERY_TIME_TYPE_DAY;
			tempMap = assembleMap(dp);
		}
		if (heatMapHistory.getQueryTimeType().equals(HeatMapHistory.QUERY_TIME_TYPE_MONTH)) {
			heatMapHistoryList = heatmapDao.getHeatMapHistoryListByMonth(heatMapHistory);
			dp.calendarType = Calendar.DAY_OF_MONTH;
			dp.queryDateFromat = "yyyy-MM";
			showDateFromat = dp.showDateFromat = "yyyy-MM-dd";
			dp.queryTime = heatMapHistory.getQueryTime();
			dp.queryTimeType = HeatMapHistory.QUERY_TIME_TYPE_MONTH;
			tempMap = assembleMap(dp);
		}
		if (heatMapHistory.getQueryTimeType().equals(HeatMapHistory.QUERY_TIME_TYPE_YEAR)) {
			heatMapHistoryList = heatmapDao.getHeatMapHistoryListByYear(heatMapHistory);
			dp.calendarType = Calendar.MONTH;
			dp.queryDateFromat = "yyyy";
			showDateFromat = dp.showDateFromat = "yyyy-MM";
			dp.queryTime = heatMapHistory.getQueryTime();
			dp.queryTimeType = HeatMapHistory.QUERY_TIME_TYPE_YEAR;
			tempMap = assembleMap(dp);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(showDateFromat);
		for (HeatMapHistory heatMapHistoryTemp : heatMapHistoryList) {
			String key = null;
			try {
				key = sdf.format(sdf.parse(heatMapHistoryTemp.getStatDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			tempMap.put(key, heatMapHistoryTemp);
		}
		return new ArrayList<HeatMapHistory>(tempMap.values());
	}


	@Override
	public Page<HeatMapSuspicious> getHeatMapSuspicious(HeatMapSuspicious heatMapSuspicious, Integer pageNo, Integer pageSize) {
		Page<HeatMapSuspicious> page = new Page<HeatMapSuspicious>(pageSize, pageNo);
		heatMapSuspicious.setPage(page);
		page.setList(heatmapDao.getHeatMapSuspicious(heatMapSuspicious));
		return page;
	}


	@Override
	public int updateSuspicious(HashMap<String, String> requestMap) {
		return heatmapDao.updateSuspicious(requestMap);
	}


	@Override
	public Page<HeatMapDetail> getHeatMapDetailByDay(HeatMapDetail heatMapDetail, Integer pageNo, Integer pageSize) {
		Page<HeatMapDetail> page = new Page<HeatMapDetail>(pageSize, pageNo);
		heatMapDetail.setPage(page);
		page.setList(heatmapDao.getHeatMapDetailByDayList(heatMapDetail));
		return page;
	}

	@Override
	public Page<HeatMapDetail> getHeatMapDetailByMonth(HeatMapDetail heatMapDetail, Integer pageNo, Integer pageSize) {
		Page<HeatMapDetail> page = new Page<HeatMapDetail>(pageSize, pageNo);
		heatMapDetail.setPage(page);
		page.setList(heatmapDao.getHeatMapDetailByMonthList(heatMapDetail));
		return page;
	}

	@Override
	public Page<HeatMapDetail> getHeatMapDetailByYear(HeatMapDetail heatMapDetail, Integer pageNo, Integer pageSize) {
		Page<HeatMapDetail> page = new Page<HeatMapDetail>(pageSize, pageNo);
		heatMapDetail.setPage(page);
		page.setList(heatmapDao.getHeatMapDetailByYearList(heatMapDetail));
		return page;
	}


	@Override
	public Page<ScanEndingImproves> getHeatMapCrowdQuery(CrowdQuery query, Integer pageNo, Integer pageSize) {
		Page<ScanEndingImproves> page = new Page<ScanEndingImproves>(pageSize, pageNo);
		query.setPhoneArea(Utils.parseArea(query.getPhoneArea()));
		query.setPage(page);
		page.setList(heatmapDao.findCowrdList(query));
		return page;
	}
}
