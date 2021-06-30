package com.mhb.mosa.util;

import com.mhb.mosa.vo.ServerLimitVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @auther: MaoHangBin
 * @date: 2020/4/8 0008 10:03
 */

public class ServerLimitUtils {

    /**
     * @param list  数据集合
     * @param total 数据总数
     * @return com.sinovatech.release.vo.limit.ServerLimitVO
     * @author MaoHangBin
     * @description 服务端分页返回数据封装工具类
     * @date 2020/4/8 0008 10:07
     **/
    public static ServerLimitVO build(List list, long total) {
        ServerLimitVO vo = new ServerLimitVO();
        if (list != null && list.size() > 0) {
            vo.setRows(list);
            vo.setTotal(String.valueOf(total));
        } else {
            vo.setRows(new ArrayList<>());
            vo.setTotal("0");
        }
        return vo;
    }

}
