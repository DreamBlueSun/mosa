package com.mhb.mosa.redis.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@Component
public class RedisClient implements RedisClientInterface {
    @Autowired
    private JedisPool jedisPool;

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    @Override
    public void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    @Override
    public String set(String key, String value, Jedis jedis) {
        return jedis.set(key, value);
    }

    @Override
    public String set(String key, String value, String nxxx, String expx, long time, Jedis jedis) {
        return jedis.set(key, value, nxxx, expx, time);
    }

    @Override
    public String get(String key, Jedis jedis) {
        return jedis.get(key);
    }

    @Override
    public Long hSet(String key, String field, String value, Jedis jedis) {
        return jedis.hset(key, field, value);
    }

    @Override
    public String hGet(String key, String field, Jedis jedis) {
        return jedis.hget(key, field);
    }

    @Override
    public Map<String, String> hGetAll(String key, Jedis jedis) {
        return jedis.hgetAll(key);
    }

    @Override
    public Boolean hexists(String key, String field, Jedis jedis) {
        return jedis.hexists(key, field);
    }

    @Override
    public Long expire(String key, int seconds, Jedis jedis) {
        return jedis.expire(key, seconds);
    }

    @Override
    public Long del(String key, Jedis jedis) {
        return jedis.del(key);
    }

    @Override
    public Long hDel(String key, String field, Jedis jedis) {
        return jedis.hdel(key, field);
    }

    @Override
    public Long hDel(String key, String[] fields, Jedis jedis) {
        return jedis.hdel(key, fields);
    }

    @Override
    public Long lpush(String key, String value, Jedis jedis) {
        return jedis.lpush(key, value);
    }

    @Override
    public Long lpush(String key, List<String> stringList, Jedis jedis) {
        return jedis.lpush(key, stringList.toArray(new String[]{}));
    }

    @Override
    public Long rpush(String key, String value, Jedis jedis) {
        return jedis.rpush(key, value);
    }

    @Override
    public Long rpush(String key, List<String> stringList, Jedis jedis) {
        return jedis.rpush(key, stringList.toArray(new String[]{}));
    }

    @Override
    public List<String> lrange(String key, long start, long stop, Jedis jedis) {
        return jedis.lrange(key, start, stop);
    }

    @Override
    public Long lrem(String key, long count, String value, Jedis jedis) {
        return jedis.lrem(key, count, value);
    }

    @Override
    public Long llen(String key, Jedis jedis) {
        return jedis.llen(key);
    }

    @Override
    public String ltrim(String key, long start, long stop, Jedis jedis) {
        return jedis.ltrim(key, start, stop);
    }

    @Override
    public String lindex(String key, long index, Jedis jedis) {
        return jedis.lindex(key, index);
    }
}
