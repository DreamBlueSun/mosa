package com.mhb.mosa.memory;

import com.mhb.mosa.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @date: 2021/5/13 17:25
 */

public class PlayerHome {

    /**
     * Map<sessionId, Player>
     * 存放连接中的用户
     */
    private static Map<String, Player> playerMap = new ConcurrentHashMap<>(8);

    public static void put(String sessionId, Player player) {
        playerMap.put(sessionId, player);
    }

    public static Player get(String sessionId) {
        return playerMap.get(sessionId);
    }

    public static void remove(String sessionId) {
        playerMap.remove(sessionId);
    }

}
