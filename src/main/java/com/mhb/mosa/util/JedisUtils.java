package com.mhb.mosa.util;

import com.mhb.mosa.service.impl.LinkServiceImpl;
import com.mhb.mosa.service.impl.MosaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @date: 2021/6/18 17:37
 */
@Slf4j
@Component
public class JedisUtils {

    @Autowired
    private JedisCluster jedisCluster;

    @PostConstruct
    private void initRedisData() {
        try {
            jedisCluster.del("initKey");
        } catch (Exception e) {
            log.error("初始化redis缓存数据-initKey-异常：", e);
        }
        String[] keys = new String[3];
        keys[0] = LinkServiceImpl.redisKeyZsetPlayerOnLine();
        keys[1] = MosaServiceImpl.redisKeyStringRoomIdMax();
        keys[2] = MosaServiceImpl.redisKeyZsetRoomIdStatusWaiting();
        try {
            jedisCluster.del(keys);
        } catch (Exception e) {
            log.error("初始化redis缓存数据-keys-异常：", e);
        }
    }

    /**
     * hmset
     *
     * @param key  key
     * @param bean bean
     * @return java.lang.String
     */
    public String hmset(final String key, final Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return jedisCluster.hmset(key, BeanUtils.describe(bean));
    }

    /**
     * hmget
     *
     * @param key   key
     * @param clazz class
     * @return T
     */
    public <T> T hmget(final String key, Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        String[] fields = Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toList()).toArray(new String[]{});
        List<String> values = jedisCluster.hmget(key, fields);
        Map<String, String> map = new HashMap<>(1);
        for (int i = 0; i < fields.length; i++) {
            map.put(fields[i], values.get(i));
        }
        T t = clazz.newInstance();
        BeanUtils.populate(t, map);
        return t;
    }

}
