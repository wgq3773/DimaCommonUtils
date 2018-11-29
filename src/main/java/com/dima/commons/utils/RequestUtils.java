package com.dima.commons.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtils {
	
	private static Logger log = LoggerFactory.getLogger(RequestUtils.class);
	
	/**
	 * 根据用户请求的IP地址和user-agent生成用户ID
	 * @param request
	 * @return
	 */
	public static String generateUserIdByIpUserAgent(HttpServletRequest request){
		String ipAddress = RequestUtils.getIpAddress(request);
		Map<String, String> requestParamts = RequestUtils.getRequestParamts(request);
		String originalUserId = ipAddress + requestParamts.get("user-agent");
		String userId = CommonUtils.signWithMD5(originalUserId);
		return userId;
	}
	
	/**
	 * 以key-value形式提取请求数据到可读写的map集合中
	 * <p>Title: reqMapToMap</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map reqMapToMap(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		// 提出Map值到另一map
		Map tmpRstMap = new HashMap();
		Set tmpKeySet = map.keySet();
		for (Iterator tmpIt = tmpKeySet.iterator(); tmpIt.hasNext();) {
			Object tmpKey = tmpIt.next();
			Object tmpVal = map.get(tmpKey);
			if (tmpVal instanceof Object[]) {
				tmpVal = ((Object[]) tmpVal)[0];
			}
			tmpRstMap.put(tmpKey, tmpVal);
		}
		return tmpRstMap;
	}
	
	/**
	 * 以数据流的方式接收请求数据
	 * <p>Title: getStreamData</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 */
	public static String getStreamData(HttpServletRequest request){
		try {
			StringBuffer sb = new StringBuffer();
			BufferedReader bfr = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line = null;
			while((line = bfr.readLine()) != null){
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			log.error("parse request data to json error.",e);
		}
		return null;
	}

	/**
	 * <p>Title: getIpAddress</p>
	 * <p>Description: 获取IP地址</p>
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * <p>
	 * Title: getRequestParamts
	 * </p>
	 * <p>
	 * Description: 从HttpServletRequest中获取请求头信息用于日志打印
	 * </p>
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getRequestParamts(HttpServletRequest request) {
		Map<String, String> returnMap = new HashMap<String, String>();
		Enumeration e = request.getHeaderNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value = request.getHeader(name);
			returnMap.put(name, value);
		}
		return returnMap;
	}

	/**
	 * <p>
	 * Title: isFromMobile
	 * </p>
	 * <p>
	 * Description: 根据 Agent判断是否是智能手机
	 * </p>
	 * 
	 * @param request
	 * @return
	 */
	public static Boolean isFromMobile(HttpServletRequest request) {
		try {
			Boolean flag = false;
			String agent = request.getHeader("user-agent");
			String[] keywords = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" };
			// 排除 Windows 桌面系统
			if (!agent.contains("Windows NT")
					|| (agent.contains("Windows NT") && agent.contains("compatible; MSIE 9.0;"))) {
				// 排除 苹果桌面系统
				if (!agent.contains("Windows NT") && !agent.contains("Macintosh")) {
					for (String item : keywords) {
						if (agent.contains(item)) {
							flag = true;
							break;
						}
					}
				}
			}
			return flag;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * <p>
	 * Title: isWechat
	 * </p>
	 * <p>
	 * Description: 判断是否微信浏览器
	 * </p>
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isWechat(HttpServletRequest request) {
		Boolean fromMobile = RequestUtils.isFromMobile(request);
		if (!fromMobile) {// 不是来自手机
			return false;
		}
		String ua = request.getHeader("User-Agent").toLowerCase();
		if (ua.indexOf("micromessenger") > -1) {
			return true;// 微信手机浏览器
		}
		return false;// 非微信手机浏览器
	}
}
