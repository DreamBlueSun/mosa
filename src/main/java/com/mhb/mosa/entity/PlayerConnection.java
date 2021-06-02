package com.mhb.mosa.entity;

/**
 * 用户连接接口
 *
 * @date: 2021/6/2 17:33
 */

public interface PlayerConnection {

    /**
     * 用户断开连接后执行的方法
     *
     * @return void
     */
    void afterConnectionClosed();

}
