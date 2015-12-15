package com.xuanniu.framework.utils;

import com.xuanniu.framework.utils.encrypt.Digests;
import com.xuanniu.framework.utils.encrypt.MD5Util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author tianshaojie
 * 
 */
public class StringUtils {
	
	/**
s	 * @author tianshaojie
	 * @date 2010-11-11
	 * @discription : 为空返回true
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String toString(String[] strArray, String separator) {
		String strResult = "";
		if (strArray == null || separator == null) {
			strResult = null;
		} else {
			StringBuffer strBuffer = new StringBuffer();
			for (int i = 0; i < strArray.length; i++) {
				if (strArray[i] == null || strArray[i].length() == 0)
					continue;
				strBuffer.append(separator);
				strBuffer.append(strArray[i]);
			}
			if (strBuffer.length() > 0) {
				strBuffer.delete(0, separator.length());
			}
			strResult = strBuffer.toString();
		}
		return strResult;
	}

	public static String join(Object[] src, String separator, String quot, String defaultValue) {
		StringBuffer sb = new StringBuffer();
		if (src == null || src.length == 0) {
			return defaultValue;
		} else {
			for (int i = 0; i < src.length; i++) {
				if (sb.length() > 0) {
					sb.append(separator);
				}
				if (quot != null) {
					sb.append(quot);
				}
				sb.append(src[i]);
				if (quot != null) {
					sb.append(quot);
				}
			}
			return sb.toString();
		}
	}

	public static String join(List<String> list, String separator, String quot, String defaultValue) {
		StringBuffer sb = new StringBuffer();
		if (list == null || list.size() == 0) {
			return defaultValue;
		} else {
			for (String value : list) {
				if (sb.length() > 0 && separator != null) {
					sb.append(separator);
				}
				if (quot != null) {
					sb.append(quot);
				}
				sb.append(value);
				if (quot != null) {
					sb.append(quot);
				}
			}
			return sb.toString();
		}
	}

	public static String join(Collection<String> coll, String split) {
		if(coll.isEmpty()) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder();
			boolean isFirst = true;

			String s;
			for(Iterator i$ = coll.iterator(); i$.hasNext(); sb.append(s)) {
				s = (String)i$.next();
				if(isFirst) {
					isFirst = false;
				} else {
					sb.append(split);
				}
			}

			return sb.toString();
		}
	}

	public static String format(String pattern, Object value) {
		if (value == null) {
			if(pattern!=null&&pattern.indexOf("#,##0.00")!=-1){
				return new DecimalFormat(pattern).format(0);
			}
			return "";
		} else if (value instanceof Number) {
			return new DecimalFormat(pattern).format(value);
		} else if (value instanceof Date) {
			return new SimpleDateFormat(pattern).format(value);
		} else if (value instanceof Calendar) {
			return new SimpleDateFormat(pattern).format(((Calendar) value).getTime());
		} else {
			return value.toString();
		}
	}


    /**
     * 生成随机盐
     * @return
     */
    public static String generateSalt() {
        return Encodes.encodeHex(Digests.generateSalt(4));
    }

    /**
     * 密码加密
     * @param salt
     * @param password
     * @return
     */
    public static String encryptPassword(String salt, String password) {
        return MD5Util.sha256Hex(salt, password);
    }
}
