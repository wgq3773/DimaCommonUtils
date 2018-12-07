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

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * Title: CommonSoftRSAUtils
 * Description: RSA-S软证书通用工具类
 * @author Dima
 * @date 2018年12月7日 下午10:17:04
 */
public class CommonSoftRSAUtils {
		// 字符集
		public static final String CHARSET = "UTF-8";
		
		// 加密算法RSA
		public static final String KEY_ALGORITHM = "RSA";

		// 签名算法SHA256WithRSA
		public static final String SIGNATURE_ALGORITHM_SHA256 = "SHA256WithRSA";
		
		// 签名算法SHA1WithRSA
		public static final String SIGNATURE_ALGORITHM_SHA1 = "SHA1WithRSA";
		
		// 签名算法MD5withRSA
		public static final String SIGNATURE_ALGORITHM_MD5 = "MD5withRSA";

		// 获取公钥的key
		public static final String PUBLIC_KEY = "RSAPublicKey";

		// 获取私钥的key
		public static final String PRIVATE_KEY = "RSAPrivateKey";

		// RSA最大加密明文大小
		private static final int MAX_ENCRYPT_BLOCK = 116;

		// RSA最大解密密文大小
		private static final int MAX_DECRYPT_BLOCK = 128;

		/**
		 * @Title: signByPrivateKey 
		 * @Description: 私钥签名
		 * @param data
		 * @param privateKey
		 * @param signatureAlgorithmType
		 * @return
		 * @throws Exception
		 */
		public static String signByPrivateKey(String data, String privateKey, String signatureAlgorithmType) throws Exception {
			byte[] keyBytes = new BASE64Decoder().decodeBuffer(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Signature signature = Signature.getInstance(signatureAlgorithmType);
			signature.initSign(privateK);
			signature.update(data.getBytes(CHARSET));
			return new BASE64Encoder().encode(signature.sign()).replaceAll("\r", "").replaceAll("\n", "").replaceAll("\r\n", "");
		}
		
		/**
		 * @Title: signByPrivateKey 
		 * @Description: RSA-S私钥签名--默认使用MD5withRSA算法
		 * @param data
		 * @param privateKey
		 * @return
		 * @throws Exception
		 */
		public static String signByPrivateKey(String data, String privateKey) throws Exception {
			return signByPrivateKey(data, privateKey, CommonSoftRSAUtils.SIGNATURE_ALGORITHM_MD5);
		}

		/**
		 * @Title: validateSignByPublicKey 
		 * @Description: 公钥验签
		 * @param paramStr
		 * @param publicKey
		 * @param signedData
		 * @param signatureAlgorithmType
		 * @return
		 * @throws Exception
		 */
		public static boolean validateSignByPublicKey(String paramStr, String publicKey, String signedData, String signatureAlgorithmType) throws Exception {
			byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PublicKey publicK = keyFactory.generatePublic(keySpec);
			Signature signature = Signature.getInstance(signatureAlgorithmType);
			signature.initVerify(publicK);
			signature.update(paramStr.getBytes(CHARSET));
			return signature.verify(new BASE64Decoder().decodeBuffer(signedData));
		}
		
		/**
		 * @Title: validateSignByPublicKey 
		 * @Description: RSA-S公钥验签--默认使用MD5withRSA算法
		 * @param paramStr
		 * @param publicKey
		 * @param signedData
		 * @return
		 * @throws Exception
		 */
		public static boolean validateSignByPublicKey(String paramStr, String publicKey, String signedData) throws Exception {
			return validateSignByPublicKey(paramStr, publicKey, signedData, CommonSoftRSAUtils.SIGNATURE_ALGORITHM_MD5);
		}
		
		/**
		 * @Title: decryptByPrivateKey 
		 * @Description: 私钥解密
		 * @param encryptedData
		 * @param privateKey
		 * @return
		 * @throws Exception
		 */
		public static String decryptByPrivateKey(String encryptedData, String privateKey) throws Exception {
			byte[] keyBytes = new BASE64Decoder().decodeBuffer(privateKey);
			byte[] encryptedBytes = new BASE64Decoder().decodeBuffer(encryptedData);
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

		/**
		 * @Title: encryptByPublicKey 
		 * @Description: 公钥加密
		 * @param data
		 * @param publicKey
		 * @return
		 * @throws Exception
		 */
		public static String encryptByPublicKey(String data, String publicKey) throws Exception {
			byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey);
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
			return new BASE64Encoder().encode(encryptedData).replaceAll("\r", "").replaceAll("\n", "").replaceAll("\r\n", "");
		}
		
		/**
		 * @Title: genKeyPair 
		 * @Description: 生成密钥对(公钥和私钥)
		 * @return
		 * @throws Exception
		 */
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

		/**
		 * @Title: getPrivateKey 
		 * @Description: 获取私钥
		 * @param keyMap
		 * @return
		 * @throws Exception
		 */
		public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
			Key key = (Key) keyMap.get(PRIVATE_KEY);
			return new BASE64Encoder().encode(key.getEncoded()).replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
		}

		/**
		 * @Title: getPublicKey 
		 * @Description: 获取公钥
		 * @param keyMap
		 * @return
		 * @throws Exception
		 */
		public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
			Key key = (Key) keyMap.get(PUBLIC_KEY);
			return new BASE64Encoder().encode(key.getEncoded()).replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
		}
}
