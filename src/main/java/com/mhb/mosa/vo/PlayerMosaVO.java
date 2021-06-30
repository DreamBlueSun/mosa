package com.mhb.mosa.vo;

import com.mhb.mosa.entity.PlayerMosa;
import lombok.Data;

/**
 * @date: 2021/6/22 17:23
 */
@Data
public class PlayerMosaVO {

    /**
     * 名称
     */
    private String userName;

    /**
     * 位置
     */
    private Integer index;

    /**
     * 消息
     */
    private String msg;

    /**
     * 类型（0：目标是自己，1：目标是其他玩家）
     */
    private Integer type;

    public PlayerMosaVO(PlayerMosa player, String msg) {
        this.msg = msg;
        this.userName = player.getUserName();
        this.index = player.getIndex();
    }

    public PlayerMosaVO(PlayerMosa player, String msg, int type) {
        this.msg = msg;
        this.userName = player.getUserName();
        this.index = player.getIndex();
        this.type = type;
    }
}
