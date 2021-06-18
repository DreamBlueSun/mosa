package com.mhb.mosa.util;

import com.mhb.mosa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

/**
 * @date: 2021/6/3 15:56
 */
@Component
public class StaticUtils {

    @Autowired
    public void setJedisCluster(JedisCluster jedisCluster) {
        StaticUtils.jedisCluster = jedisCluster;
    }

    public static JedisCluster jedisCluster;

    @Autowired
    public void setJedisUtils(JedisUtils jedisUtils) {
        StaticUtils.jedisUtils = jedisUtils;
    }

    public static JedisUtils jedisUtils;

    @Autowired
    public void setLinkService(LinkService linkService) {
        StaticUtils.linkService = linkService;
    }

    public static LinkService linkService;

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        StaticUtils.playerService = playerService;
    }

    public static PlayerService playerService;

    @Autowired
    public void setMosaService(MosaService mosaService) {
        StaticUtils.mosaService = mosaService;
    }

    public static MosaService mosaService;

    @Autowired
    public void setRoomService(RoomService roomService) {
        StaticUtils.roomService = roomService;
    }

    public static RoomService roomService;

    @Autowired
    public void setPlayService(PlayService playService) {
        StaticUtils.playService = playService;
    }

    public static PlayService playService;
}
