package com.rengu.tools7.exception;


import com.rengu.tools7.enums.SystemErrorEnum;

public class SystemException extends RuntimeException {

    private SystemErrorEnum systemErrorEnum;

    public SystemException(SystemErrorEnum systemErrorEnum) {
        super(systemErrorEnum.getMessage());
        this.systemErrorEnum = systemErrorEnum;
    }

    public SystemErrorEnum getSystemErrorEnum() {
        return systemErrorEnum;
    }
}
