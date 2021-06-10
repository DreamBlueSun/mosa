package com.mhb.mosa.util;

import com.mhb.mosa.service.LinkService;
import com.mhb.mosa.service.PlayService;
import com.mhb.mosa.service.RoomService;
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
    public void setLoginService(LinkService loginService) {
        StaticUtils.linkService = loginService;
    }

    public static LinkService linkService;

    @Autowired
    public void setLoginService(RoomService roomService) {
        StaticUtils.roomService = roomService;
    }

    public static RoomService roomService;

    @Autowired
    public void setLoginService(PlayService playService) {
        StaticUtils.playService = playService;
    }

    public static PlayService playService;
}
