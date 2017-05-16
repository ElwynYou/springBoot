package com.elwyn.bigData.modules.population.crowd.service.oracle;

import com.rainsoft.core.paging.Page;
import com.rainsoft.modules.population.crowd.dao.EmphasisAreaDao;
import com.rainsoft.modules.population.crowd.dao.EmphasisAreaRefreshDao;
import com.rainsoft.modules.population.crowd.entity.EmphasisAreaDetail;
import com.rainsoft.modules.population.crowd.entity.EmphasisAreaHistory;
import com.rainsoft.modules.population.crowd.entity.EmphasisAreaSuspicious;
import com.rainsoft.modules.population.crowd.entity.EmphasisRefresh;
import com.rainsoft.modules.population.crowd.runner.*;
import com.rainsoft.modules.population.crowd.service.IEmphasisAreaService;
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

/**
 * @Name com.rainsoft.modules.population.crowd.service.CrowdManageServiceImpl
 * @Description
 * @Author xa
 * @Version 2017年3月29日 下午5:06:59
 * @Copyright 上海云辰信息科技有限公司
 */

@Service
public class EmphasisAreaServiceImpl implements IEmphasisAreaService {
	@Autowired
	protected EmphasisAreaDao dao;

	@Autowired
	private EmphasisAreaRefreshDao emphasisAreaRefreshDao;

	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	private Map<String, EmphasisAreaHistory> assembleMap(DateParm dp) {
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
		Map<String, EmphasisAreaHistory> hm = new LinkedHashMap<String, EmphasisAreaHistory>();
		while (tempDate != maxDate + 1) {
			int saveDate = 0;
			if (!dp.queryTimeType.equals("day")) {
				saveDate = tempDate + 1;
			} else {
				saveDate = tempDate;
			}
			cal.set(dp.calendarType, saveDate);
			EmphasisAreaHistory hh = new EmphasisAreaHistory();
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
	public List<EmphasisAreaHistory> getEmphasisAreaHistory(EmphasisAreaHistory emphasisAreaHistory) {
		List<EmphasisAreaHistory> emphasisAreaHistoryList = null;
		Map<String, EmphasisAreaHistory> tempMap = null;
		String showDateFromat = null;
		DateParm dp = new DateParm();
		if (emphasisAreaHistory.getQueryTimeType().equals(EmphasisAreaHistory.QUERY_TIME_TYPE_DAY)) {
			emphasisAreaHistoryList = dao.getEmphasisAreaHistoryListByDay(emphasisAreaHistory);
			dp.calendarType = Calendar.HOUR_OF_DAY;
			dp.queryDateFromat = "yyyy-MM-dd";
			showDateFromat = dp.showDateFromat = "yyyy-MM-dd HH";
			dp.queryTime = emphasisAreaHistory.getQueryTime();
			dp.queryTimeType = EmphasisAreaHistory.QUERY_TIME_TYPE_DAY;
			tempMap = assembleMap(dp);
		}
		if (emphasisAreaHistory.getQueryTimeType().equals(EmphasisAreaHistory.QUERY_TIME_TYPE_MONTH)) {
			emphasisAreaHistoryList = dao.getEmphasisAreaHistoryListByMonth(emphasisAreaHistory);
			dp.calendarType = Calendar.DAY_OF_MONTH;
			dp.queryDateFromat = "yyyy-MM";
			showDateFromat = dp.showDateFromat = "yyyy-MM-dd";
			dp.queryTime = emphasisAreaHistory.getQueryTime();
			dp.queryTimeType = EmphasisAreaHistory.QUERY_TIME_TYPE_MONTH;
			tempMap = assembleMap(dp);
		}
		if (emphasisAreaHistory.getQueryTimeType().equals(EmphasisAreaHistory.QUERY_TIME_TYPE_YEAR)) {
			emphasisAreaHistoryList = dao.getEmphasisAreaHistoryListByYear(emphasisAreaHistory);
			dp.calendarType = Calendar.MONTH;
			dp.queryDateFromat = "yyyy";
			showDateFromat = dp.showDateFromat = "yyyy-MM";
			dp.queryTime = emphasisAreaHistory.getQueryTime();
			dp.queryTimeType = EmphasisAreaHistory.QUERY_TIME_TYPE_YEAR;
			tempMap = assembleMap(dp);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(showDateFromat);
		for (EmphasisAreaHistory emphasisAreaHistoryTemp : emphasisAreaHistoryList) {
			String key = null;
			try {
				key = sdf.format(sdf.parse(emphasisAreaHistoryTemp.getStatDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			tempMap.put(key, emphasisAreaHistoryTemp);
		}
		return new ArrayList<EmphasisAreaHistory>(tempMap.values());
	}


	@Override
	public Page<EmphasisAreaSuspicious> getEmphasisAreaSuspicious(EmphasisAreaSuspicious emphasisAreaSuspicious, Integer pageNo, Integer pageSize) {
		Page<EmphasisAreaSuspicious> page = new Page<EmphasisAreaSuspicious>(pageSize, pageNo);
		emphasisAreaSuspicious.setPage(page);
		page.setList(dao.getEmphasisAreaSuspicious(emphasisAreaSuspicious));
		return page;
	}

	@Override
	public int updateSuspicious(HashMap<String, String> requestMap) {
		return dao.updateSuspicious(requestMap);
	}

	@Override
	public Page<EmphasisAreaDetail> getEmphasisAreaDetailByDay(EmphasisAreaDetail emphasisAreaDetail, Integer pageNo,
	                                                           Integer pageSize) {
		Page<EmphasisAreaDetail> page = new Page<EmphasisAreaDetail>(pageSize, pageNo);
		emphasisAreaDetail.setPage(page);
		page.setList(dao.getEmphasisAreaDetailByDayList(emphasisAreaDetail));
		return page;
	}

	@Override
	public Page<EmphasisAreaDetail> getEmphasisAreaDetailByMonth(EmphasisAreaDetail emphasisAreaDetail, Integer pageNo,
	                                                             Integer pageSize) {
		Page<EmphasisAreaDetail> page = new Page<EmphasisAreaDetail>(pageSize, pageNo);
		emphasisAreaDetail.setPage(page);
		page.setList(dao.getEmphasisAreaDetailByMonthList(emphasisAreaDetail));
		return page;
	}


	@Override
	public Page<EmphasisAreaDetail> getEmphasisAreaDetailByYear(EmphasisAreaDetail emphasisAreaDetail, Integer pageNo,
	                                                            Integer pageSize) {
		Page<EmphasisAreaDetail> page = new Page<EmphasisAreaDetail>(pageSize, pageNo);
		emphasisAreaDetail.setPage(page);
		page.setList(dao.getEmphasisAreaDetailByYearList(emphasisAreaDetail));
		return page;
	}

	@Override
	public Page<ScanEndingImproves> getEmphasisAreaCrowdQuery(CrowdQuery query, Integer pageNo, Integer pageSize) {
		Page<ScanEndingImproves> page = new Page<ScanEndingImproves>(pageSize, pageNo);
		query.setPhoneArea(Utils.parseArea(query.getPhoneArea()));
		query.setPage(page);
		page.setList(dao.findCowrdList(query));
		return page;
	}

	@Override
	public List<EmphasisRefresh> initEmphasisAreaIndex(String init, String interval) {
		Map<String, Object> time;
		if (init != null) {
			time = emphasisAreaRefreshDao.getMaxTime(null);
			return emphasisAreaRefreshDao.initEmphasisAreaIndex(time);
		} else {
			time = emphasisAreaRefreshDao.getMaxTime(interval);
			return emphasisAreaRefreshDao.initEmphasisAreaIndex(time);
		}
	}

	@Override
	public Map<String, Object> initEmphasisAreaDetail(String init, String interval, String serviceCode) {


		if (init != null) {
			try {
				Map<String, Object> time = emphasisAreaRefreshDao.getMaxTime(null);
				Set<String> machineIds = emphasisAreaRefreshDao.getMachineIdsByserviceCode(serviceCode);
				time.put("machineIds", machineIds);
				time.put("serviceCode", serviceCode);
				time.put("init", init);
				List<EmphasisRefresh> initDangerAreaCount = emphasisAreaRefreshDao.initDangerAreaCount(time);
				List<EmphasisRefresh> initDangerPersonCount = emphasisAreaRefreshDao.initDangerPersonCount(time);
				List<Map<String, Object>> initTotalPerson = emphasisAreaRefreshDao.initTotalPerson(time);
				String beginTime = String.valueOf(time.get("beginTime"));
				String endTime = String.valueOf(time.get("endTime"));
				Map<String, Object> timeMap = new HashMap<>();
				timeMap.put("machineIds",machineIds);
				timeMap.put("beginTime", DateUtil.getDateByString(beginTime, "yyyy-MM-dd HH:mm", -1, Calendar.DATE));
				timeMap.put("endTime", DateUtil.getDateByString(endTime, "yyyy-MM-dd HH:mm", -1, Calendar.DATE));
				List<Map<String, Object>> initTotalPersonPreDay = emphasisAreaRefreshDao.initTotalPerson(timeMap);
				timeMap.put("beginTime", DateUtil.getDateByString(beginTime, "yyyy-MM-dd HH:mm", -7, Calendar.DATE));
				timeMap.put("endTime", DateUtil.getDateByString(endTime, "yyyy-MM-dd HH:mm", -7, Calendar.DATE));
				List<Map<String, Object>> initTotalPersonPreWeek = emphasisAreaRefreshDao.initTotalPerson(timeMap);
				timeMap.put("beginTime", DateUtil.getDateByString(beginTime, "yyyy-MM-dd HH:mm", -7, Calendar.YEAR));
				timeMap.put("endTime", DateUtil.getDateByString(endTime, "yyyy-MM-dd HH:mm", -7, Calendar.YEAR));
				List<Map<String, Object>> initTotalPersonPreYear = emphasisAreaRefreshDao.initTotalPerson(timeMap);
				List<EmphasisRefresh> initSuspiciousPeople = emphasisAreaRefreshDao.initSuspiciousPeople(time);
				List<EmphasisRefresh> empArea = emphasisAreaRefreshDao.emphasisArea(time);
				Map<String, Object> resultData = new HashMap<>();
				resultData.put("totalPerson", initTotalPerson);
				resultData.put("dangerAreaCount", initDangerAreaCount);
				resultData.put("dangerPersonCount", initDangerPersonCount);
				resultData.put("suspiciousPeople", initSuspiciousPeople);
				resultData.put("totalPersonPreDay", initTotalPersonPreDay);
				resultData.put("totalPersonPreWeek", initTotalPersonPreWeek);
				resultData.put("totalPersonPreYear", initTotalPersonPreYear);
				resultData.put("empArea", empArea);
				resultData.putAll(time);
				resultData.put("initdata", "init");
				return resultData;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			Set<String> machineIds = emphasisAreaRefreshDao.getMachineIdsByserviceCode(serviceCode);
			try {
				Map<String, Object> resultData = emphasisAreaRefreshDao.getMaxTime(interval);
				resultData.put("machineIds", machineIds);
				resultData.put("serviceCode", serviceCode);
				cachedThreadPool.execute(new EmpTotalPersonRunner(resultData));
				cachedThreadPool.execute(new EmpDangerAreaCountRunner(resultData));
				cachedThreadPool.execute(new EmpDangerPersonCountRunner(resultData));
				cachedThreadPool.execute(new EmpSuspiciousPeopleRunner(resultData));
				cachedThreadPool.execute(new EmpAreaRunner(resultData));
				while (true) {
					if (resultData.containsKey("totalPerson")
							&& resultData.containsKey("dangerAreaCount") && resultData.containsKey("dangerPersonCount")
							&& resultData.containsKey("suspiciousPeople") && resultData.containsKey("totalPersonPreDay")
							&& resultData.containsKey("totalPersonPreWeek") && resultData.containsKey("totalPersonPreYear")
							&& resultData.containsKey("empArea")
							) {
						return resultData;
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}

		}

	}
}
