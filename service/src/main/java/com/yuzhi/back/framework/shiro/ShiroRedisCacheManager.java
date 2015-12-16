package com.yuzhi.back.framework.shiro;

import com.yuzhi.back.framework.RedisManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by tiansj on 15/8/10.
 */
public class ShiroRedisCacheManager implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(ShiroRedisCacheManager.class);

    private String keyPrefix = "shiro_redis_cache:";

    // fast lookup by name map
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    @Resource
    private RedisManager redisManager;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("获取名称为: " + name + " 的RedisCache实例");

        Cache c = caches.get(name);

        if (c == null) {
            // create a new cache instance
            c = new ShiroRedisCache<K, V>(redisManager, keyPrefix);

            // add it to the cache collection
            caches.put(name, c);
        }
        return c;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

}
