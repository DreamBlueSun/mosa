package com.mhb.mosa.vo;

import com.mhb.mosa.entity.PlayerMosa;
import com.mhb.mosa.service.impl.MosaServiceImpl;
import com.mhb.mosa.util.StaticUtils;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @date: 2021/6/22 17:23
 */
@Data
public class MosaVO {

    /**
     * 类型（0：消息触发对象==userName，1：消息触发对象!=userName）
     */
    private Integer type;

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 触发名称
     */
    private String userName;

    /**
     * 触发位置
     */
    private Integer index;

    /**
     * 消息
     */
    private String msg;

    /**
     * 其他玩家信息
     */
    private List<MosaVOPlayer> players;

    public MosaVO(PlayerMosa player, String msg) {
        this.msg = msg;
        this.roomId = player.getRoomId();
        this.userName = player.getUserName();
        this.index = player.getIndex();
    }

    public MosaVO(PlayerMosa player, String msg, int type) {
        this.msg = msg;
        this.roomId = player.getRoomId();
        this.userName = player.getUserName();
        this.index = player.getIndex();
        this.type = type;
    }

    public void fillPlayers() {
        Map<String, String> map = StaticUtils.jedisCluster.hgetAll(MosaServiceImpl.redisKeyHashRoomIndexInfo(this.roomId));
        this.players = map.entrySet().stream().map(i -> new MosaVOPlayer(i.getValue(), Integer.parseInt(i.getKey()))).collect(Collectors.toList());
    }

}
