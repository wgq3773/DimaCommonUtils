package com.dima.commons.learn.dom4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Title: XmlDeepToMap
 * Description: 
 * @author Dima
 * @date 2018年12月7日 下午10:56:40
 */
public class XmlDeepToMap {
	public static void main(String[] args) throws DocumentException {
		String xml = "<?xml version=\"1.0\" ?><com.bean.PayResponseMsg><ORDER_ID>20181025153649</ORDER_ID><aaa><bbb>fdffffffff</bbb><ccc><ddd>1245</ddd></ccc></aaa></com.bean.PayResponseMsg>";
		Map<String, String> xmlToMap = xmlDeepToMap(xml);
		System.out.println(xmlToMap);
	}

	/**
	 * @Title: xmlDeepToMap
	 * @Description: dom4j解析xml到map(包含子节点)
	 * @param xmlString
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlDeepToMap(String xmlString) throws DocumentException {
		Map<String, String> returnMap = new HashMap<String, String>();
		// 解析返回的xml字符串，生成document对象
		Document document = DocumentHelper.parseText(xmlString);
		// 根节点
		Element root = document.getRootElement();
		// 子节点
		List<Element> childElements = root.elements();
		// 遍历子节点
		returnMap = xmlDeepToMapGetAllElements(childElements, returnMap);
		return returnMap;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String> xmlDeepToMapGetAllElements(List<Element> childElements,
			Map<String, String> mapElement) {
		for (Element ele : childElements) {
			if (ele.elements().size() > 0) {
				mapElement = xmlDeepToMapGetAllElements(ele.elements(), mapElement);
			} else {
				mapElement.put(ele.getName(), ele.getText());
			}
		}
		return mapElement;
	}
}
