package com.dima.commons.utils;

import java.util.UUID;

public class CommonUtils {
	
	public static final String ENCODE_UTF8 = "UTF-8";

	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
