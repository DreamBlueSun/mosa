package com.mhb.mosa.scoket;

import com.mhb.mosa.memory.SessionHome;
import com.mhb.mosa.service.LoginService;
import com.mhb.mosa.service.PlayService;
import com.mhb.mosa.service.RoomService;
import com.mhb.mosa.vo.TextMsgEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class VsHandler extends TextWebSocketHandler {

    @Autowired
    public void setLoginService(LoginService loginService) {
        VsHandler.loginService = loginService;
    }
    private static LoginService loginService;

    @Autowired
    public void setLoginService(RoomService roomService) {
        VsHandler.roomService = roomService;
    }
    private static RoomService roomService;

    @Autowired
    public void setLoginService(PlayService playService) {
        VsHandler.playService = playService;
    }
    private static PlayService playService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        boolean in = false;
        try {
            in = loginService.in(session);
        } catch (Exception e) {
            log.error("登入异常：", e);
        }
        try {
            if (in) {
                SessionHome.sendMsg(session, TextMsgEnum.LOGIN_OK);
                super.afterConnectionEstablished(session);
            } else {
                SessionHome.sendMsg(session,TextMsgEnum.LOGIN_FAIL);
                SessionHome.close(session);
            }
        } catch (Exception e) {
            log.error("连接socket异常：", e);
            SessionHome.close(session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        //TODO 交互
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        roomService.leaveRoom(session);
        loginService.out(session);
        super.afterConnectionClosed(session, status);
    }


}
