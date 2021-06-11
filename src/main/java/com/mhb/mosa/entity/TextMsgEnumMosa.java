package com.mhb.mosa.entity;

import com.mhb.mosa.scoket.Handle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

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
