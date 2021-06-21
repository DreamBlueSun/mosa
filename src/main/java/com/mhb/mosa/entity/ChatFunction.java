package com.mhb.mosa.entity;

import java.io.IOException;

/**
 * @date: 2021/6/8 15:09
 */

public interface ChatFunction {

    void send(String msg) throws IOException;

    void sendAll(String msg) throws IOException;

    void sendOther(String msg) throws IOException;

}
