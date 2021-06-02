package com.mhb.mosa.entity;

import com.mhb.mosa.memory.SessionHome;
import lombok.Data;

import java.io.IOException;

/**
 * @date: 2021/6/2 17:44
 */
@Data
public class MoSaPlayer extends Player implements Comparable{

    /**
     * 玩家所在房间
     **/
    private String roomName;
    /**
     * 玩家位置
     **/
    private Integer index;

    public MoSaPlayer(String sessionId, String userName, String roomName, Integer index) {
        super(sessionId, userName);
        this.roomName = roomName;
        this.index = index;
    }

    @Override
    public void afterConnectionClosed() {
        try {
            SessionHome.sendMsg(this,"msg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof MoSaPlayer) {
            return ((MoSaPlayer) o).index - this.index;
        }
        return 1;
    }
}
