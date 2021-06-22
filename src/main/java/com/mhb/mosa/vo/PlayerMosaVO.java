package com.mhb.mosa.vo;

import com.mhb.mosa.entity.PlayerMosa;
import lombok.Data;

/**
 * @date: 2021/6/22 17:23
 */
@Data
public class PlayerMosaVO {

    /**
     * 消息
     */
    private String msg;

    /**
     * 名称
     */
    private String userName;

    /**
     * 位置
     */
    private Integer index;

    public PlayerMosaVO(PlayerMosa player, String msg) {
        this.msg = msg;
        this.userName = player.getUserName();
        this.index = player.getIndex();
    }
}
