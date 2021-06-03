package com.mhb.mosa.scoket;

import com.mhb.mosa.memory.PlayerHome;
import com.mhb.mosa.memory.SessionHome;
import com.mhb.mosa.util.StaticUtils;
import com.mhb.mosa.vo.TextMsgEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

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
                SessionHome.sendMsg(session, TextMsgEnum.LOGIN_OK);
            } else {
                SessionHome.sendMsg(session, TextMsgEnum.LOGIN_FAIL);
                SessionHome.close(session);
            }
        } catch (Exception e) {
            log.error("连接socket异常：", e);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        //TODO 交互
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        try {
            PlayerHome.get(session.getId()).afterConnectionClosed();
            StaticUtils.loginService.out(session);
        } catch (Exception e) {
            log.error("关闭socket异常：", e);
        }
    }


}
