package com.zjut.shop.enums;

public enum AuthLevelEnum {

    ONE("1", "一级权限"),
    TWO("2", "二级权限"),
    THREE("3", "三级权限"),
    FOUR("4", "四级权限");

    /**
     * 权限的等级
     */
    private final String code;
    /**
     * 权限的描述
     */
    private final String name;

    AuthLevelEnum(String level, String name) {
        this.code = level;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
