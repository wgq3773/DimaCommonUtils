package com.dima.commons.utils;

import java.util.UUID;

public class CommonUtils {

	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
