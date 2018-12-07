package com.dima.commons.learn.encrypt;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
/**
 * Title: RSAWithSoftCertificateUtils
 * Description: RSA-S软证书 	Apache Commons Codec实现Base64编码解码
 * @author Dima
 * @date 2018年12月7日 下午10:46:39
 */
public class RSAWithSoftCertificateUtils {
	//字符编码
		public static final String CHARSET = "UTF-8";

		//加密算法RSA
		public static final String KEY_ALGORITHM = "RSA";

		//签名算法:MD5withRSA
		public static final String MD5_WITH_RSA = "MD5withRSA";
		
		//签名算法:SHA1WithRSA
		public static final String SHA1_WITH_RSA = "SHA1WithRSA";
		
		//签名算法:SHA256WithRSA
		public static final String SHA256_WITH_RSA = "SHA256WithRSA";

		//获取公钥的key
		private static final String PUBLIC_KEY = "RSAPublicKey";

		//获取私钥的key
		private static final String PRIVATE_KEY = "RSAPrivateKey";

		//RSA最大加密明文大小
		private static final int MAX_ENCRYPT_BLOCK = 116;

		//RSA最大解密密文大小
		private static final int MAX_DECRYPT_BLOCK = 128;

		// 生成密钥对(公钥和私钥)
		public static Map<String, Object> genKeyPair() throws Exception {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			Map<String, Object> keyMap = new HashMap<String, Object>(2);
			keyMap.put(PUBLIC_KEY, publicKey);
			keyMap.put(PRIVATE_KEY, privateKey);
			return keyMap;
		}
		
		// 获取私钥
		public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
			Key key = (Key) keyMap.get(PRIVATE_KEY);
			String privateKey = new String(new Base64().encode(key.getEncoded())).replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
			return privateKey;
		}

		// 获取公钥
		public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
			Key key = (Key) keyMap.get(PUBLIC_KEY);
			String publicKey = new String(new Base64().encode(key.getEncoded())).replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
			return publicKey;
		}

		// 私钥解密  私钥(BASE64编码)
		public static String decryptByPrivateKey(String encryptedData, String privateKey) throws Exception {
			byte[] keyBytes = new Base64().decode(privateKey.getBytes());
			byte[] encryptedBytes = new Base64().decode(encryptedData.getBytes());
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateK);
			int inputLen = encryptedBytes.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedBytes, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedBytes, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return new String(decryptedData, CHARSET);
		}

		// 公钥加密  公钥(BASE64编码)
		public static String encryptByPublicKey(String data, String publicKey) throws Exception {
			byte[] keyBytes = new Base64().decode(publicKey.getBytes());
			byte[] dataBytes = data.getBytes(CHARSET);
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicK = keyFactory.generatePublic(x509KeySpec);
			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicK);
			int inputLen = dataBytes.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(dataBytes, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(dataBytes, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return new String(new Base64().encode(encryptedData)).replaceAll("\n", "").replaceAll("\r\n", "");
		}

		// 私钥签名  私钥(BASE64编码)
		public static String signByPrivateKey(String data, String privateKey, String signatureAlgorithm) throws Exception {
			byte[] keyBytes = new Base64().decode(privateKey.getBytes());
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Signature signature = Signature.getInstance(signatureAlgorithm);
			signature.initSign(privateK);
			signature.update(data.getBytes(CHARSET));
			return new String(new Base64().encode(signature.sign())).replaceAll("\n", "").replaceAll("\r\n", "");
		}
		
		public static String signByPrivateKey(String data, String privateKey) throws Exception {
			// 默认为MD5withRSA算法
			return signByPrivateKey(data, privateKey, RSAWithSoftCertificateUtils.MD5_WITH_RSA);
		}
		
		// 公钥验签  公钥(BASE64编码)
		public static boolean validateSignByPublicKey(String paramStr, String publicKey, String signedData, String signatureAlgorithm) throws Exception {
			byte[] keyBytes = new Base64().decode(publicKey.getBytes());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PublicKey publicK = keyFactory.generatePublic(keySpec);
			Signature signature = Signature.getInstance(signatureAlgorithm);
			signature.initVerify(publicK);
			signature.update(paramStr.getBytes(CHARSET));
			return signature.verify(new Base64().decode(signedData.getBytes()));
		}
		
		public static boolean validateSignByPublicKey(String paramStr, String publicKey, String signedData) throws Exception {
			// 默认为MD5withRSA算法
			return validateSignByPublicKey(paramStr,publicKey,signedData,RSAWithSoftCertificateUtils.MD5_WITH_RSA);
		}
}
