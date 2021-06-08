package com.mhb.mosa.scoket.util;

import com.mhb.mosa.entity.PlayerConnection;

/**
 * @date: 2021/6/8 18:20
 */

public class PlayerConnectionUtils {

    public static void closed(PlayerConnection connection){
        connection.afterConnectionClosed();
    }

}
