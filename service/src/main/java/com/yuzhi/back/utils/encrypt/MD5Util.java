package com.yuzhi.back.utils.encrypt;



import com.yuzhi.back.utils.Encodes;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

public class MD5Util {
	private static final String DEFAULT_HASH = "HmacSHA256";
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";
	public static final String KEY_MAC = "HmacMD5";
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	public static String sha256Hex(String signingKey, String stringToSign) {
        try {
            Mac mac = Mac.getInstance(DEFAULT_HASH);
            mac.init(new SecretKeySpec(signingKey.getBytes(DEFAULT_CHARSET), DEFAULT_HASH));
            byte[] result = mac.doFinal(stringToSign.getBytes(DEFAULT_CHARSET));
            return Encodes.encodeHex(result);
        } catch (Exception ex) {
        }
        return null;
    }
	public static String hmacMd5Hex(String signingKey, String stringToSign) {
		try {
			Mac mac = Mac.getInstance(KEY_MAC);
            mac.init(new SecretKeySpec(signingKey.getBytes(DEFAULT_CHARSET), KEY_MAC));
            byte[] result = mac.doFinal(stringToSign.getBytes(DEFAULT_CHARSET));
            return Encodes.encodeHex(result);
		} catch (Exception ex) {
		}
		return null;
	}
	public static String shaHex(String stringToSign) {
		try {
			MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
			sha.update(stringToSign.getBytes(DEFAULT_CHARSET));
			byte[] result =  sha.digest();
			return Encodes.encodeHex(result);
		} catch (Exception ex) {
		}
		return null;
	}
	public static String md5Hex(String stringToSign) {
		try {
			MessageDigest sha = MessageDigest.getInstance(KEY_MD5);
			sha.update(stringToSign.getBytes(DEFAULT_CHARSET));
			byte[] result =  sha.digest();
			return Encodes.encodeHex(result);
		} catch (Exception ex) {
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		//System.out.println(sha256Hex("12345", "abaaaaaaaaaaaaaaaaaaaaaacde"));
		//System.out.println(hmacMd5Hex("12345", "abaaaaaaaaaaaaaaaaaaaaaacde"));
		//System.out.println(shaHex("abaaaaaaaaaaaaaaaaaaaaaacde"));
		//System.out.println(md5Hex("abaaaaaaaaaaaaaaaaaaaaaacde"));
	}
	
	
}
