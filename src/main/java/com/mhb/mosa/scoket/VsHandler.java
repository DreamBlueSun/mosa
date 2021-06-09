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
            in = StaticUtils.loginService.in(session);
        } catch (Exception e) {
            log.error("登入异常：", e);
        }
        try {
            if (in) {
                TextMsgFunctionUtils.send(new TextMsg<Integer>(TextMsgEnum.LOGIN_O.getType(), TextMsgEnum.LOGIN_O.getMsg()), session);
            } else {
                TextMsgFunctionUtils.send(new TextMsg<Integer>(TextMsgEnum.LOGIN_F.getType(), TextMsgEnum.LOGIN_F.getMsg()), session);
                SessionHome.close(session);
            }
        } catch (Exception e) {
            log.error("连接socket异常：", e);
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
            StaticUtils.loginService.out(session);
        } catch (Exception e) {
            log.error("关闭socket异常：", e);
        }
    }


}
