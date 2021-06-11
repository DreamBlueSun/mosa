package com.mhb.mosa.entity;
import lombok.Data;

/**
 * @date: 2021/6/8 16:40
 */
@Data
public class TextMsg<T> {

    /**
     * 模块
     */
    private int module;

    /**
     * 类型
     */
    private int type;

    /**
     * 数据
     */
    private T data;
}
