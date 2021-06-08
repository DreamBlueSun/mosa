package com.mhb.mosa.memory;

import java.util.HashSet;
import java.util.Set;

/**
 * @date: 2021/6/8 15:49
 */

public class RoleSquareHome implements RoleHome{

    /**
     * Set<String>
     * 存放连接中的sessionId
     * 对应用户类型为广场
     */
    private static Set<String> set = new HashSet<>(8);

    @Override
    public void add(String sessionId) {
        set.add(sessionId);
    }

    @Override
    public void remove(String sessionId) {
        set.remove(sessionId);
    }

    @Override
    public Set<String> get() {
        return set;
    }
}
