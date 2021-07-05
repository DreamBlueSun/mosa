package com.mhb.mosa.entity;

import lombok.Data;

/**
 * @date: 2021/6/18 17:24
 */
@Data
public class RoomMosa {

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 房主名称
     */
    private String master;

    /**
     * 方向（0：逆时针，1：顺时针）
     */
    private Integer direction;

    /**
     * 属性（0：火，1：水，2：风）
     */
    private Integer properties;

    /**
     * 当前位置
     */
    private Integer index;

    /**
     * 出牌规则（0：基本，1：连击，2：棱镜）
     */
    private Integer rule;

    /**
     * 连击值
     */
    private Integer hit;

    /**
     * 当前位置已主动抽卡（0：未主动抽卡，1：已主动抽卡）（每次变更当前player位置时重置为0）
     */
    private Integer draw;

    public RoomMosa(String roomId, String master) {
        this.roomId = roomId;
        this.master = master;
        this.direction = 0;
        this.properties = 0;
        this.index = 0;
        this.rule = 0;
        this.hit = 0;
        this.draw = 0;
    }
}
