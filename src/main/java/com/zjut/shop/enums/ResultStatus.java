package com.zjut.shop.enums;

public enum ResultStatus {

    INTERNAL_EXEC(1000, "内部异常"),
    PARAM_EXEC(4000, "参数异常"),
    MENU_EXEC(4001, "获取菜单异常"),
    SELECT_EXEC(5000, "查询异常"),
    PAGE_NUM_EXEC(6000, "页码参数不规范"),
    PAGE_SIZE_EXEC(6001, "页码参数不规范"),
    UPDATE_USER_STATUS_FAILURE(6100, "更新用户状态失败"),
    MORE_USER_FAILURE(6101, "存在多个相同id的用户，数据异常"),
    NO_USER_FAILURE(6102, "没查到相应的用户"),
    ADD_USER_EXEC(6200, "添加用户异常"),
    DELETE_USER_EXEC(6201, "删除用户异常"),
    UPDATE_USER_EXEC(6202, "修改用户异常"),
    SELECT_USER_EXEC(6203, "查找用户异常"),
    NO_USER_EXEC(6204, "用户不存在"),
    OPERATE_TYPE_EXEC(6205, "操作类型异常"),
    MORE_ROLE_FAILURE(6206, "存在多个相同id的角色，数据异常"),
    NO_ROLE_FAILURE(6207, "没查到相应的角色"),
    NO_RIGHT_EXEC(6300, "当前权限不存在");



    private Integer code;
    private String msg;
    ResultStatus(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
