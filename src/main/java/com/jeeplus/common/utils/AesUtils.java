package com.jeeplus.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * AES加密解密工具类
 * @author LGT
 */
public class AesUtils {
	
	/**
	 * 加密实现
	 * @param content
	 * @param strKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(String content,String strKey ) {
	    SecretKeySpec skeySpec = getKey(strKey);
	    try {
	    	Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
		    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		    byte[] encrypted = cipher.doFinal(content.getBytes());
		    return  encrypted;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    return null;
	}
	
	
	/**
	 * 解密实现
	 * @param strKey
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(byte[] content,String strKey ) {
	    SecretKeySpec skeySpec = getKey(strKey);
	    try {
	    	Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
		    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		    byte[] original = cipher.doFinal(content);
		    String originalString = new String(original);
		    return originalString;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    return null;
	}

	
	/**
	 * 获取SecretKeySpec
	 * @param strKey
	 * @return
	 * @throws Exception
	 */
	private static SecretKeySpec getKey(String strKey) {
	    byte[] arrBTmp = strKey.getBytes();
	    byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）
	    for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
	        arrB[i] = arrBTmp[i];
	    }
	    SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
	    return skeySpec;
	}
	
	
	/**
	 * base64编码
	 * @param bytes 待编码的byte[]
	 * @return 编码后的base 64 code
	 */
	public static String base64Encode(byte[] bytes){
	    return new BASE64Encoder().encode(bytes);
	}

	
	/**
	 * base64解码
	 * @param base64Code 待解码的base 64 code
	 * @return 解码后的byte[]
	 * @throws Exception
	 */
	public static byte[] base64Decode(String base64Code) {
		try {
			return base64Code.isEmpty() ? null : new BASE64Decoder().decodeBuffer(base64Code);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    return null;
	}
	
	
	/**
	 * AES加密
	 * @param content 待加密的内容
	 * @param encryptKey 加密密钥
	 * @return 加密后的base 64 code
	 * @throws Exception
	 */
	public static String aesEncrypt(String content, String encryptKey) {
	    return base64Encode(encrypt(content, encryptKey));
	}
	
	
	/**
	 * AES解密
	 * @param encryptStr 待解密的base 64 code
	 * @param decryptKey 解密密钥
	 * @return 解密后的string
	 * @throws Exception
	 */
	public static String aesDecrypt(String encryptStr, String decryptKey) {
	    return encryptStr.isEmpty() ? null : decrypt(base64Decode(encryptStr), decryptKey);
	}
}
