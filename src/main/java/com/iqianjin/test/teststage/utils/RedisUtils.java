package com.iqianjin.test.teststage.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Component
public class RedisUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtils.class);


    private static RedisTemplate redisTemplate;

    @Resource(type= RedisTemplate.class,name = "redisTemplate")
    private RedisTemplate redisTemplateAutowired;

    @PostConstruct
    private void init() {
        redisTemplate = this.redisTemplateAutowired;
    }

    /**
     *  放入String类型缓存数据,带过期时间
     * @param key
     * @param data
     * @param overtime
     * @param timeUnit
     */
    public static void setRedisValueByKey(String key, String data, long overtime, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, data, overtime, timeUnit);
            LOGGER.info("放入缓存数据:{},{},{},{}", key, data, overtime, timeUnit.name());
        } catch (Exception e) {
            LOGGER.error("redis放入缓存数据异常异常{}.", key, e);
        }
    }


    /**
     * hset操作
     * @param key
     * @param field
     * @param value
     */
    public static void hsetKeyValue(String key, String field, String value) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
            LOGGER.info("放入缓存数据:key:{},field:{},value:{}", key, field, value);
        }catch (Exception e) {
            LOGGER.error("redis hset {}放入缓存数据异常异常{}.", key, e);
        }
    }

    /**
     * zadd操作
     * @param key
     * @param value
     * @param score
     */
    public static void zaddKeyValue(String key, String value, Double score) {
        try {
            redisTemplate.opsForZSet().add(key, value, score);
            LOGGER.info("放入缓存数据:key:{},value:{},score:{}", key, value, score);
        }catch (Exception e) {
            LOGGER.error("redis zadd {}放入缓存数据异常异常{}.", key, e);
        }
    }


    /**
     * 删除某个ZSet中的一个成员
     * @param key
     * @param value
     */
    public static void zremValueInZSet(String key, String value) {
        try {
            redisTemplate.opsForZSet().remove(key, value);
        }catch (Exception e) {
            LOGGER.error("redis zrem  Zset格式的{}中删除缓存数据{}异常{}.", key, value, e);
        }
    }

    /**
     * 替换key的name
     * @param oldKey
     * @param newKey
     */
    public static void renameOldKey(String oldKey, String newKey) {
        try {
            redisTemplate.rename(oldKey, newKey);
            LOGGER.info("修改oldKey:{}为newKey:{}", oldKey, newKey);
        }catch (Exception e){
            LOGGER.error("redis rename缓存数据异常异常{}.", e);
        }
    }

    /**
     * 根据key获取缓存数据
     * @param key
     * @return
     */
    public static String getRedisValueByKey(String key) {
        try {
            Object dataString = redisTemplate.opsForValue().get(key);
            if (dataString != null) {
                return dataString.toString();
            }
        } catch (Exception e) {
            LOGGER.error("getRedisValueByKey获取缓存数据异常异常{}.", key, e);
        }
        return null;
    }


    /**
     *
     * @param lockName	key
     * @param expire
     * @return 0 说明key已经存在;1 说明放入key成功
     */
    public static long acquireLock(final String lockName, final long expire){
        return (long) redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) {
                byte[] lockBytes = redisTemplate.getStringSerializer().serialize(lockName);
                boolean locked = connection.setNX(lockBytes, lockBytes);
                connection.expire(lockBytes, expire);
                if(locked){
                    return 1L;
                }
                return 0L;
            }
        });
    }

    /**
     * 清除缓存
     *
     * @param key
     */
    public static void delRedisByKey(String key) {
        try {
            if (key == null) {
                return;
            }
            redisTemplate.delete(key);
        } catch (Exception e) {
            LOGGER.error("delRedisByKey exception,key={}", key, e);
        }
    }

    public static Boolean exists(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            LOGGER.error("exists操作异常：{}", e);
            return Boolean.FALSE;
        }
    }
    /**
     * 设置key对应的value，永久有效
     *
     * @param key
     * @param value
     */
    public static void setRedisValueByKeyForever(String key, String value) {
        try {
            if (value != null && !"".equals(value)) {
                redisTemplate.opsForValue().set(key, value);
            }
        } catch (Exception e) {
            LOGGER.error("redis.setRedisValueByKey放入缓存数据异常异常{}.", key, e);
        }
    }

    /**
     * Lpop KEY_NAME 用于移除并返回列表的第一个元素
     * @param key
     */
    public static void leftPopKey(String key) {
        try {
            redisTemplate.opsForList().leftPop(key);
            LOGGER.info("在新redis: 操作 lpop， key = {}", key);
        }catch (Exception e) {
            LOGGER.error("r在新redis: 操作 lpop 放入缓存数据{}异常，异常{}.", key, e);
        }
    }

    /**
     * Rpop KEY_NAME 用于移除并返回列表的第一个元素
     * @param key
     */
    public static void rightPopKey(String key) {
        try {
            redisTemplate.opsForList().rightPop(key);
            LOGGER.info("在新redis: 操作 rpop， key = {}", key);
        }catch (Exception e) {
            LOGGER.error("在新redis: 操作 rpop 放入缓存数据{}异常，异常{}.", key, e);
        }
    }

    /**
     * Lpush KEY_NAME value 将一个或多个值插入到列表头部
     * @param key
     * @param value
     */
    public static void leftPushKeyValue(String key, String value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            LOGGER.info("在新redis: 操作 lpush， key = {}, value = {}", key, value);
        }catch (Exception e) {
            LOGGER.error("在新redis: 操作 rpop 放入缓存数据key:{}, value:{} 异常，异常{}", key, value ,e);
        }
    }

    /**
     * Rpush KEY_NAME value 在列表中添加一个或多个值
     * @param key
     * @param value
     */
    public static void rightPushKeyValue(String key, String value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            LOGGER.info("在新redis: 操作 rpush， key = {}, value = {}", key, value);
        }catch (Exception e) {
            LOGGER.error("在新redis: 操作 rpop 放入缓存数据key:{}, value:{} 异常，异常{}", key, value ,e);
        }
    }

    /**
     *LSET key index value  通过索引设置列表元素的值。
     * @param key
     * @param value
     * @param index
     */
    public static void leftSetValueByIndex(String key, String value, Long index) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            LOGGER.info("在新redis: 操作 lset， key = {}, value = {}, index = {}", key, value, index);
        }catch (Exception e) {
            LOGGER.error("在新redis: 操作 lset 放入缓存数据key:{}, value:{} , index: {} 异常，异常{}", key, value, index, e);
        }
    }
}
