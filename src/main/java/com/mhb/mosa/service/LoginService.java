package com.mhb.mosa.service;

import org.springframework.web.socket.WebSocketSession;

/**
 * @date: 2021/5/21 15:31
 */

public interface LoginService {

    /**
     * 登入
     *
     * @param session session
     * @return void
     */
    boolean in(WebSocketSession session);

    /**
     * 登出
     *
     * @param session session
     * @return void
     */
    void out(WebSocketSession session);
}
