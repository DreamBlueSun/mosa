package com.mhb.mosa.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
public enum TextMsgEnumMosa implements Handle {
    /**
     * 创建房间
     */
    CREATE_ROOM(0) {
        @Override
        public void execute(WebSocketSession session, TextMessage message) {
            String json = new String(message.asBytes());
            try {
                TextMsg msg = JSONObject.parseObject(json, TextMsg.class);
                Player player = PlayerHome.get(session.getId());
                //TODO 创建房间
                ChatFunctionUtils.send(player, JSON.toJSONString(msg));
            } catch (IOException e) {
                log.error("创建房间-" + json + "-异常：", e);
            }
        }
    },
    /**
     * 加入房间
     */
    JOIN_ROOM(1) {
        @Override
        public void execute(WebSocketSession session, TextMessage message) {

        }
    },
    /**
     * 离开房间
     */
    LEAVE_ROOM(2) {
        @Override
        public void execute(WebSocketSession session, TextMessage message) {

        }
    },
    /**
     * 准备就绪
     */
    ROUND_READY(3) {
        @Override
        public void execute(WebSocketSession session, TextMessage message) {

        }
    },
    /**
     * 打出卡牌
     */
    PLAY_OUT(4) {
        @Override
        public void execute(WebSocketSession session, TextMessage message) {

        }
    },
    /**
     * 抽取卡牌
     */
    DRAW_CARD(5) {
        @Override
        public void execute(WebSocketSession session, TextMessage message) {

        }
    };

    /**
     * 类型
     */
    private int type;

    TextMsgEnumMosa(int type) {
        this.type = type;
    }

    public static TextMsgEnumMosa getInstance(int type) {
        for (TextMsgEnumMosa value : TextMsgEnumMosa.values()) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }
}
