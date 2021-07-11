package com.zjut.shop.execption;

import com.zjut.shop.enums.ResultStatus;

public class ShopRuntimeException extends RuntimeException{

    /**
     * 状态码
     */
    private Integer code;

    public ShopRuntimeException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public ShopRuntimeException(ResultStatus resultStatus) {
        super(resultStatus.getMsg());
        this.code = resultStatus.getCode();
    }

    public Integer getCode() {
        return code;
    }

}
