package com.mhb.mosa.vo;

import lombok.Data;

/**
 * @date: 2021/6/24 17:26
 */
@Data
public class ListMosaRoomVO {

    /**
     * 房间号
     */
    private String roomId;

    /**
     * 人数
     */
    private String playersNum;

    public ListMosaRoomVO(String roomId) {
        this.roomId = roomId;
    }
}
