package com.mhb.mosa.entity;

import lombok.Data;

/**
 * @date: 2021/6/8 16:40
 */
@Data
public class TextMsg<T> {
    /**
     * 消息类型
     */
    private int type;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 消息内容
     */
    private T data;

    public TextMsg(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public TextMsg(int type, String msg, T data) {
        this.type = type;
        this.msg = msg;
        this.data = data;
    }
}
