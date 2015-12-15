package com.xuanniu.framework.utils.encrypt;


import com.xuanniu.framework.utils.Encodes;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESUtil {
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	public static String StrEncrypt(String content,String secret) {
		return StrEncrypt(content,secret,DEFAULT_CHARSET);
	}
	
	public static String StrDecrypt(String content, String secret) {
		return StrDecrypt(content,secret,DEFAULT_CHARSET);
	}
	
	public static String StrEncrypt(String content,String secret,String charset) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			//kgen.init(128, new SecureRandom(authorization.getBytes(charset)));
			SecureRandom secureRandom=
				      SecureRandom.getInstance("SHA1PRNG");
				      secureRandom.setSeed(secret.getBytes(charset));
				      kgen.init(128,secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes(charset);
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);// 加密
			return Encodes.encodeHex(result);
		} catch (Exception e) {
		}
		return null;
	}

	public static String StrDecrypt(String content, String secret,String charset){
		try {
			byte[] bytes = Encodes.decodeHex(content);
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			//kgen.init(128, new SecureRandom(authorization.getBytes(charset)));
			SecureRandom secureRandom=
				      SecureRandom.getInstance("SHA1PRNG");
				      secureRandom.setSeed(secret.getBytes(charset));
				      kgen.init(128,secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(bytes);// 加密
			return bytes == null ? null : new String(result, charset);
		} catch (Exception e) {
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		String authorization="123456";
		String s="0e251e8f76fa57f8394ca8ba70543bdb";
		//String s = StrEncrypt("22222", authorization);
		//System.out.println("httpEncrypt:"+s);
		s=StrDecrypt(s, authorization);
		System.out.println("decrypt:"+s);
		System.out.println("==================");
	}
}