package com.mhb.mosa.service;

import java.util.List;

/**
 * @date: 2021/6/15 18:16
 */

public interface PlayerService {

    /**
     * 获取指定类型的player
     *
     * @param userName 玩家名称
     * @param clazz    类
     * @return T
     */
    <T> T getPlayer(String userName, Class<T> clazz);

    /**
     * 获取指定类型的player的某些字段
     *
     * @param userName 玩家名称
     * @param fields   字段名称数组
     * @return java.util.List<java.lang.String>
     */
    List<String> getPlayer(String userName, String[] fields);

}
