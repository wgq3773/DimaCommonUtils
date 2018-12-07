package com.dima.commons.learn.jsoup;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * Title: JsoupTest
 * Description: Jsoup解析HTML
 * @author Dima
 * @date 2018年12月7日 下午10:52:22
 */
public class JsoupTest {
	/**
	 * @Title: testXml 
	 * @Description: Jsoup解析HTML
	 */
	@Test
	public void testXml(){
		/**
		 * <form action="http://www.baidu.com" method="post">
		 * <input type="text" name="aaa1" value="1111111"/>
		 * <input type="text" name="aaa2" value="1111112"/>
		 * </form>
		 */
		String rspMsg = "<form action=\"http://www.baidu.com\" method=\"post\"><input type=\"text\" name=\"aaa1\" value=\"1111111\"/>";
		// 将html表单转为Document对象
		Document doc = Jsoup.parse(rspMsg);
		// 从Document中和获取元素对象
        Element payUrl = doc.getElementsByTag("form").get(0);
        // 从元素对象中获取值
        String action = payUrl.attr("action");
        System.out.println(action);
        
        Elements links = doc.getElementsByTag("input");
        Map<String, Object> htmlMap = new HashMap<String, Object>();
    	for (Element link : links) {
    		htmlMap.put(link.attr("name"), link.attr("value"));
    	}
    	System.out.println(htmlMap);
	}
}
