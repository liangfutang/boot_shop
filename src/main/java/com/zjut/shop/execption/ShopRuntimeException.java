package com.zjut.shop.execption;

public class ShopRuntimeException extends RuntimeException{

    /**
     * 状态码
     */
    private Integer code;

    public ShopRuntimeException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
