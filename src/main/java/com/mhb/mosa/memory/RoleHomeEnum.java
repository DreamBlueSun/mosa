package com.mhb.mosa.memory;

import com.mhb.mosa.entity.Player;
import com.mhb.mosa.entity.PlayerMosa;

/**
 * @date: 2021/6/8 16:10
 */

public enum RoleHomeEnum {

    /**
     * 广场
     */
    SQUARE(2, new RoleSquareHome()) {
        @Override
        public Player newPlayer(String sessionId, String userName) {
            return new Player(sessionId, userName);
        }
    },
    /**
     * MoSa
     */
    MOSA(3, new RoleMoSaHome()) {
        @Override
        public Player newPlayer(String sessionId, String userName) {
            return new PlayerMosa(sessionId, userName);
        }
    };

    /**
     * 角色
     */
    private int role;

    /**
     * 角色集合
     */
    private RoleHome roleHome;

    RoleHomeEnum(int role, RoleHome roleHome) {
        this.role = role;
        this.roleHome = roleHome;
    }

    public int getRole() {
        return role;
    }

    public RoleHome getRoleHome() {
        return roleHome;
    }

    public abstract Player newPlayer(String sessionId, String userName);

    public static RoleHomeEnum getInstance(int role) {
        for (RoleHomeEnum value : RoleHomeEnum.values()) {
            if (value.role == role) {
                return value;
            }
        }
        return null;
    }
}
