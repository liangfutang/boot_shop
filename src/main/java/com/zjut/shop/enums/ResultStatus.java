package com.zjut.shop.enums;

public enum ResultStatus {

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
    NO_USER_EXEC(6203, "用户不存在");



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
