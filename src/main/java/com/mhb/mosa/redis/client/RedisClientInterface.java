package com.mhb.mosa.redis.client;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

public interface RedisClientInterface {
    Jedis getJedis();

    void closeJedis(Jedis jedis);

    String set(String key, String value, Jedis jedis);

    String set(String key, String value, String nxxx, String expx, long time, Jedis jedis);

    String get(String key, Jedis jedis);

    Long hSet(String key, String field, String value, Jedis jedis);

    String hGet(String key, String field, Jedis jedis);

    Map<String, String> hGetAll(String key, Jedis jedis);

    Boolean hexists(String key, String field, Jedis jedis);

    Long expire(String key, int seconds, Jedis jedis);

    Long del(String key, Jedis jedis);

    Long hDel(String key, String field, Jedis jedis);

    Long hDel(String key, String[] fields, Jedis jedis);

    Long lpush(String key, String value, Jedis jedis);

    Long lpush(String key, List<String> stringList, Jedis jedis);

    Long rpush(String key, String value, Jedis jedis);

    Long rpush(String key, List<String> stringList, Jedis jedis);

    List<String> lrange(String key, long start, long stop, Jedis jedis);

    Long lrem(String key, long count, String value, Jedis jedis);

    Long llen(String key, Jedis jedis);

    String ltrim(String key, long start, long stop, Jedis jedis);

    String lindex(String key, long index, Jedis jedis);

}
