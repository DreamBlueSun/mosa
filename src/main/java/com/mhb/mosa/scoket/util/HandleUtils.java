package com.mhb.mosa.scoket.util;

import com.mhb.mosa.scoket.Handle;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @date: 2021/6/11 16:35
 */

public class HandleUtils {

    public static void execute(Handle handle, WebSocketSession session, TextMessage message) {
        handle.execute(session, message);
    }
}
