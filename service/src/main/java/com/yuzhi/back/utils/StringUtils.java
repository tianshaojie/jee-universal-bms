package com.yuzhi.back.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 
 * @author tianshaojie
 * 
 */
public class StringUtils {

    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");

    public static boolean isBlank(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static boolean isEquals(String s1, String s2) {
        return s1 == null && s2 == null?true:(s1 != null && s2 != null?s1.equals(s2):false);
    }

    public static boolean isInteger(String str) {
        return str != null && str.length() != 0?INT_PATTERN.matcher(str).matches():false;
    }

    public static int parseInteger(String str) {
        return !isInteger(str)?0: Integer.parseInt(str);
    }

    public static boolean isContains(String[] values, String value) {
        if(value != null && value.length() > 0 && values != null && values.length > 0) {
            String[] arr$ = values;
            int len$ = values.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String v = arr$[i$];
                if(value.equals(v)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isNumeric(String str) {
        if(str == null) {
            return false;
        } else {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if(!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }


    public static String[] split(String str, char ch) {
        ArrayList list = null;
        int ix = 0;
        int len = str.length();

        for(int i = 0; i < len; ++i) {
            char c = str.charAt(i);
            if(c == ch) {
                if(list == null) {
                    list = new ArrayList();
                }

                list.add(str.substring(ix, i));
                ix = i + 1;
            }
        }

        if(ix > 0) {
            list.add(str.substring(ix));
        }

        return list == null?EMPTY_STRING_ARRAY:(String[])((String[])list.toArray(EMPTY_STRING_ARRAY));
    }

    public static String join(String[] array) {
        if(array.length == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            String[] arr$ = array;
            int len$ = array.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String s = arr$[i$];
                sb.append(s);
            }

            return sb.toString();
        }
    }

    public static String join(String[] array, char split) {
        if(array.length == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < array.length; ++i) {
                if(i > 0) {
                    sb.append(split);
                }

                sb.append(array[i]);
            }

            return sb.toString();
        }
    }

    public static String join(String[] array, String split) {
        if(array.length == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < array.length; ++i) {
                if(i > 0) {
                    sb.append(split);
                }

                sb.append(array[i]);
            }

            return sb.toString();
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

}
