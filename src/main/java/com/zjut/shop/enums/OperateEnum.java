package com.zjut.shop.enums;

public enum OperateEnum {

    AUTH_SELECT_TYPE_LIST("list", "查询结果列表显示"),
    AUTH_SELECT_TYPE_TREE("tree", "查询结果树状显示");

    private String code;
    private String desc;

    OperateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 判断该code是否在当前所有的可操作范围内
     * @param code
     * @return
     */
    public static boolean contains(String code) {
        for (OperateEnum one : OperateEnum.values()) {
            if (one.getCode().equals(code)) return true;
        }
        return false;
    }
}
