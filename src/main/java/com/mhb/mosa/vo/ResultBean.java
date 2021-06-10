package com.mhb.mosa.vo;

/**
 * @date: 2021/6/10 11:21
 */

public class ResultBean {

    private String code;
    private String msg;

    public ResultBean(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
