package com.mhb.mosa.util;

import com.mhb.mosa.vo.ResultBean;

/**
 * @date: 2021/6/10 11:23
 */

public class ResultBeanUtils {

    private final static String CODE_SUCCESS = "0000";
    private final static String MSG_SUCCESS = "成功";

    public static ResultBean success() {
        return new ResultBean(CODE_SUCCESS, MSG_SUCCESS);
    }

    private final static String CODE_FAILED = "0001";
    private final static String MSG_FAILED = "失败";

    public static ResultBean failed() {
        return new ResultBean(CODE_FAILED, MSG_FAILED);
    }

    private final static String CODE_EXCEPTION = "0002";
    private final static String MSG_EXCEPTION = "异常";

    public static ResultBean exception() {
        return new ResultBean(CODE_EXCEPTION, MSG_EXCEPTION);
    }

}
