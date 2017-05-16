package com.elwyn.modules.population.heatmap.websocket;

import org.apache.log4j.Logger;

import javax.websocket.Session;
import java.util.*;

/**
 * @Name com.rainsoft.modules.population.heatmap.websocket.WebSocketSessionFactory
 * @Description webSocket工具类
 * @Author Elwyn
 * @Version 2017/4/13
 * @Copyright 上海云辰信息科技有限公司
 **/
public class WebSocketSessionFactory {
	private static Logger logger = Logger.getLogger(WebSocketSessionFactory.class);
	private static Map<Session, String> sessionConfigIdMap = new HashMap<>();
	private static Map<String, Set<Session>> configIdSessionMap = new HashMap<>();
	private static Map<Session,String> sessionCornMap=new HashMap<>();//每个Session对应的刷新频率
	private static Map<Session,String> sessionServiceCodeMap=new HashMap<>();//每个session对应serviceCode
	private static Map<String, Set<Session>> serviceCodeSessionMap = new HashMap<>();//每个serviceCode对应多个Session

	private static  Queue<Map<String,Object>> queue = new LinkedList<>();

	private WebSocketSessionFactory() {
	}

	private static final WebSocketSessionFactory WEB_SOCKET_SESSION_FACTORY = new WebSocketSessionFactory();

	public static WebSocketSessionFactory getInstance() {
		return WEB_SOCKET_SESSION_FACTORY;
	}


	public static Map<Session, String> getSessionConfigIdMap() {
		return sessionConfigIdMap;
	}



	public static Map<String, Set<Session>> getConfigIdSessionMap() {
		return configIdSessionMap;
	}



	public static Queue<Map<String, Object>> getQueue() {
		return queue;
	}

	public static Map<String, Set<Session>> getServiceCodeSessionMap() {
		return serviceCodeSessionMap;
	}

	public static Map<Session, String> getSessionServiceCodeMap() {
		return sessionServiceCodeMap;
	}

	/**
	 * 一个configId对应多个Session 同时要存储Session对应哪个configID
	 *
	 * @param configId
	 * @param session
	 * @return
	 */
	public static void putConfigIdSessionMap(String configId, Session session) {
		Set<Session> sessions = configIdSessionMap.get(configId);
		if (sessions != null) {
			sessions.add(session);
		} else {
			Set<Session> sessions1 = new HashSet<>();
			sessions1.add(session);
			configIdSessionMap.put(configId, sessions1);
		}

		sessionConfigIdMap.put(session, configId);
		logger.debug("添加了configid" +configId+"对应session"+session.getId());
	}



	public static void removeHeatMapSession(Session session) {
		if (session == null) {
			logger.info("删除失败,参数错误");
			return;
		}
		String s = sessionConfigIdMap.get(session);
		if (s == null) {
			logger.debug("session没有存入sessionConfigIdMap中");
		} else {
			//删除configIdSessionMap存储的session
			Set<Session> sessions = configIdSessionMap.get(s);
			Iterator<Session> iterator = sessions.iterator();
			while (iterator.hasNext()) {
				Session session1 = iterator.next();
				if (session1.equals(session)) {
					iterator.remove();
					break;
				}
			}
			//删除sessionConfigIdMap 删除session对应的configId缓存信息
			sessionConfigIdMap.remove(session);
			logger.debug("删除session"+session.getId());
		}
	}

	//判断configId对应的session集合是否为空
	public static boolean isEmptySessionHeat(String configId){
		Set<Session> sessions = configIdSessionMap.get(configId);
		logger.debug("当前configId:"+configId+"对应集合size"+sessions.size());
		return !(sessions != null && sessions.size() > 0);


	}

	/**
	 * 添加session对应的刷新频率
	 * @param session
	 * @param corn
	 */
	public static void addSessionCornMap(Session session, String corn){
		sessionCornMap.put(session,corn);
	}

	/**
	 * 重点防范session
	 */
	public static void addEmpSession(Session session, String serviceCode){
		sessionServiceCodeMap.put(session,serviceCode);
		Set<Session> sessions = serviceCodeSessionMap.get(serviceCode);
		if (sessions != null) {
			sessions.add(session);
		} else {
			Set<Session> sessions1 = new HashSet<>();
			sessions1.add(session);
			serviceCodeSessionMap.put(serviceCode, sessions1);
		}
		logger.debug("添加了serviceCode" +serviceCode+"对应session"+session.getId());
	}



	public static void removeEmpSession(Session session) {
		if (session == null) {
			logger.info("删除失败,参数错误");
			return;
		}
		String s = sessionConfigIdMap.get(session);
		if (s == null) {
			logger.debug("session没有存入sessionConfigIdMap中");
		} else {
			//serviceCodeSessionMap存储的session
			Set<Session> sessions = serviceCodeSessionMap.get(s);
			Iterator<Session> iterator = sessions.iterator();
			while (iterator.hasNext()) {
				Session session1 = iterator.next();
				if (session1.equals(session)) {
					iterator.remove();
					break;
				}
			}
			//删除sessionServiceCodeMap 删除session对应的serviceCode缓存信息
			sessionServiceCodeMap.remove(session);
			logger.debug("删除重点防范session"+session.getId());
		}
	}


	//判断serviceCode对应的session集合是否为空
	public static boolean isEmptySessionEmp(String serviceCode){
		Set<Session> sessions = serviceCodeSessionMap.get(serviceCode);
		logger.debug("当前serviceCode:"+serviceCode+"对应集合size"+sessions.size());
		return !(sessions != null && sessions.size() > 0);


	}
}
