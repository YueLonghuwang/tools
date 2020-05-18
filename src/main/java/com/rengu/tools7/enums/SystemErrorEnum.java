package com.rengu.tools7.enums;

public enum SystemErrorEnum {


    SUCCESS(0, "请求成功"),
    UNKNOWN_ERROR(0, "未知错误"),
    ROLE_NAME_IS_EMPTY(101, "不存在"),
    ROLE_IS_EXISTS(102, "已存在该角色"),
    ROLE_NOT_FOUND(103, "未发现该角色"),
    ROLE_CHANGE_NOT_SUPPORT_ERROR(104, "不可修改的角色"),
    ROLE_ID_NOT_FOUND_ERROR(106, "角色id未发现"),

    MODEL_ID_NOT_FOUND(407, "该id不存在"),
    FILE_MD5_EXISTED(408, "文件MD5不存在"),

    //数据实例相关
    DATA_ID_NOT_FOUND(4001, "数据实例ID未发现"),

    //产品相关
    PRODUCT_ID_NOT_FOUND(10001, "该产品id未找到"),
    PRODUCT_FIELD_NUMBER_EXIST(10002, "该代号已存在"),
    PRODUCT_FIELD_NAME_EXIST(10003, "该代号已存在"),
    PRODUCT_LEVEL_NAME_EXIST(10004, "该产品已添加过批次"),
    PRODUCT_BATCH_EXIST(10005, "该产品已添加过批次"),
    PRODUCT_NUMBER_EXIST(10006, "该产品已添加过编号"),
    PRODUCT_CODE_NAME_NOT_FOUND(10007, "未发现该产品的产品名称和产品代号"),

    //购物车异常
    SHOPPING_ARGS_NOT_FOUNT(701, "购物车内容不存在"),
    FILE_NOT_EXIST(702, "待在下载的文件不存在"),
    PRODUCT_ID_NOT_NULL(703, "产品id未找到"),
    TIME_NOT_FOUND(704, "请输入开始时间和结束时间"),
    CURRENT_DATA_DELETED(705, "当前数据实例不存在"),

    // 部门相关    1003x
    DEPARTMENT_NAME_NOT_FOUND(704, "部门名称不存在"),
    DEPARTMENT_ID_NOT_FOUND(705, "部门ID不存在"),
    DEPARTMENT_NAME_EXISTED_ERROR(10032, "该部门名称已存在"),

    //任务阶段+类型相关
    PERIOD_NAME_NOT_FOUND(100011, "任务节点名称未发现"),

    // 子库相关   2600x
    SUBLIBRARY_ID_NOT_FOUND_ERROR(26001, "未发现该子库id"),
    SUBLIBRARY_FILE_ARGS_NOT_FOUND_ERROR(27003, "未发现子库文件参数"),
    FILE_NAME_EXIST(23002, "文件名称已存在"),

    // 子任务相关    2200x
    SUBTASK_ARGS_NOT_FOUND_ERROR(22001, "未发现子任务参数"),
    TASK_PERIOD_ARGS_NOT_FOUND_ERROR(22002, "任务阶段已存在"),
    TASK_PERIOD_NAME_EXIST(22003, "任务阶段已存在"),
    TASK_TYPE_ARGS_NOT_FOUND_ERROR(22004, "任务阶段已存在"),

    USER_USERNAME_IS_EMPTY(201, "用户名参数不存在"),
    USER_PASSWORD_IS_EMPTY(202, "用户密码参数不存在"),
    USER_NAME_NOT_FOUND(205, "用户名不存在"),
    USER_NOT_FOUND(203, "未发现该用户"),
    USER_ID_NOT_FOUND_ERROR(207, "用户名id不存在"),
    USER_ROLE_NOT_FOUND_ERROR(10019, "请指定用户角色"),

    USER_IS_EXISTS(204, "已存在该用户");

    private int code;
    private String message;

    SystemErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
