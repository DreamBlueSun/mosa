package com.mhb.mosa.vo;

import java.util.List;

/**
 * @description:
 * @auther: MaoHangBin
 * @date: 2020/4/8 0008 10:01
 */

public class ServerLimitVO {

    private List rows;

    private String total;

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
