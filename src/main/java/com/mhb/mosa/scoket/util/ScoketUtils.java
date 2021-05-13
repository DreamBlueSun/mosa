package com.mhb.mosa.scoket.util;

import org.springframework.web.socket.WebSocketSession;

public class ScoketUtils {

    /**
     * 获取session中的值
     *
     * @param session session
     * @param key     key
     * @return java.lang.String
     */
    public static String getAttributes(WebSocketSession session, String key) {
        return (String) session.getAttributes().get(key);
    }

}
