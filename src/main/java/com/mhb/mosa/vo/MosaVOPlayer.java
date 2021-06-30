package com.mhb.mosa.vo;

import lombok.Data;

/**
 * @date: 2021/6/30 17:58
 */
@Data
public class MosaVOPlayer {

    /**
     * 名称
     */
    private String userName;

    /**
     * 位置
     */
    private Integer index;

    public MosaVOPlayer(String userName, Integer index) {
        this.userName = userName;
        this.index = index;
    }
}
