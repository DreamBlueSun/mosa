package com.mhb.mosa.memory;

/**
 * @date: 2021/6/8 16:10
 */

public enum RoleHomeEnum {

    /**
     * 广场
     */
    SQUARE(0, new RoleSquareHome()),
    /**
     * MoSa
     */
    MOSA(1, new RoleMoSaHome());

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

    public static RoleHomeEnum getInstance(int role) {
        for (RoleHomeEnum value : RoleHomeEnum.values()) {
            if (value.role == role) {
                return value;
            }
        }
        return null;
    }
}
