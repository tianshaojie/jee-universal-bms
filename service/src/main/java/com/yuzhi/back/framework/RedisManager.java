package com.yuzhi.back.framework;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;

@Component
public class RedisManager {

    @Resource
    private JedisPool pool;

    /**
     * 通过key获取储存在redis中的value
     * 并释放连接
     * @param key
     * @return 成功返回value 失败返回null
     */
    public String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return value;
    }

    /**
     * 向redis存入key和value,并释放连接资源
     * 如果key已经存在 则覆盖
     * @param key
     * @param value
     * @return 成功 返回OK 失败返回 0
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return "0";
        } finally {
            returnResource(pool, jedis);
        }
    }

    /**
     * 删除指定的key,也可以传入一个包含key的数组
     * @param keys 一个key  也可以使 string 数组
     * @return 返回删除成功的个数
     */
    public Long del(String... keys) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.del(keys);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return 0L;
        } finally {
            returnResource(pool, jedis);
        }
    }

    /**
     * 判断key是否存在
     * @param key
     * @return true OR false
     */
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return false;
        } finally {
            returnResource(pool, jedis);
        }
    }


    /**
     * 返还到连接池
     *
     * @param pool
     * @param jedis
     */
    public static void returnResource(JedisPool pool, Jedis jedis) {
        if (jedis != null) {
            pool.returnResource(jedis);
        }
    }

    /**
     * get value from redis
     * @param key
     * @return
     */
    public byte[] get(byte[] key) {
        byte[] value = null;
        Jedis jedis = pool.getResource();
        try {
            value = jedis.get(key);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    /**
     * set
     * @param key
     * @param value
     * @return
     */
    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = pool.getResource();
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    /**
     * set
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public byte[] set(byte[] key, byte[] value, int expire) {
        Jedis jedis = pool.getResource();
        try {
            jedis.set(key, value);
            if (expire != 0) {
                jedis.expire(key, expire);
            }
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    public void setHash(String key,HashMap<String,String> map,int expire){
        Jedis jedis = pool.getResource();
        try{
            jedis.hmset(key, map);
            if (expire != 0) {
                jedis.expire(key, expire);
            }
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally{
            pool.returnResource(jedis);
        }
    }

    /**
     * del
     * @param key
     */
    public void del(byte[] key){
        Jedis jedis = pool.getResource();
        try{
            jedis.del(key);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally{
            pool.returnResource(jedis);
        }
    }

    /**
     * flush
     */
    public void flushDB() {
        Jedis jedis = pool.getResource();
        try {
            jedis.flushDB();
        } finally {
            pool.returnResource(jedis);
        }
    }

    /**
     * size
     */
    public Long dbSize() {
        Long dbSize = 0L;
        Jedis jedis = pool.getResource();
        try {
            dbSize = jedis.dbSize();
        } finally {
            pool.returnResource(jedis);
        }
        return dbSize;
    }

//    /**
//     * keys
//     *
//     * @param pattern
//     * @return
//     */
//    public Set<byte[]> keys(String pattern) {
//        Set<byte[]> keys = null;
//        Jedis jedis = pool.getResource();
//        try {
//            keys = jedis.keys(pattern.getBytes());
//        } finally {
//            pool.returnResource(jedis);
//        }
//        return keys;
//    }
    /*
    创建列表，并设置列表长度
     */
    public Long rpush(String key,String value){
        Long index=0L;
        Jedis jedis = pool.getResource();
        try {
            jedis.rpush(key,value);
        } finally {
            pool.returnResource(jedis);
        }
        return index;
    }
    /*
        设置列表(key)相应坐标(index)的value
    */
    public Long lset(String key,Long index,String value){
        Jedis jedis = pool.getResource();
        try {
            jedis.lset(key,index,value);
        } finally {
            pool.returnResource(jedis);
        }
        return index;
    }
    /*
        取指定列表指定坐标的value
     */
    public String lindex(String key,Long index){
        String value=null;
        Jedis jedis = pool.getResource();
        try {
            value =jedis.lindex(key,index);
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }
    /*
       返回指定列表的长度
    */
    public Long llen(String key){
        Long length=null;
        Jedis jedis = pool.getResource();
        try {
            length=jedis.llen(key);
        } finally {
            pool.returnResource(jedis);
        }
        return length;
    }
    /*
    删除列表
     */
    public  Long del(String key){
        Long length=null;
        Jedis jedis = pool.getResource();
        try {
            length=jedis.del(key);
        } finally {
            pool.returnResource(jedis);
        }
        return length;
    }


    public String hget(String key,String filed){
        String value=null;
        Jedis jedis = pool.getResource();
        try {
            value=jedis.hget(key,filed);
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    public Long hset(String key,String filed,String value){
        Long length=null;
        Jedis jedis = pool.getResource();
        try {
            length=jedis.hset(key, filed,value);
        } finally {
            pool.returnResource(jedis);
        }
        return length;
    }

}
