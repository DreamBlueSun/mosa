package com.mhb.mosa.memory;

import java.util.Set;

/**
 * @date: 2021/6/8 16:13
 */

public interface RoleHome {

    void add(String sessionId);

    void remove(String sessionId);

    Set<String> get();
}
