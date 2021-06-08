package com.mhb.mosa.entity;

/**
 * @date: 2021/5/21 18:05
 */

public enum TextMsgEnum {
    /**
     * 登入成功
     */
    LOGIN_O(1, "登入成功"),
    /**
     * 登入失败
     */
    LOGIN_F(1, "登入失败"),
    /**
     * 发送聊天消息
     */
    CHAT(2);

    /**
     * 消息类型
     */
    private int type;

    /**
     * 消息内容
     */
    private String msg;

    TextMsgEnum(int type) {
        this.type = type;
    }

    TextMsgEnum(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

    public static TextMsgEnum getInstance(int type) {
        for (TextMsgEnum value : TextMsgEnum.values()) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }
}
