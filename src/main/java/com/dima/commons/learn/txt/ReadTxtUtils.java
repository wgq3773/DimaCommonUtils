package com.dima.commons.learn.txt;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * Title: ReadTxtUtils
 * Description: 读取txt文件内容
 * @author Dima
 * @date 2018年12月7日 下午10:54:45
 */
public class ReadTxtUtils {
	public static void main(String[] args) throws Exception {
		String bankResult = readTxt("D:\\b.txt");
//		String bankResult = readTxt("D:\\b.properties");
		System.out.println(bankResult);
		
		Map<String, Object> resultMap = JSON.parseObject(bankResult, new TypeReference<Map<String, Object>>() {
		});
		System.out.println(resultMap);
		
		String transDetail = String.valueOf(resultMap.get("transDetail")).replace("[", "").replace("]", "");
		System.out.println(transDetail);
		
		Map<String, Object> transDetailMap = JSON.parseObject(transDetail, new TypeReference<Map<String,Object>>(){});
		System.out.println(transDetailMap);
		
//		Map<String, String> propertiesValue = getPropertiesValue("G:\\test\\rsatest.properties");
//		System.out.println(propertiesValue);
	}

	/**
	 * @Title: readTxt 
	 * @Description: 读取txt文件内容
	 * @param filePath
	 * @return
	 */
	public static String readTxt(String filePath) {// D:\\a.txt
		StringBuilder result = new StringBuilder();
		try {
//			BufferedReader bfr = new BufferedReader(new FileReader(new File(filePath)));
			BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8"));
			String lineTxt = null;
			while ((lineTxt = bfr.readLine()) != null) {
				result.append(lineTxt).append("\n");
			}
			bfr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	/**
	 * @Title: getPropertiesValue 
	 * @Description: 读取properties配置文件的内容到map集合中
	 * @param filePath
	 * @returnMap<String, String>
	 * @throws IOException
	 */
	public static Map<String, String> getPropertiesValue(String filePath) throws IOException {
		Map<String, String> returnMap = new HashMap<String, String>();
		Properties prop = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		prop.load(in);
		Iterator<String> it = prop.stringPropertyNames().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = prop.getProperty(key);
			returnMap.put(key, value);
		}
		in.close();
		return returnMap;
	}
}
