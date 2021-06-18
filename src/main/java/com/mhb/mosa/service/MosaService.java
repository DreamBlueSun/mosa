package com.mhb.mosa.service;

import java.lang.reflect.InvocationTargetException;

/**
 * @date: 2021/6/15 17:30
 */

public interface MosaService {

    /**
     * 创建房间
     *
     * @param userName 用户名称
     * @return boolean
     */
    boolean createRoom(String userName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    /**
     * 加入房间
     *
     * @param roomId   房间号
     * @param userName 用户名称
     * @return boolean
     */
    boolean joinRoom(String roomId, String userName);
}
