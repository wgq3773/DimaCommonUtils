package com.dima.commons.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

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
	public static String getRequestParamts(HttpServletRequest request) {
		StringBuilder params = new StringBuilder("");
		Enumeration e = request.getHeaderNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value = request.getHeader(name);
			params.append(name).append("=").append(value);
			params.append("|");
		}
		return params.toString();
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
		String ua = request.getHeader("User-Agent").toLowerCase();
		if (ua.indexOf("micromessenger") > -1) {
			return true;// 微信
		}
		return false;// 非微信手机浏览器
	}
}
