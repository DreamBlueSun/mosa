package com.mhb.mosa.entity;

import lombok.Data;

@Data
public class Player implements PlayerConnection {

    /**
     * 玩家会话id
     **/
    private String sessionId;

    /**
     * 玩家名称
     **/
    private String userName;

    public Player(String sessionId, String userName) {
        this.sessionId = sessionId;
        this.userName = userName;
    }

    @Override
    public void afterConnectionClosed() {
    }
}
