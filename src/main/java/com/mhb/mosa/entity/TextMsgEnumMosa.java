package com.mhb.mosa.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mhb.mosa.memory.PlayerHome;
import com.mhb.mosa.scoket.Handle;
import com.mhb.mosa.service.impl.MosaServiceImpl;
import com.mhb.mosa.util.ChatFunctionUtils;
import com.mhb.mosa.util.StaticUtils;
import com.mhb.mosa.vo.MosaVO;
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
                TextMsg<MosaVO> msg = JSONObject.parseObject(json, new TypeReference<TextMsg<MosaVO>>() {
                });
                PlayerMosa player = (PlayerMosa) PlayerHome.get(session.getId());
                msg.setData(new MosaVO(player, player.getUserName() + "创建房间"));
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
            String json = new String(message.asBytes());
            try {
                TextMsg<MosaVO> msg = JSONObject.parseObject(json, new TypeReference<TextMsg<MosaVO>>() {
                });
                PlayerMosa player = (PlayerMosa) PlayerHome.get(session.getId());
                msg.setData(new MosaVO(player, player.getUserName() + "加入房间", -1));
                ChatFunctionUtils.sendOther(player, JSON.toJSONString(msg));
                msg.getData().setType(0);
                msg.getData().fillPlayers();
                ChatFunctionUtils.send(player, JSON.toJSONString(msg));
            } catch (IOException e) {
                log.error("加入房间-" + json + "-异常：", e);
            }
        }
    },
    /**
     * 离开房间
     */
    LEAVE_ROOM(2) {
        @Override
        public void execute(WebSocketSession session, TextMessage message) {
            String json = new String(message.asBytes());
            try {
                TextMsg<MosaVO> msg = JSONObject.parseObject(json, new TypeReference<TextMsg<MosaVO>>() {
                });
                PlayerMosa player = (PlayerMosa) PlayerHome.get(session.getId());
                msg.setData(new MosaVO(player, player.getUserName() + "离开房间"));
                ChatFunctionUtils.sendOther(player, JSON.toJSONString(msg));
            } catch (IOException e) {
                log.error("离开房间-" + json + "-异常：", e);
            }
        }
    },
    /**
     * 准备就绪
     */
    BE_READY(3) {
        @Override
        public void execute(WebSocketSession session, TextMessage message) {
            String json = new String(message.asBytes());
            try {
                TextMsg<MosaVO> msg = JSONObject.parseObject(json, new TypeReference<TextMsg<MosaVO>>() {
                });
                PlayerMosa player = (PlayerMosa) PlayerHome.get(session.getId());
                int ready = StaticUtils.mosaService.beReady(player.getRoomId(), player.getUserName());
                if (ready < MosaServiceImpl.ROOM_PLAYER_COUNT_MAX) {
                    msg.setData(new MosaVO(player, player.getUserName() + "准备就绪"));
                } else {
                    //TODO 牌局初始化

                }
                ChatFunctionUtils.sendAll(player, JSON.toJSONString(msg));
            } catch (IOException e) {
                log.error("准备就绪-" + json + "-异常：", e);
            }

        }
    },
    /**
     * 取消就绪
     */
    CANCEL_READY(4) {
        @Override
        public void execute(WebSocketSession session, TextMessage message) {

        }
    },
    /**
     * 打出卡牌
     */
    PLAY_OUT(5) {
        @Override
        public void execute(WebSocketSession session, TextMessage message) {

        }
    },
    /**
     * 抽取卡牌
     */
    DRAW_CARD(6) {
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

    public int getType() {
        return type;
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
