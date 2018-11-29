package com.dima.commons.utils;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public class CommonUtils {
	
	public static final String ENCODE_UTF8 = "UTF-8";

	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static String signWithMD5(String signData){
		return DigestUtils.md5Hex(signData);
	}
}
