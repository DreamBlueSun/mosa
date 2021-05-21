package com.mhb.mosa.vo;

/**
 * @date: 2021/5/21 18:05
 */

public enum TextMsgEnum {
    /**
     * 登入成功
     */
    LOGIN_OK(1, 1),
    /**
     * 登入失败
     */
    LOGIN_FAIL(1, 0);

    /**
     * 消息类型
     */
    private int type;

    /**
     * 行为数据
     */
    private Object data;

    TextMsgEnum(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
