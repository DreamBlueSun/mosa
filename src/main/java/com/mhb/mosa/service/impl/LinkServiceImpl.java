package com.mhb.mosa.service.impl;

import com.mhb.mosa.memory.SessionHome;
import com.mhb.mosa.service.LinkService;
import com.mhb.mosa.task.LinkTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import redis.clients.jedis.JedisCluster;

/**
 * @date: 2021/5/21 15:32
 */
@Slf4j
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
    public static String redisKeyZsetPlayerOnLine() {
        return "r:k:zset:p:o:l";
    }

    @Override
    public boolean socketOn(WebSocketSession session) {
        String userName = SessionHome.getUserName(session);
        if (userName.length() > 0) {
            LinkTask.removeListLogout(userName);
            if (jedisCluster.zrank(redisKeyZsetPlayerOnLine(), userName) != null) {
                SessionHome.put(session);
                return true;
            }
        }
        return false;
    }

    @Override
    public void socketOff(WebSocketSession session) {
        String sessionId = session.getId();
        SessionHome.remove(sessionId);
        LinkTask.putListLogout(SessionHome.getUserName(session));
    }

    @Override
    public boolean login(String userName) {
        if (jedisCluster.zrank(redisKeyZsetPlayerOnLine(), userName) == null) {
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
