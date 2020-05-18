package com.rengu.tools7.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rengu.tools7.enums.SystemErrorEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
public class ResultEntity<T> implements Serializable {

    private String id = UUID.randomUUID().toString();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime = new Date();
    private int code;
    private String message;
    private T data;


    public ResultEntity(SystemErrorEnum systemErrorEnum, T data) {
        this.code = systemErrorEnum.getCode();
        this.message = systemErrorEnum.getMessage();
        this.data = data;
    }

    public ResultEntity(T data) {
        this.code = 1;
        this.message = "请求成功";
        this.data = data;
    }
}
