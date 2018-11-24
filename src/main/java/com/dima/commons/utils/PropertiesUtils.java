package com.dima.commons.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 静态读取properties配置文件
 * @author Administrator
 */
public class PropertiesUtils {
	
	private static Log log = LogFactory.getLog(PropertiesUtils.class);

	private static Map<String, PropertiesConfiguration> configMap = new HashMap<String, PropertiesConfiguration>();

	private static PropertiesConfiguration initConfigFile(String configFileName) throws ConfigurationException {
		PropertiesConfiguration propConfig = new PropertiesConfiguration();
		propConfig.setDelimiterParsingDisabled(true);
		propConfig.setFileName(configFileName);
		FileChangedReloadingStrategy reloadingStrategy = new FileChangedReloadingStrategy();
		reloadingStrategy.setRefreshDelay(30000);
		propConfig.setReloadingStrategy(reloadingStrategy);
		propConfig.load();
		configMap.put(configFileName, propConfig);
		return propConfig;
	}

	public static void setProperty(String configFileName, String key, String value) {
		PropertiesConfiguration propConfig = configMap.get(configFileName);
		try {
			if (propConfig == null) {
				propConfig = initConfigFile(configFileName);
			}

			propConfig.setProperty(key, value);
			propConfig.save();
		} catch (ConfigurationException ex) {
			log.error("save property value error,file name:" + configFileName + " key:" + key + " value:" + value, ex);
		}
	}

	public static String getProperty(String configFileName, String key) {
		PropertiesConfiguration propConfig = configMap.get(configFileName);
		String value = null;
		try {
			if (propConfig == null) {
				propConfig = initConfigFile(configFileName);
			}
			if (propConfig.containsKey(key)) {
				Object propValue = propConfig.getProperty(key);
				value = (String) propValue;
			}
		} catch (ConfigurationException ex) {
			log.error("get property value error,file name:" + configFileName + " key:" + key, ex);
		}

		return value;
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getProperties(String configFileName) {
		PropertiesConfiguration propConfig = configMap.get(configFileName);
		try {
			if (propConfig == null) {
				propConfig = initConfigFile(configFileName);
			}
		} catch (ConfigurationException ex) {
			log.error("init config file error,file name:" + configFileName, ex);
			return null;
		}
		Map<String, Object> propMap = new HashMap<String, Object>();
		Iterator iter = propConfig.getKeys();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			propMap.put(key, propConfig.getProperty(key));
		}
		return propMap;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<String> getKeys(String configFileName) {
		PropertiesConfiguration propConfig = configMap.get(configFileName);
		try {
			if (propConfig == null) {
				propConfig = initConfigFile(configFileName);
			}
		} catch (ConfigurationException ex) {
			log.error("init config file error,file name:" + configFileName, ex);
			return null;
		}
		
		List<String> propList = new ArrayList<String>();
		Iterator iter = propConfig.getKeys();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			propList.add(key);
		}
		return propList;
	}
	
}
