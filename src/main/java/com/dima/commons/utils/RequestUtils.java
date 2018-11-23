package com.dima.commons.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

	/**
	 * 从HttpServletRequest中获取请求头信息用于日志打印
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
}
