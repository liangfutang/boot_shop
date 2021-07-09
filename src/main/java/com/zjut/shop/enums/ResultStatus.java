package com.zjut.shop.enums;

public enum ResultStatus {

    MENU_EXEC(4001, "获取菜单异常"),
    SELECT_EXEC(5000, "查询异常");



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
