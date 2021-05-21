package com.mhb.mosa.service.impl;

import com.mhb.mosa.memory.SessionHome;
import com.mhb.mosa.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import redis.clients.jedis.JedisCluster;

/**
 * @date: 2021/5/21 15:32
 */
@Service
public class LoginServiceImpl implements LoginService {

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
    public boolean in(WebSocketSession session) {
        String userName = SessionHome.getUserName(session);
        Long i = jedisCluster.zrank(redisKeyZsetPlayerOnLine(), userName);
        if (i == null) {
            jedisCluster.zadd(redisKeyZsetPlayerOnLine(), System.currentTimeMillis(), userName);
            SessionHome.put(session);
            return true;
        }
        return false;
    }

    @Override
    public void out(WebSocketSession session) {
        String userName = SessionHome.getUserName(session);
        jedisCluster.zrem(redisKeyZsetPlayerOnLine(), userName);
        SessionHome.remove(session.getId());
    }
}
