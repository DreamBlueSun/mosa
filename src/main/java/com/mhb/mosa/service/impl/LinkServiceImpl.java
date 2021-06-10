package com.mhb.mosa.service.impl;

import com.mhb.mosa.entity.Player;
import com.mhb.mosa.memory.PlayerHome;
import com.mhb.mosa.memory.SessionHome;
import com.mhb.mosa.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import redis.clients.jedis.JedisCluster;

/**
 * @date: 2021/5/21 15:32
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 缓存key：在线玩家集合
     * zset
     * 权重：登入时间戳
     *
     * @return java.lang.String
     */
    private String redisKeyZsetPlayerOnLine() {
        return "r:k:zset:p:o:l";
    }

    @Override
    public boolean socketOn(WebSocketSession session) {
        SessionHome.put(session);
        String sessionId = session.getId();
        PlayerHome.put(sessionId, new Player(sessionId, SessionHome.getUserName(session)));
        return true;
    }

    @Override
    public void socketOff(WebSocketSession session) {
        String sessionId = session.getId();
        SessionHome.remove(sessionId);
        PlayerHome.remove(sessionId);
    }

    @Override
    public boolean login(String userName) {
        Long i = jedisCluster.zrank(redisKeyZsetPlayerOnLine(), userName);
        if (i == null) {
            jedisCluster.zadd(redisKeyZsetPlayerOnLine(), System.currentTimeMillis(), userName);
            return true;
        }
        return false;
    }

    @Override
    public void logout(String userName) {
        jedisCluster.zrem(redisKeyZsetPlayerOnLine(), userName);
    }
}
