package com.mhb.mosa.util;

import com.mhb.mosa.entity.TextMsgFunction;
import java.io.IOException;

/**
 * @date: 2021/6/9 16:06
 */

public class TextMsgFunctionUtils {

    public static void send(TextMsgFunction function, String msg) throws IOException {
        function.send(msg);
    }

    public static void sendAll(TextMsgFunction function, String msg) throws IOException {
        function.sendAll(msg);
    }
}
