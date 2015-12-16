package com.yuzhi.back.utils;

import com.alibaba.fastjson.JSONObject;
import com.yuzhi.back.utils.encrypt.Digests;
import com.yuzhi.back.utils.encrypt.MD5Util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类
 */
public class Utils {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 得到显示时间
     * @return   yyyy-MM-dd HH:mm:ss
     */
    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        return sdf.format(date);
    }

    /**
     * 根据显示时间得到日期
     * @param time  yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date stringToDate(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }


    /**
     * 将对象转成json串
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        if (obj == null)
            return null;
        JSONObject result = new JSONObject();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            // 字段名
            String key = fields[j].getName();
            // 字段类型
            String type = fields[j].getType().getName();
            try {
                // 字段值
                Object value = fields[j].get(obj);
                if (value != null) {
                    //如果是Date类型，转换成 yyyy-MM-dd HH:mm:ss
                    if (type.equalsIgnoreCase("java.util.Date")) {
                        value = Utils.dateToString((Date) value);
                    }
                    result.put(key, value.toString());
                } else {
                    //如果是Integer类型，转换成0
                    if (type.equalsIgnoreCase("java.lang.Integer") || type.equalsIgnoreCase("java.lang.Long")) {
                        value = 0;
                        result.put(key, value.toString());
                    }
                }
            } catch (Exception e) {
                System.out.println("object field transfer failed, but it`s ok. field = " + key + ", type = " + type);
            }
        }
        return JSONObject.toJSONString(result);
    }

    /**
     * 将json串转成对象
     * @param json
     * @param clazz
     * @return
     */
    public static Object fromJson(String json, Class clazz) {
        if (StringUtils.isNotEmpty(json)) {
            return JSONObject.parseObject(json, clazz);
        }
        return null;
    }

    /**
     * 将newObj中待更新的字段值，填充到oldObj中做替换
     * @param oldObj
     * @param newObj
     * @return  更新后的obj
     */
    public static Object fillObj(Object oldObj, Object newObj) {
        Field[] oldf = oldObj.getClass().getDeclaredFields();
        Field[] newf = newObj.getClass().getDeclaredFields();
        for (int j = 0; j < oldf.length; j++) {
            try {
                newf[j].setAccessible(true);
                if (newf[j].get(newObj) != null) {
                    oldf[j].setAccessible(true);
                    oldf[j].set(oldObj, newf[j].get(newObj));
                }
            } catch (IllegalAccessException e) {
                System.out.println("object field transfer failed, but it`s ok. field = " + newf[j].getName() + ", type = " + newf[j].getType().getName());
            }
        }
        return oldObj;
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

    public static void main(String[] args) {
        String salt = generateSalt();
        String password = encryptPassword(salt, "111111");

        System.out.println("salt="+salt);
        System.out.println("password="+password);
    }



}
