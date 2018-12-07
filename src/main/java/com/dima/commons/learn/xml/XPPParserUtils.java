package com.dima.commons.learn.xml;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
maven依耐地址
<!-- https://mvnrepository.com/artifact/xpp3/xpp3 -->
<dependency>
    <groupId>xpp3</groupId>
    <artifactId>xpp3</artifactId>
    <version>1.1.4c</version>
</dependency>
 */
/**
 * Title: XPPParserUtils
 * Description: 深度解析xml工具类
 * @author Dima
 * @date 2018年12月7日 下午11:00:43
 */
public class XPPParserUtils {
	private static XmlPullParserFactory parserFactory = null;
	static {
		try {
			parserFactory = XmlPullParserFactory.newInstance();
			parserFactory.setNamespaceAware(true);
		} catch (XmlPullParserException e) {
			System.out.println("XPPParser initialize error!!");
		}
	}
	
	public static Map<String,String> parse(String strXML) {
		return parse(new StringReader(strXML));
	}
	
	/**
	 * 深度解析XML格式流，获取所有子元素名和值
	 * @param reader
	 * @return Map<String,String>
	 */
	public static Map<String,String> parse(Reader reader) {
		Map<String,String> map = new HashMap<String,String>();
		try {
			XmlPullParser xpp = parserFactory.newPullParser();
			xpp.setInput(reader);
			int eventType = xpp.getEventType();	
			String currentTag = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if(eventType == XmlPullParser.START_TAG) {
					currentTag = xpp.getName();
				} else if(eventType == XmlPullParser.TEXT) {
					map.put(currentTag, xpp.getText());
				}
				eventType = xpp.next();
			}			
		} catch (Exception ex) {
			System.out.println("xml parse exception:" + ex);
		}
		return map;
	}
}
