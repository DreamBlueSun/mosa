package com.mhb.mosa.entity;

import com.mhb.mosa.memory.RoleHomeEnum;
import com.mhb.mosa.memory.SessionHome;
import com.mhb.mosa.util.StaticUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

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
    }

    @Override
    public void afterConnectionEstablished() {
        String[] fields = {"roomId", "index"};
        List<String> list = StaticUtils.playerService.getPlayer(getUserName(), fields);
        if (!CollectionUtils.isEmpty(list) && list.size() > 1) {
            this.roomId = list.get(0);
            this.index = Integer.parseInt(list.get(1));
        }
        //TODO 通知房间内玩家信息
        super.afterConnectionEstablished();
    }

    @Override
    public void afterConnectionClosed() {
        try {
            SessionHome.sendMsg(getSessionId(), "连接丢失");
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.afterConnectionClosed();
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof PlayerMosa) {
            return ((PlayerMosa) o).index - this.index;
        }
        return 1;
    }
}
