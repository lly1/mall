package com.mall.cache;

import com.mall.utils.cache.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;


public class CacheManagers extends RedisCacheManager {

    private static Logger logger = LoggerFactory.getLogger(CacheManagers.class);

    private static RedisUtils redisUtils;

    public CacheManagers(RedisOperations redisOperations) {
        super(redisOperations);
        redisUtils = new RedisUtils();
        redisUtils.setRedisTemplate((RedisTemplate) redisOperations);
    }

    @PostConstruct
    public void init() throws Exception {
        Long before = System.currentTimeMillis();
        System.out.println("开始初始化缓存");
        refreshCache();
        System.out.println("初始化缓存耗时:" + (System.currentTimeMillis() - before) + "ms");
    }

    //    刷新缓存
    public static void refreshCache() throws Exception {
        clearCache();
        initCache();
    }

    //      清空缓存，会全部刷新掉
    private static void clearCache() {
        redisUtils.flushDB();
    }

    //    初始化缓存
    public static void initCache() throws Exception {

    }


}
