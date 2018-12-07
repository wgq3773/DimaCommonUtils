package com.dima.commons.learn.encrypt;

import java.util.Map;

public class CommonSoftRSAUtilsTest {

	public static void main(String[] args) throws Exception {
		String data = "这是原始数据！！！";
		Map<String, Object> genKeyPair = CommonSoftRSAUtils.genKeyPair();
		String privateKey = CommonSoftRSAUtils.getPrivateKey(genKeyPair);
		String publicKey = CommonSoftRSAUtils.getPublicKey(genKeyPair);
		System.out.println("私钥：" + privateKey);
		System.out.println("公钥：" + publicKey);
		
		String sign = CommonSoftRSAUtils.signByPrivateKey(data, privateKey)/*.replaceAll("\r", "").replaceAll("\r\n", "")*/;
		System.out.println("签名：" + sign);
		
		boolean validateSignByPublicKey = CommonSoftRSAUtils.validateSignByPublicKey(data, publicKey, sign);
		System.out.println("验签结果：" + validateSignByPublicKey);
		
		String encryptByPublicKey = CommonSoftRSAUtils.encryptByPublicKey(data, publicKey);
		System.out.println("公钥加密结果：" + encryptByPublicKey);
		String decryptByPrivateKey = CommonSoftRSAUtils.decryptByPrivateKey(encryptByPublicKey, privateKey);
		System.out.println("私钥解密结果：" + decryptByPrivateKey);
	}
}
