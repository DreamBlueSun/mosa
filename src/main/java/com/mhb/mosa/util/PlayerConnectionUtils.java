package com.mhb.mosa.util;

import com.mhb.mosa.entity.PlayerConnection;

/**
 * @date: 2021/6/8 18:20
 */

public class PlayerConnectionUtils {

    public static void enabled(PlayerConnection connection){
        connection.afterConnectionEstablished();
    }

    public static void closed(PlayerConnection connection){
        connection.afterConnectionClosed();
    }

}
