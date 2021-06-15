package com.mhb.mosa.scoket;

import com.alibaba.fastjson.JSONObject;
import com.mhb.mosa.entity.Player;
import com.mhb.mosa.entity.TextMsg;
import com.mhb.mosa.entity.TextMsgEnum;
import com.mhb.mosa.memory.PlayerHome;
import com.mhb.mosa.memory.SessionHome;
import com.mhb.mosa.scoket.util.HandleUtils;
import com.mhb.mosa.util.PlayerConnectionUtils;
import com.mhb.mosa.util.StaticUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
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
        try {
            TextMsg textMsg = JSONObject.parseObject(new String(message.asBytes()), TextMsg.class);
            Handle handle = TextMsgEnum.getHandle(textMsg.getModule(), textMsg.getType());
            if (handle != null) {
                HandleUtils.execute(handle, session, message);
            }
        } catch (Exception e) {
            log.error("socket处理异常：", e);
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
