package com.mhb.mosa.service.impl;

import com.mhb.mosa.constant.Constants;
import com.mhb.mosa.entity.RoomMosa;
import com.mhb.mosa.service.MosaService;
import com.mhb.mosa.util.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @date: 2021/6/15 17:30
 */
@Slf4j
@Service
public class MosaServiceImpl implements MosaService {

    @Autowired
    private JedisUtils jedisUtils;

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 缓存key：历史最大房间号
     * String
     *
     * @return java.lang.String
     */
    private String redisKeyStringRoomIdMax() {
        return "{mosa}:r:k:string:r:i:m";
    }

    /**
     * 缓存key：房间信息
     * hash
     * field：房间属性名称
     * value：房间属性值
     *
     * @return java.lang.String
     */
    private String redisKeyHashRoomInfo(String roomId) {
        return "{mosa}:r:k:hash:r:i:" + roomId;
    }

    /**
     * 缓存key：未开始的房间号列表
     * zset
     * 权重：创建时间
     *
     * @return java.lang.String
     */
    private String redisKeyZsetRoomIdStatusWaiting() {
        return "{mosa}:r:k:zset:r:i:s:w";
    }

    /**
     * 缓存key：房间玩家信息
     * hash
     * field：玩家名称
     * value：就绪状态（Y：已就绪，N：未就绪）
     *
     * @return java.lang.String
     */
    private String redisKeyHashRoomPlayerInfo(String roomId) {
        return "{mosa}:r:k:hash:r:p:i:" + roomId;
    }

    @PostConstruct
    private void initMosa() {
        try {
            jedisCluster.del(redisKeyStringRoomIdMax(), redisKeyZsetRoomIdStatusWaiting());
        } catch (Exception e) {
            log.error("初始化Mosa-异常：", e);
        }
    }

    /**
     * 生成新的房间号
     *
     * @return java.lang.String
     */
    private String newRoomId() {
        return String.valueOf(jedisCluster.incr(redisKeyStringRoomIdMax()));
    }

    @Override
    public boolean createRoom(String userName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String roomId = newRoomId();
        Map<String, String> playerMap = new HashMap<>(2);
        playerMap.put("roomId", roomId);
        playerMap.put("index", "0");
        //玩家信息
        jedisCluster.hmset(PlayerServiceImpl.redisKeyHashPlayerInfo(userName), playerMap);
        //房间信息
        jedisUtils.hmset(redisKeyHashRoomInfo(roomId), new RoomMosa(roomId, userName));
        //房间玩家信息
        jedisCluster.hset(redisKeyHashRoomPlayerInfo(roomId), userName, Constants.N);
        //房间列表
        jedisCluster.zadd(redisKeyZsetRoomIdStatusWaiting(), System.currentTimeMillis(), roomId);
        return true;
    }

    private final static int ROOM_PLAYER_COUNT_MAX = 4;

    @Override
    public boolean joinRoom(String roomId, String userName) {
        int count = jedisCluster.hlen(redisKeyHashRoomPlayerInfo(roomId)).intValue();
        if ((count + 1) > ROOM_PLAYER_COUNT_MAX) {
            return false;
        }
        Map<String, String> playerMap = new HashMap<>(2);
        playerMap.put("roomId", roomId);
        playerMap.put("index", String.valueOf(count));
        //玩家信息
        jedisCluster.hmset(PlayerServiceImpl.redisKeyHashPlayerInfo(userName), playerMap);
        //房间玩家信息
        jedisCluster.hset(redisKeyHashRoomPlayerInfo(roomId), userName, Constants.N);
        return true;
    }
}
