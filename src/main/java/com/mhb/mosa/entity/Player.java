package com.mhb.mosa.entity;

import lombok.Data;

@Data
public class Player implements Comparable {
    /**
     * 玩家名称
     **/
    private String userName;

    /**
     * 玩家所在房间
     **/
    private String roomName;
    /**
     * 玩家位置
     **/
    private Integer index;
    /**
     * 玩家会话id
     **/
    private String sessionId;

    @Override
    public int compareTo(Object o) {
        if (o instanceof Player) {
            return ((Player) o).index - this.index;
        }
        return 1;
    }
}
