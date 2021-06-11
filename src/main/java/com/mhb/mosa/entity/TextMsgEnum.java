package com.mhb.mosa.entity;

import com.mhb.mosa.scoket.Handle;
import lombok.extern.slf4j.Slf4j;

/**
 * @date: 2021/5/21 18:05
 */
@Slf4j
public enum TextMsgEnum {
    /**
     * 聊天
     */
    CHAT(2) {
        @Override
        public Handle next(int type) {
            return TextMsgEnumChat.getInstance(type);
        }
    },
    /**
     * MoSa
     */
    MOSA(3) {
        @Override
        Handle next(int type) {
            return TextMsgEnumMosa.getInstance(type);
        }
    };

    /**
     * 模块
     */
    private int module;

    TextMsgEnum(int module) {
        this.module = module;
    }

    abstract Handle next(int type);

    public static Handle getHandle(int module, int type) {
        for (TextMsgEnum value : TextMsgEnum.values()) {
            if (value.module == module) {
                return value.next(type);
            }
        }
        return null;
    }
}
