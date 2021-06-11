package com.mhb.mosa.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mhb.mosa.memory.PlayerHome;
import com.mhb.mosa.scoket.Handle;
import com.mhb.mosa.util.ChatFunctionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @date: 2021/5/21 18:05
 */
@Slf4j
public enum TextMsgEnumChat implements Handle {
    /**
     * 发送全体聊天消息
     */
    ALL(0) {
        @Override
        public void execute(WebSocketSession session, TextMessage message) {
            String json = new String(message.asBytes());
            try {
                TextMsg<String> msg = JSONObject.parseObject(json, new TypeReference<TextMsg<String>>() {
                });
                Player player = PlayerHome.get(session.getId());
                msg.setData("[" + player.getUserName() + "]：" + msg.getData());
                ChatFunctionUtils.sendAll(player, JSON.toJSONString(msg));
            } catch (IOException e) {
                log.error("发送全体聊天消息-" + json + "-异常：", e);
            }
        }
    },
    /**
     * 发送单体聊天消息
     */
    ONE(1) {
        @Override
        public void execute(WebSocketSession session, TextMessage message) {
            String json = new String(message.asBytes());
            try {
                TextMsg<String> msg = JSONObject.parseObject(json, new TypeReference<TextMsg<String>>() {
                });
                Player player = PlayerHome.get(session.getId());
                msg.setData("（私聊）[" + player.getUserName() + "]：" + msg.getData());
                ChatFunctionUtils.send(player, JSON.toJSONString(msg));
            } catch (IOException e) {
                log.error("发送单体聊天消息-" + json + "-异常：", e);
            }
        }
    };

    /**
     * 类型
     */
    private int type;

    TextMsgEnumChat(int type) {
        this.type = type;
    }

    public static TextMsgEnumChat getInstance(int type) {
        for (TextMsgEnumChat value : TextMsgEnumChat.values()) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }
}
