package com.mhb.mosa.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

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

    /**
     * 离开房间
     *
     * @param roomId   房间号
     * @param userName 用户名称
     * @param index    用户房间位置
     * @return boolean
     */
    void leaveRoom(String roomId, String userName, String index);

    /**
     * 关闭房间
     *
     * @param roomId 房间号
     * @return boolean
     */
    void closeRoom(String roomId);

    /**
     * 获取房间内玩家名称列表
     *
     * @param roomId 房间号
     * @return java.util.List<java.lang.String>
     */
    List<String> listUserNameInRoom(String roomId);
}