package com.mhb.mosa.util;

import com.mhb.mosa.entity.TextMsgFunction;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @date: 2021/6/9 16:06
 */

public class TextMsgFunctionUtils {

    public static void send(TextMsgFunction function, WebSocketSession session) throws IOException {
        function.send(session);
    }

    public static void sendAll(TextMsgFunction function, WebSocketSession session) throws IOException {
        function.sendAll(session);
    }
}
