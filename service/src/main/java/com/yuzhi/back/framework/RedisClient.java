package com.yuzhi.back.framework;

import com.yuzhi.back.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class RedisClient<T> {
    static Logger log = LoggerFactory.getLogger(RedisClient.class);
    @Resource(name="stringRedisTemplate")
    private StringRedisTemplate rt;
    long longtime = 7*60*60*24*1000;

    public T getRedis(String key, Class clazz) {
        try {
            ValueOperations<String, String> ops = rt.opsForValue();
            String json = ops.get(key);
            T t = (T) Utils.fromJson(json, clazz);
            log.info("getRedis, key=" + key + ", value=" + (t == null ? null : t.toString()));
            return t;
        } catch (Exception e) {
            log.error("getRedis error, key=" + key);
            return null;
        }
    }

    public boolean setRedis(String key, T t) {
        try {
            String json = Utils.toJson(t);
            ValueOperations<String, String> ops = rt.opsForValue();
            ops.set(key, json, longtime, TimeUnit.MILLISECONDS);
            log.info("setRedis, key="+key+", value=" + json);
            return true;
        } catch (Exception e) {
            log.error("setRedis error, key=" + key);
            return false;
        }
    }

    private long expire(Date expire) {
        return expire.getTime() - System.currentTimeMillis();
    }

    public boolean setRedis(String key, T t, Date expire) {
        try {
            String json = Utils.toJson(t);
            long expireMilliSeconds = expire(expire);
            ValueOperations<String, String> ops = rt.opsForValue();
            ops.set(key, json, expireMilliSeconds, TimeUnit.MILLISECONDS);
            log.info("setRedis, key=" + key + ", expire=" + expire+", value=" + json);
            return true;
        } catch (Exception e) {
            log.error("setRedis expire error, key=" + key + ", expire=" + expire + ", e=" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRedis(String key, T t) {
        log.info("updateRedis, key=" + key);
        try {
            T old = getRedis(key, t.getClass());
            if (old != null) {
                t = (T) Utils.fillObj(old, t);
                setRedis(key, t);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("updateRedis expire error, key=" + key);
            return false;
        }
    }

    public boolean updateRedis(String key, T t, Date expire) {
        log.info("updateRedis, key=" + key + ", expire=" + expire);
        try {
            T old = getRedis(key, t.getClass());
            if (old != null) {
                t = (T) Utils.fillObj(old, t);
                setRedis(key, t, expire);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("updateRedis expire error, key=" + key + ", expire=" + expire);
            return false;
        }
    }

    public boolean delRedis(String key) {
        log.info("delRedis, key=" + key);
        try {
            rt.delete(key);
            return true;
        } catch (Exception e) {
            log.error("delRedis error, key=" + key);
            return false;
        }
    }
}
