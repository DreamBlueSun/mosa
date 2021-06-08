package com.mhb.mosa.entity;

import com.mhb.mosa.memory.SessionHome;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;

/**
 * @date: 2021/6/2 17:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerMoSa extends Player implements Comparable {

    /**
     * 玩家所在房间
     **/
    private String roomName;
    /**
     * 玩家位置
     **/
    private Integer index;

    public PlayerMoSa(String sessionId, String userName, String roomName, Integer index) {
        super(sessionId, userName);
        this.roomName = roomName;
        this.index = index;
    }

    @Override
    public void afterConnectionClosed() {
        try {
            SessionHome.sendMsg(getSessionId(), "连接丢失");
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.afterConnectionClosed();
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof PlayerMoSa) {
            return ((PlayerMoSa) o).index - this.index;
        }
        return 1;
    }
}
