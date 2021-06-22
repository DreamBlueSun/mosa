package com.mhb.mosa.service.impl;

import com.mhb.mosa.constant.Constants;
import com.mhb.mosa.entity.RoomMosa;
import com.mhb.mosa.service.MosaService;
import com.mhb.mosa.util.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
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
     * field：玩家名称、玩家房间位置
     * value：就绪状态（Y：已就绪，N：未就绪）、玩家名称
     *
     * @return java.lang.String
     */
    public static String redisKeyHashRoomPlayerInfo(String roomId) {
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
        final String index = "0";
        //玩家信息
        Map<String, String> playerMap = new HashMap<>(2);
        playerMap.put("roomId", roomId);
        playerMap.put("index", index);
        jedisCluster.hmset(PlayerServiceImpl.redisKeyHashPlayerInfo(userName), playerMap);
        //房间信息
        jedisUtils.hmset(redisKeyHashRoomInfo(roomId), new RoomMosa(roomId, userName));
        //房间玩家信息
        Map<String, String> roomPlayerMap = new HashMap<>(2);
        roomPlayerMap.put(index, userName);
        roomPlayerMap.put(userName, Constants.N);
        jedisCluster.hmset(redisKeyHashRoomPlayerInfo(roomId), roomPlayerMap);
        //房间列表
        jedisCluster.zadd(redisKeyZsetRoomIdStatusWaiting(), System.currentTimeMillis(), roomId);
        return true;
    }

    private final static int ROOM_PLAYER_COUNT_MAX = 4;
    private final static String[] ROOM_PLAYER_INDEX_ARRAY = {"0", "1", "2", "3"};

    @Override
    public boolean joinRoom(String roomId, String userName) {
        int index = -1;
        List<String> list = jedisCluster.hmget(redisKeyHashRoomPlayerInfo(roomId), ROOM_PLAYER_INDEX_ARRAY);
        for (int i = 0; i < ROOM_PLAYER_COUNT_MAX; i++) {
            if (list.get(i) == null) {
                index = i;
            }
        }
        if (index < 0) {
            return false;
        }
        //玩家信息
        Map<String, String> playerMap = new HashMap<>(2);
        playerMap.put("roomId", roomId);
        playerMap.put("index", String.valueOf(index));
        jedisCluster.hmset(PlayerServiceImpl.redisKeyHashPlayerInfo(userName), playerMap);
        //房间玩家信息
        Map<String, String> roomPlayerMap = new HashMap<>(2);
        roomPlayerMap.put(String.valueOf(index), userName);
        roomPlayerMap.put(userName, Constants.N);
        jedisCluster.hmset(redisKeyHashRoomPlayerInfo(roomId), roomPlayerMap);
        return true;
    }

    @Override
    public void leaveRoom(String roomId, String userName, String index) {
        //移除房间内当前玩家信息
        jedisCluster.hdel(redisKeyHashRoomPlayerInfo(roomId), userName, index);
        //移除缓存玩家信息
        jedisCluster.del(PlayerServiceImpl.redisKeyHashPlayerInfo(userName));
        //关闭房间
        if (jedisCluster.hlen(redisKeyHashRoomPlayerInfo(roomId)) == 0) {
            //房间信息
            jedisCluster.del(redisKeyHashRoomInfo(roomId));
            //房间列表
            jedisCluster.zrem(redisKeyZsetRoomIdStatusWaiting(), roomId);
        } else {
            String master = jedisCluster.hget(redisKeyHashRoomInfo(roomId), "master");
            if (StringUtils.equals(master, userName)) {
                //房主变更
                List<String> list = jedisCluster.hmget(redisKeyHashRoomPlayerInfo(roomId), ROOM_PLAYER_INDEX_ARRAY);
                if (CollectionUtils.isNotEmpty(list)) {
                    for (int i = 0; i < ROOM_PLAYER_COUNT_MAX; i++) {
                        if (list.get(i) != null) {
                            jedisCluster.hset(redisKeyHashRoomInfo(roomId), "master", list.get(i));
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<String> listUserNameInRoom(String roomId) {
        return jedisCluster.hmget(redisKeyHashRoomPlayerInfo(roomId), ROOM_PLAYER_INDEX_ARRAY);
    }
}
