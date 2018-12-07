package com.dima.commons.learn.base64;

import java.io.IOException;

import org.junit.Test;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Title: JDKsunBase64
 * Description: JDK(sun.misc包)实现Base64编码解码
 * @author Dima
 * @date 2018年12月7日 下午10:13:26
 */
public class JDKsunBase64 {
	
	@Test
    public void test() throws IOException{
    	String sourceData = "这是Base64编码前的原始数据！！！";
        String encode = new BASE64Encoder().encode(sourceData.getBytes());
        System.out.println("编码结果：" + encode);//6L+Z5pivQmFzZTY057yW56CB5YmN55qE5Y6f5aeL5pWw5o2u77yB77yB77yB
        String decode = new String(new BASE64Decoder().decodeBuffer(encode));
        System.out.println("解码结果：" + decode);
    }
}
