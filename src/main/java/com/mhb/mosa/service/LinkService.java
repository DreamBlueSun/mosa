package com.mhb.mosa.service;

import org.springframework.web.socket.WebSocketSession;

/**
 * @date: 2021/5/21 15:31
 */

public interface LinkService {

    /**
     * 连接socket
     *
     * @param session session
     * @return void
     */
    boolean socketOn(WebSocketSession session);

    /**
     * 断开socket
     *
     * @param session session
     * @return void
     */
    void socketOff(WebSocketSession session);

    /**
     * 登入
     *
     * @param userName
     * @return boolean
     */
    boolean login(String userName);

    /**
     * 登出
     *
     * @param userName
     * @return void
     */
    void logout(String userName);
}
