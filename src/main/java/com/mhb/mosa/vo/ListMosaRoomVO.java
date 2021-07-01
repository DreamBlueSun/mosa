package com.mhb.mosa.vo;

import lombok.Data;

/**
 * @date: 2021/6/24 17:26
 */
@Data
public class ListMosaRoomVO {

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 房主名称
     */
    private String master;

    /**
     * 人数
     */
    private String playersNum;

    public ListMosaRoomVO(String roomId) {
        this.roomId = roomId;
    }
}
