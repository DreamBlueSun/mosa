package com.mhb.mosa.scoket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @date: 2021/6/11 16:28
 */

public interface Handle {

    void execute(WebSocketSession session, TextMessage message);
}
