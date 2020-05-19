package com.rengu.tools7.utils;


import com.rengu.tools7.entity.ResultEntity;
import com.rengu.tools7.enums.SystemErrorEnum;

/**
 * @author yueyongchang
 * @date 2019/11/4 14:50
 */
public class ResultUtils {
    public static ResultEntity success(Object data, Object o) {
        return new ResultEntity<>(SystemErrorEnum.SUCCESS, data);
    }

    public static ResultEntity warn(SystemErrorEnum resultCode, String msg) {
        ResultEntity<Object> resultEntity = new ResultEntity<>(resultCode);
        resultEntity.setMessage(msg);
        return resultEntity;
    }

    public static ResultEntity warn(SystemErrorEnum resultCode) {
        return new ResultEntity(resultCode);
    }

    public static ResultEntity success(Object data) {
        return new ResultEntity<>(SystemErrorEnum.SUCCESS, data);
    }
}
