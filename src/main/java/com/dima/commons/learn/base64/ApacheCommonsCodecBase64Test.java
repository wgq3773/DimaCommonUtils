package com.dima.commons.learn.base64;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
/**
 * Title: ApacheCommonsCodecBase64Test
 * Description: Apache Commons Codec实现Base64编码解码
 * @author Dima
 * @date 2018年12月7日 下午10:11:55
 */
public class ApacheCommonsCodecBase64Test {
	
	@Test
	public void test3() {
		String sourceData = "这是Base64编码前的原始数据！！！";
		System.out.println("原始数据：" + sourceData);
		
		// 编码
		String encodeString = new String(new Base64().encode(sourceData.getBytes()));
		System.out.println("编码结果：" + encodeString);//6L+Z5pivQmFzZTY057yW56CB5YmN55qE5Y6f5aeL5pWw5o2u77yB77yB77yB

		// 解码
		String decodeString = new String(new Base64().decode(encodeString.getBytes()));
		System.out.println("解码结果：" + decodeString);
	}
}
