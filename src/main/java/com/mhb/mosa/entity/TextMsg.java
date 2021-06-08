package com.mhb.mosa.entity;

import com.mhb.mosa.memory.PlayerHome;
import com.mhb.mosa.memory.SessionHome;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2021/6/8 16:40
 */
@Data
public class TextMsg<T> implements TextMsgFunction {
    /**
     * 消息类型
     */
    private int type;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 消息内容
     */
    private T data;

    public TextMsg(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public TextMsg(int type, String msg, T data) {
        this.type = type;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public void send(WebSocketSession session) throws IOException {
        SessionHome.sendMsg(session, this);
    }

    @Override
    public void sendAll(WebSocketSession session) throws IOException {
        Player player = PlayerHome.get(session.getId());
        List<String> list = new ArrayList<>(player.getRole().getRoleHome().get());
        SessionHome.sendMsg(list, this);
    }
}
