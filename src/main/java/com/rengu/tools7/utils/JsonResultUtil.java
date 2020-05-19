package com.rengu.tools7.utils;

/**
 * @author Eason岳
 * @date 2020/5/7
 */
public class JsonResultUtil {

    private Integer code;
    private String message;
    private Object obj;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "JsonResultUtil{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", obj=" + obj +
                '}';
    }

    private JsonResultUtil(){};

    public static JsonResultUtil success(String message, Object obj){
        JsonResultUtil jsonResultUtil = new JsonResultUtil();
        jsonResultUtil.setCode(200);
        jsonResultUtil.setMessage(message);
        jsonResultUtil.setObj(obj);
        return jsonResultUtil;
    }

    public static JsonResultUtil failure(String message, Object obj){
        JsonResultUtil jsonResultUtil = new JsonResultUtil();
        jsonResultUtil.code = 100;
        jsonResultUtil.message = message;
        jsonResultUtil.obj = obj;
        return jsonResultUtil;
    }

}
