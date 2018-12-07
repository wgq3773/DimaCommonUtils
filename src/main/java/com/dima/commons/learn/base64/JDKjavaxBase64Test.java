package com.dima.commons.learn.base64;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;

/**
 * Title: JDKjavaxBase64Test
 * Description: JDK1.6(javax.xml包)实现Base64编码解码
 * @author Dima
 * @date 2018年12月7日 下午10:12:50
 */
public class JDKjavaxBase64Test {
	
	@Test
	public void testBase64ByJDK6() throws Exception{
		String sourceData = "这是Base64编码前的原始数据！！！";
		byte[] bytes = sourceData.getBytes();
		
		// Base64编码
		String base64Encode = DatatypeConverter.printBase64Binary(bytes);
		System.out.println(base64Encode);//6L+Z5pivQmFzZTY057yW56CB5YmN55qE5Y6f5aeL5pWw5o2u77yB77yB77yB
		
		// Base64解码
		byte[] parseBase64Binary = DatatypeConverter.parseBase64Binary(base64Encode);
		String base64Dncode = new String(parseBase64Binary, "utf-8");
		System.out.println(base64Dncode);//这是Base64编码前的原始数据！！！
		
	}
}
