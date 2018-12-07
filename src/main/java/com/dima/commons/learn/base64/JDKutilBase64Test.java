package com.dima.commons.learn.base64;

import java.util.Base64;//注意：这是jdk8才有的

import org.junit.Test;

/**
 * Title: JDKutilBase64Test
 * Description: Java 8(java.util包)实现Base64编码解码
 * @author Dima
 * @date 2018年12月7日 下午10:14:56
 */
public class JDKutilBase64Test {
	@Test
	public void test2() throws Exception{
		String sourceData = "这是Base64编码前的原始数据！！！";
		byte[] bytes = sourceData.getBytes();
		
		// 编码
		String asB64 = Base64.getEncoder().encodeToString(bytes);
		System.out.println(asB64); //6L+Z5pivQmFzZTY057yW56CB5YmN55qE5Y6f5aeL5pWw5o2u77yB77yB77yB
		
		// 解码
		byte[] asBytes = Base64.getDecoder().decode(asB64);
		System.out.println(new String(asBytes, "utf-8")); //这是Base64编码前的原始数据！！！
		
	}
}
