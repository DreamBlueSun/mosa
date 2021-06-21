package com.mhb.mosa.entity;

import com.mhb.mosa.memory.PlayerHome;
import com.mhb.mosa.memory.RoleHomeEnum;
import com.mhb.mosa.memory.SessionHome;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;

@Data
public class Player implements PlayerConnection, ChatFunction {

    /**
     * 会话id
     */
    private String sessionId;

    /**
     * 名称
     */
    private String userName;

    /**
     * 角色
     */
    private RoleHomeEnum role;

    public Player(String sessionId, String userName) {
        this.sessionId = sessionId;
        this.userName = userName;
        this.role = RoleHomeEnum.SQUARE;
    }

    public Player(String sessionId, String userName, RoleHomeEnum role) {
        this.sessionId = sessionId;
        this.userName = userName;
        this.role = role;
    }

    @Override
    public void afterConnectionEstablished() {
        PlayerHome.put(this.sessionId, this);
        this.role.getRoleHome().add(this.sessionId);
    }

    @Override
    public void afterConnectionClosed() {
        PlayerHome.remove(this.sessionId);
        this.role.getRoleHome().remove(this.sessionId);
    }

    @Override
    public void send(String msg) throws IOException {
        SessionHome.sendMsg(SessionHome.get(this.sessionId), msg);
    }

    @Override
    public void sendAll(String msg) throws IOException {
        SessionHome.sendMsg(new ArrayList<>(this.role.getRoleHome().get()), msg);
    }

    @Override
    public void sendOther(String msg) throws IOException {
        ArrayList<String> list = new ArrayList<>(this.role.getRoleHome().get());
        list.remove(this.userName);
        if (CollectionUtils.isNotEmpty(list)) {
            SessionHome.sendMsg(list, msg);
        }
    }
}
