package com.mhb.mosa.service;

import org.springframework.web.socket.WebSocketSession;

/**
 * @date: 2021/5/13 17:23
 */

public interface RoomService {

    /**
     * 创建房间
     *
     * @param session session
     * @return void
     */
    void createRoom(WebSocketSession session);

    /**
     * 加入房间
     *
     * @param session session
     * @return void
     */
    void joinRoom(WebSocketSession session);

    /**
     * 离开房间
     *
     * @param session session
     * @return void
     */
    void leaveRoom(WebSocketSession session);
}
