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
     * 类型
     * 0：自身加入房间动作（表示是自身加入房间,需要填充其他玩家信息）
     * 1：准备就绪动作（表示全部玩家都已准备就绪，需要填充玩家手牌、当前出牌位置等信息）
     */
    private Integer type;

    /**
     * 其他玩家信息
     */
    private List<MosaVOPlayer> players;

    /**
     * 手牌名称集合
     */
    private List<String> handCards;

    private MosaVO() {
    }

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

    private MosaVO copyNew(MosaVO data) {
        MosaVO vo = new MosaVO();
        vo.setRoomId(data.getRoomId());
        vo.setUserName(data.getUserName());
        vo.setIndex(data.getIndex());
        vo.setMsg(data.getMsg());
        vo.setType(data.getType());
        vo.setPlayers(data.getPlayers());
        vo.setHandCards(data.getHandCards());
        return vo;
    }

    /**
     * 填充数据-其他玩家信息
     *
     * @return void
     */
    public MosaVO fillPlayers() {
        Map<String, String> map = StaticUtils.jedisCluster.hgetAll(MosaServiceImpl.redisKeyHashRoomIndexInfo(this.roomId));
        map.remove(String.valueOf(this.index));
        this.players = map.entrySet().stream().map(i -> {
            MosaVOPlayer vo = new MosaVOPlayer();
            vo.setIndex(Integer.parseInt(i.getKey()));
            vo.setUserName(i.getValue());
            return vo;
        }).collect(Collectors.toList());
        return this;
    }

    /**
     * 填充数据-自己手牌名称集合、其他玩家手牌数
     *
     * @return void
     */
    public void fillPlayersHandCards() {
        this.handCards = StaticUtils.jedisCluster.lrange(MosaServiceImpl.redisKeyListRoomPlayerHandCards(this.roomId, this.userName), 0, -1);
        this.players = this.players.stream().peek(i -> i.setHandNum(StaticUtils.jedisCluster.llen(MosaServiceImpl.redisKeyListRoomPlayerHandCards(this.roomId, i.getUserName())).intValue())).collect(Collectors.toList());
    }

    /**
     * 获取其他目标位置玩家手牌对象
     *
     * @param index this.players的集合目标数据下标
     * @return com.mhb.mosa.vo.MosaVO
     */
    public MosaVO otherPlayersHandCards(int index) {
        MosaVOPlayer self = new MosaVOPlayer();
        self.setIndex(this.index);
        self.setUserName(this.userName);
        self.setHandNum(StaticUtils.jedisCluster.llen(MosaServiceImpl.redisKeyListRoomPlayerHandCards(this.roomId, this.userName)).intValue());
        MosaVO aNew = copyNew(this);
        aNew.getPlayers().add(self);
        MosaVOPlayer target = this.players.get(index);
        this.players.remove(index);
        aNew.setIndex(target.getIndex());
        aNew.setUserName(target.getUserName());
        aNew.setHandCards(StaticUtils.jedisCluster.lrange(MosaServiceImpl.redisKeyListRoomPlayerHandCards(this.roomId, this.userName), 0, -1));
        return aNew;
    }

}
