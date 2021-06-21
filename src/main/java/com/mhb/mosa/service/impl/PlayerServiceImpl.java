package com.mhb.mosa.service.impl;

import com.alibaba.fastjson.JSON;
import com.mhb.mosa.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.Map;

/**
 * @date: 2021/6/15 18:17
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 缓存key：玩家信息
     * hash
     * field：玩家属性名称
     * value：玩家属性值
     *
     * @return java.lang.String
     */
    public static String redisKeyHashPlayerInfo(String userName) {
        return "{mosa}:r:k:hash:p:i:" + userName;
    }

    @Override
    public <T> T get(String userName, Class<T> clazz) {
        Map<String, String> map = jedisCluster.hgetAll(redisKeyHashPlayerInfo(userName));
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(map), clazz);
    }

    @Override
    public List<String> getProperties(String userName, String... fields) {
        List<String> list = jedisCluster.hmget(redisKeyHashPlayerInfo(userName), fields);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }
}
