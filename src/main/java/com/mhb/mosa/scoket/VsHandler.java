package com.mhb.mosa.scoket;

import com.mhb.mosa.service.PlayService;
import com.mhb.mosa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class VsHandler extends TextWebSocketHandler {

    @Autowired
    private RoomService roomService;

    @Autowired
    private PlayService playService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        roomService.joinRoom(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        //TODO 交互
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        roomService.leaveRoom(session);
        super.afterConnectionClosed(session, status);
    }


}
