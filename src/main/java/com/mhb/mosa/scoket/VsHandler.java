package com.mhb.mosa.scoket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
        boolean in = false;
        try {
            in = StaticUtils.linkService.socketOn(session);
        } catch (Exception e) {
            log.error("socket连接异常：", e);
        }
        try {
            if (in) {
                TextMsgFunctionUtils.send(new TextMsg<>(TextMsgEnum.LOGIN_O.getType(), TextMsgEnum.LOGIN_O.getMsg(), 1), session);
            } else {
                TextMsgFunctionUtils.send(new TextMsg<>(TextMsgEnum.LOGIN_F.getType(), TextMsgEnum.LOGIN_F.getMsg(), 0), session);
                SessionHome.close(session);
            }
        } catch (Exception e) {
            log.error("socket连接返回异常：", e);
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
                    textMsg.setMsg(PlayerHome.get(session.getId()).getUserName() + "：" + textMsg.getMsg());
                    TextMsgFunctionUtils.sendAll(textMsg, session);
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
            PlayerConnectionUtils.closed(PlayerHome.get(session.getId()));
            StaticUtils.linkService.socketOff(session);
        } catch (Exception e) {
            log.error("socket断开异常：", e);
        }
    }


}
