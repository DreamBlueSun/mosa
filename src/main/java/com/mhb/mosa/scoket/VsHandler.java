package com.mhb.mosa.scoket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mhb.mosa.entity.Player;
import com.mhb.mosa.entity.TextMsg;
import com.mhb.mosa.entity.TextMsgEnum;
import com.mhb.mosa.memory.PlayerHome;
import com.mhb.mosa.memory.SessionHome;
import com.mhb.mosa.util.PlayerConnectionUtils;
import com.mhb.mosa.util.StaticUtils;
import com.mhb.mosa.util.TextMsgFunctionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Slf4j
@Component
public class VsHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        try {
            if (!StaticUtils.linkService.socketOn(session)) {
                SessionHome.close(session);
            }
        } catch (Exception e) {
            log.error("socket连接异常：", e);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        TextMsg textMsg = JSONObject.parseObject(new String(message.asBytes()), TextMsg.class);
        TextMsgEnum textMsgEnum = TextMsgEnum.getInstance(textMsg.getType());
        if (textMsgEnum == null) {
            return;
        }
        switch (textMsgEnum) {
            case CHAT:
                try {
                    Player player = PlayerHome.get(session.getId());
                    textMsg.setMsg(player.getUserName() + "：" + textMsg.getMsg());
                    TextMsgFunctionUtils.sendAll(player, JSON.toJSONString(textMsg));
                } catch (IOException e) {
                    log.error("发送聊天消息-" + JSON.toJSONString(textMsg) + "-异常：", e);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        try {
            Player player = PlayerHome.get(session.getId());
            if (player != null) {
                PlayerConnectionUtils.closed(player);
            }
            StaticUtils.linkService.socketOff(session);
        } catch (Exception e) {
            log.error("socket断开异常：", e);
        }
    }


}
