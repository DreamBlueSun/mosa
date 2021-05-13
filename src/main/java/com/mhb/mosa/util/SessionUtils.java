package com.mhb.mosa.util;

import com.alibaba.fastjson.JSONObject;
import com.mhb.mosa.constant.ConstantVsScoket;
import com.mhb.mosa.entity.Player;
import com.mhb.mosa.scoket.util.ScoketUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @date: 2021/5/13 17:25
 */

public class SessionUtils {

    /**
     * Map<sessionId, webSocketSession>
     * 存放连接中的会话
     */
    private static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>(8);

    public static void put(WebSocketSession session) {
        sessionMap.put(session.getId(), session);
    }

    public static WebSocketSession get(String sessionId) {
        return sessionMap.get(sessionId);
    }

    public static void remove(String sessionId) {
        sessionMap.remove(sessionId);
    }

    public static void close(WebSocketSession session) throws IOException {
        session.close();
    }

    public static String getUserName(WebSocketSession session) {
        return ScoketUtils.getAttributes(session, ConstantVsScoket.SESSION_USER_NAME);
    }

    public static String getRoomName(WebSocketSession session) {
        return ScoketUtils.getAttributes(session, ConstantVsScoket.SESSION_ROOM_NAME);
    }

    public static void sendMessage(WebSocketSession session, Object vo) throws IOException {
        String s = JSONObject.toJSONString(vo);
        session.sendMessage(new TextMessage(s));
    }

    public static void sendMessage(List<Player> listPlayer, Object vo) throws IOException {
        for (Player p : listPlayer) {
            sendMessage(get(p.getSessionId()), vo);
        }
    }

}
