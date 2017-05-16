package com.elwyn.modules.population.heatmap.websocket;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Name com.rainsoft.modules.population.heatmap.websocket.HeatMapScheduler
 * @Description
 * @Author Elwyn
 * @Version 2017/4/13
 * @Copyright 上海云辰信息科技有限公司
 **/
public class HeatMapScheduler {
	private static SchedulerFactory SCHEDULER_FACTORY;
	private static Scheduler scheduler;
	private static final Logger logger = LoggerFactory.getLogger(WebSocketSessionFactory.class);



	private HeatMapScheduler() {
		SCHEDULER_FACTORY = new StdSchedulerFactory();
	}

	private static final HeatMapScheduler HEAT_MAP_SCHEDULER = new HeatMapScheduler();

	public static HeatMapScheduler getInstance() {
		return HEAT_MAP_SCHEDULER;
	}

	static {
		try {
			scheduler = SCHEDULER_FACTORY.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}


	public void startJob() {
		try {
			//启动调度
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void stopJob() {
		try {
			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param jobName          任务名
	 * @param jobGroupName     任务组名
	 * @param triggerName      触发器名
	 * @param triggerGroupName 触发器组名
	 * @param jobClass         任务
	 * @Description: 添加一个定时任务
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void addJob(String jobName, String jobGroupName,
	                   String triggerName, String triggerGroupName, Class jobClass, String configId, String interval,String serviceCode) {
		try {
			String corn="0 */1 * * * ?";
			if(interval!=null){
				switch (interval) {
					case "1":
						corn = "0 */1 * * * ?";
						break;
					case "2":
						corn = "0 */2 * * * ?";
						break;
					case "5":
						corn = "0 */5 * * * ?";
						break;
					case "10":
						corn = "0 */10 * * * ?";
						break;
					default:
						corn = "0 */1 * * * ?";
				}
			}

			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
			// 获取trigger
			CronTrigger oldTtrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 不存在，创建一个
			if (null == oldTtrigger) {
				// 任务名，任务组，任务执行类,以及configId参数
				JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
				JobDataMap jobDataMap = jobDetail.getJobDataMap();
				if (configId != null) {
					jobDataMap.put("configId", configId);
				}
				if (interval != null) {
					jobDataMap.put("interval", interval);
				}
				if(serviceCode!=null){
					jobDataMap.put("serviceCode", serviceCode);
				}
				// 触发器
				TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
				// 触发器名,触发器组
				triggerBuilder.withIdentity(triggerName, triggerGroupName);
				triggerBuilder.startNow();
				// 触发器时间设定
				triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(corn));
				// 创建Trigger对象
				CronTrigger trigger = (CronTrigger) triggerBuilder.build();
				logger.debug("新增触发器" + trigger + "频率" + corn);
				// 调度容器设置JobDetail和Trigger
				scheduler.scheduleJob(jobDetail, trigger);

				// 启动
				if (!scheduler.isShutdown()) {
					scheduler.start();
					logger.debug("自动开启定时任务");
				}

			} else {
				//存在就修改时间
				logger.debug("修改触发时间" + triggerKey.getName() + triggerKey.getGroup() + corn);
				modifyJobTime(triggerKey.getName(), triggerKey.getGroup(), corn);
			}


		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * @param triggerName      触发器名
	 * @param triggerGroupName 触发器组名
	 * @param cron             时间设置，参考quartz说明文档
	 * @Description: 修改一个任务的触发时间
	 */
	public void modifyJobTime(
			String triggerName, String triggerGroupName, String cron) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(cron)) {
				// 触发器
				TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
				// 触发器名,触发器组
				triggerBuilder.withIdentity(triggerName, triggerGroupName);
				triggerBuilder.startNow();
				// 触发器时间设定
				triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
				// 创建Trigger对象
				trigger = (CronTrigger) triggerBuilder.build();
				scheduler.rescheduleJob(triggerKey, trigger);

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @Description: 移除一个任务
	 */
	public void removeJob(String jobName, String jobGroupName,
	                      String triggerName, String triggerGroupName) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
			logger.debug("删除任务" + triggerKey);
			scheduler.pauseTrigger(triggerKey);// 停止触发器
			scheduler.unscheduleJob(triggerKey);// 移除触发器
			scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
			logger.debug("移除任务" + jobName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
