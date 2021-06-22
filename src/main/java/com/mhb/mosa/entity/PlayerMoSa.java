package com.mhb.mosa.entity;

import com.alibaba.fastjson.JSON;
import com.mhb.mosa.memory.RoleHomeEnum;
import com.mhb.mosa.memory.SessionHome;
import com.mhb.mosa.service.impl.PlayerServiceImpl;
import com.mhb.mosa.util.StaticUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date: 2021/6/2 17:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerMosa extends Player implements Comparable {

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 位置
     */
    private Integer index;

    public PlayerMosa(String sessionId, String userName) {
        super(sessionId, userName, RoleHomeEnum.MOSA);
        List<String> list = StaticUtils.playerService.getProperties(getUserName(), "roomId", "index");
        if (!CollectionUtils.isEmpty(list) && list.size() > 1) {
            this.roomId = list.get(0);
            this.index = Integer.parseInt(list.get(1));
        }
    }

    @Override
    public void afterConnectionEstablished() {
        //缓存玩家信息
        Map<String, String> playerMap = new HashMap<>(2);
        playerMap.put("sessionId", getSessionId());
        playerMap.put("userName", getUserName());
        StaticUtils.jedisCluster.hmset(PlayerServiceImpl.redisKeyHashPlayerInfo(getUserName()), playerMap);
        //发送信息
        try {
            sendAll(JSON.toJSONString(new TextMsg<>(TextMsgEnum.MOSA.getModule(), TextMsgEnumMosa.JOIN_ROOM.getType(), getUserName() + "加入房间")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.afterConnectionEstablished();
    }

    @Override
    public void afterConnectionClosed() {
        StaticUtils.mosaService.leaveRoom(this.roomId, getUserName(), String.valueOf(this.index));
        try {
            sendOther(JSON.toJSONString(new TextMsg<>(TextMsgEnum.MOSA.getModule(), TextMsgEnumMosa.LEAVE_ROOM.getType(), getUserName() + "离开房间")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.afterConnectionClosed();
    }

    @Override
    public void sendAll(String msg) throws IOException {
        List<String> listUserName = StaticUtils.mosaService.listUserNameInRoom(this.roomId);
        List<String> listSessionId = new ArrayList<>();
        for (String username : listUserName) {
            listSessionId.add(StaticUtils.playerService.getProperties(username, "sessionId").get(0));
        }
        SessionHome.sendMsg(listSessionId, msg);
    }

    @Override
    public void sendOther(String msg) throws IOException {
        List<String> listUserName = StaticUtils.mosaService.listUserNameInRoom(this.roomId);
        listUserName.remove(getUserName());
        if (CollectionUtils.isNotEmpty(listUserName)) {
            List<String> listSessionId = new ArrayList<>();
            for (String username : listUserName) {
                listSessionId.add(StaticUtils.playerService.getProperties(username, "sessionId").get(0));
            }
            SessionHome.sendMsg(listSessionId, msg);
        }
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof PlayerMosa) {
            return ((PlayerMosa) o).index - this.index;
        }
        return 1;
    }
}
