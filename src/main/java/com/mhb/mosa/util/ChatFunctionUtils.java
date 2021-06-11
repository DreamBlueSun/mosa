package com.mhb.mosa.util;

import com.mhb.mosa.entity.ChatFunction;
import java.io.IOException;

/**
 * @date: 2021/6/9 16:06
 */

public class ChatFunctionUtils {

    public static void send(ChatFunction function, String msg) throws IOException {
        function.send(msg);
    }

    public static void sendAll(ChatFunction function, String msg) throws IOException {
        function.sendAll(msg);
    }
}
