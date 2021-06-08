package com.mhb.mosa.entity;

import com.mhb.mosa.memory.RoleHomeEnum;
import lombok.Data;

@Data
public class Player implements PlayerConnection {

    /**
     * 会话id
     **/
    private String sessionId;

    /**
     * 名称
     **/
    private String userName;

    /**
     * 角色
     **/
    private RoleHomeEnum role;

    public Player(String sessionId, String userName) {
        this.sessionId = sessionId;
        this.userName = userName;
        this.role = RoleHomeEnum.SQUARE;
        this.role.getRoleHome().add(this.sessionId);
    }

    public void changeRole(RoleHomeEnum role) {
        this.role.getRoleHome().remove(this.sessionId);
        this.role = role;
        this.role.getRoleHome().add(this.sessionId);
    }

    @Override
    public void afterConnectionClosed() {
        this.role.getRoleHome().remove(this.sessionId);
    }
}
