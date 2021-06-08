package com.mhb.mosa.entity;

import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;

/**
 * @date: 2021/6/8 15:09
 */

public interface TextMsgFunction {

    void send(WebSocketSession session) throws IOException;

    void sendAll(WebSocketSession session) throws IOException;

}
